package com.yonex.dto;

import com.yonex.enums.OrderStatus;
import com.yonex.enums.PaymentMethod;
import com.yonex.enums.PaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartResponse {
        private Long id;
        private List<CartItemResponse> items;
        private BigDecimal subtotal;
        private Integer totalItems;
        private BigDecimal freeShippingThreshold;
        private Boolean eligibleForFreeShipping;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CartItemResponse {
        private Long id;
        private Long variantId;
        private Long productId;
        private String productName;
        private String productSlug;
        private String colorName;
        private String colorCode;
        private String size;
        private String sku;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal lineTotal;
        private String imageUrl;
        private Integer availableStock;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCartItemRequest {
        @NotNull(message = "Variant ID không được để trống")
        private Long variantId;

        @NotNull(message = "Số lượng không được để trống")
        @Min(value = 1, message = "Số lượng tối thiểu là 1")
        private Integer quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCartItemRequest {
        @NotNull(message = "Số lượng không được để trống")
        @Min(value = 1, message = "Số lượng tối thiểu là 1")
        private Integer quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateOrderRequest {
        @NotBlank(message = "Tên người nhận không được để trống")
        private String recipientName;

        @NotBlank(message = "Số điện thoại không được để trống")
        private String phone;

        @NotBlank(message = "Địa chỉ nhận hàng không được để trống")
        private String shippingAddress;

        private String note;

        @NotNull(message = "Phương thức thanh toán không được để trống")
        private PaymentMethod paymentMethod;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderResponse {
        private Long id;
        private String orderCode;
        private OrderStatus status;
        private PaymentStatus paymentStatus;
        private PaymentMethod paymentMethod;
        private BigDecimal totalAmount;
        private BigDecimal shippingFee;
        private String recipientName;
        private String phone;
        private String shippingAddress;
        private String note;
        private List<OrderItemResponse> items;
        private LocalDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemResponse {
        private Long id;
        private Long variantId;
        private String productName;
        private String colorName;
        private String size;
        private String sku;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
    }
}
