package com.yonex.dto;

import com.yonex.enums.OrderStatus;
import com.yonex.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AdminDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DashboardStats {
        private long totalUsers;
        private long totalActiveProducts;
        private long totalOutOfStockVariants;
        private long totalOrders;
        private Map<String, Long> ordersByStatus;
        private BigDecimal totalRevenue;
        private List<OrderDto.OrderResponse> recentOrders;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateOrderStatusRequest {
        private OrderStatus status;
        private PaymentStatus paymentStatus;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToggleUserStatusRequest {
        private Boolean active;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SaveProductRequest {
        private String sku;
        private String name;
        private String slug;
        private String shortDescription;
        private String description;
        private BigDecimal price;
        private BigDecimal originalPrice;
        private Long sportId;
        private Long categoryId;
        private String series;
        private String badge;
        private Boolean isFeatured;
        private Boolean isNew;
        private Boolean active;
    }
}
