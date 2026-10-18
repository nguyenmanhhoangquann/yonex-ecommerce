package com.yonex.controller;

import com.yonex.dto.AdminDto;
import com.yonex.dto.ApiResponse;
import com.yonex.dto.OrderDto;
import com.yonex.dto.ProductDto;
import com.yonex.entity.User;
import com.yonex.service.AdminService;
import com.yonex.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin Operations", description = "Quản lý dành riêng cho ADMIN: Dashboard, Quản trị Sản phẩm, Đơn hàng và Người dùng")
public class AdminController {

    private final AdminService adminService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    @Operation(summary = "Xem tổng quan thống kê hệ thống (Doanh thu, người dùng, sản phẩm, trạng thái đơn)")
    public ResponseEntity<ApiResponse<AdminDto.DashboardStats>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy dữ liệu dashboard thành công", adminService.getDashboard()));
    }

    @PostMapping("/products")
    @Operation(summary = "Tạo sản phẩm mới")
    public ResponseEntity<ApiResponse<ProductDto.ProductResponse>> createProduct(
            @Valid @RequestBody AdminDto.SaveProductRequest req
    ) {
        ProductDto.ProductResponse product = adminService.createProduct(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tạo sản phẩm thành công", product));
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "Cập nhật sản phẩm")
    public ResponseEntity<ApiResponse<ProductDto.ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody AdminDto.SaveProductRequest req
    ) {
        ProductDto.ProductResponse product = adminService.updateProduct(id, req);
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật sản phẩm thành công", product));
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "Xóa mềm (ẩn) sản phẩm")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.ok("Đã ẩn sản phẩm khỏi hệ thống", null));
    }

    @GetMapping("/orders")
    @Operation(summary = "Lấy danh sách tất cả các đơn hàng trên toàn hệ thống có phân trang")
    public ResponseEntity<ApiResponse<Page<OrderDto.OrderResponse>>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<OrderDto.OrderResponse> orders = orderService.getAllOrders(PageRequest.of(page, size));
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách đơn hàng thành công", orders));
    }

    @PatchMapping("/orders/{id}/status")
    @Operation(summary = "Cập nhật trạng thái đơn hàng (CONFIRMED, SHIPPED, DELIVERED, CANCELLED)")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody AdminDto.UpdateOrderStatusRequest req
    ) {
        OrderDto.OrderResponse order = orderService.updateOrderStatus(id, req.getStatus(), req.getPaymentStatus());
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật trạng thái đơn hàng thành công", order));
    }

    @GetMapping("/users")
    @Operation(summary = "Danh sách tài khoản người dùng")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách người dùng thành công", adminService.getAllUsers()));
    }

    @PatchMapping("/users/{id}/status")
    @Operation(summary = "Khóa hoặc mở khóa tài khoản người dùng")
    public ResponseEntity<ApiResponse<User>> toggleUserStatus(
            @PathVariable Long id,
            @RequestBody AdminDto.ToggleUserStatusRequest req
    ) {
        User user = adminService.toggleUserStatus(id, req.getActive());
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật trạng thái tài khoản thành công", user));
    }
}
