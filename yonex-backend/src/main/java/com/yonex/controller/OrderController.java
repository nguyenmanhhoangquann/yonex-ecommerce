package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.OrderDto;
import com.yonex.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Tạo đơn hàng, tra cứu lịch sử mua hàng và hủy đơn")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Tạo đơn hàng mới từ giỏ hàng hiện tại")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> createOrder(
            @Valid @RequestBody OrderDto.CreateOrderRequest req
    ) {
        OrderDto.OrderResponse response = orderService.createOrderFromCart(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tạo đơn hàng thành công", response));
    }

    @GetMapping("/my-orders")
    @Operation(summary = "Xem lịch sử đơn hàng của người dùng hiện tại")
    public ResponseEntity<ApiResponse<List<OrderDto.OrderResponse>>> getMyOrders() {
        List<OrderDto.OrderResponse> list = orderService.getMyOrders();
        return ResponseEntity.ok(ApiResponse.ok("Lấy lịch sử đơn hàng thành công", list));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem chi tiết một đơn hàng theo ID")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> getOrderById(@PathVariable Long id) {
        OrderDto.OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết đơn hàng thành công", response));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Hủy đơn hàng (khi đang ở trạng thái PENDING hoặc CONFIRMED)")
    public ResponseEntity<ApiResponse<OrderDto.OrderResponse>> cancelOrder(@PathVariable Long id) {
        OrderDto.OrderResponse response = orderService.cancelOrder(id);
        return ResponseEntity.ok(ApiResponse.ok("Hủy đơn hàng thành công", response));
    }
}
