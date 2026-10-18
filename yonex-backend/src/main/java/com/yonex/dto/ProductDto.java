package com.yonex.dto;

import com.yonex.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductResponse {
        private Long id;
        private String sku;
        private String name;
        private String slug;
        private String shortDescription;
        private String description;
        private BigDecimal price;
        private BigDecimal originalPrice;
        private String sport;
        private String sportSlug;
        private String category;
        private String categorySlug;
        private String series;
        private String badge;
        private Double rating;
        private Integer reviewsCount;
        private Boolean isFeatured;
        private Boolean isNew;
        private List<VariantResponse> variants;
        private List<ImageResponse> images;
        private List<SpecResponse> specs;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VariantResponse {
        private Long id;
        private String colorName;
        private String colorCode;
        private String size;
        private String sku;
        private BigDecimal price;
        private Integer stockQuantity;
        private String imageUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ImageResponse {
        private Long id;
        private String imageUrl;
        private Boolean isPrimary;
        private Integer displayOrder;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SpecResponse {
        private String key;
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RacquetSelectorRequest {
        private String sport; // badminton, tennis
        private String skillLevel; // beginner, intermediate, advanced
        private String playStyle; // offensive, defensive, all-around
        private String priority; // power, speed, control
        private BigDecimal maxBudget;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RecommendationResult {
        private String recommendationNote;
        private List<ProductResponse> recommendedRacquets;
    }
}
