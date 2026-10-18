package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.OrderDto;
import com.yonex.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Quản lý giỏ hàng của người dùng")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Xem giỏ hàng của người dùng hiện tại")
    public ResponseEntity<ApiResponse<OrderDto.CartResponse>> getCart() {
        OrderDto.CartResponse response = cartService.getCart();
        return ResponseEntity.ok(ApiResponse.ok("Lấy giỏ hàng thành công", response));
    }

    @PostMapping("/items")
    @Operation(summary = "Thêm sản phẩm/biến thể vào giỏ hàng")
    public ResponseEntity<ApiResponse<OrderDto.CartResponse>> addToCart(@Valid @RequestBody OrderDto.AddCartItemRequest req) {
        OrderDto.CartResponse response = cartService.addToCart(req);
        return ResponseEntity.ok(ApiResponse.ok("Thêm vào giỏ hàng thành công", response));
    }

    @PatchMapping("/items/{itemId}")
    @Operation(summary = "Cập nhật số lượng của một mục trong giỏ hàng")
    public ResponseEntity<ApiResponse<OrderDto.CartResponse>> updateItemQuantity(
            @PathVariable Long itemId,
            @Valid @RequestBody OrderDto.UpdateCartItemRequest req
    ) {
        OrderDto.CartResponse response = cartService.updateQuantity(itemId, req);
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật số lượng thành công", response));
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Xóa một sản phẩm khỏi giỏ hàng")
    public ResponseEntity<ApiResponse<OrderDto.CartResponse>> removeItem(@PathVariable Long itemId) {
        OrderDto.CartResponse response = cartService.removeItem(itemId);
        return ResponseEntity.ok(ApiResponse.ok("Xóa sản phẩm khỏi giỏ hàng thành công", response));
    }

    @DeleteMapping
    @Operation(summary = "Xóa toàn bộ giỏ hàng")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(ApiResponse.ok("Đã làm trống giỏ hàng", null));
    }
}
