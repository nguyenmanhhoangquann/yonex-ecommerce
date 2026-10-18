package com.yonex.service;

import com.yonex.dto.ProductDto;
import com.yonex.entity.Product;
import com.yonex.exception.ApiException;
import com.yonex.exception.ResourceNotFoundException;
import com.yonex.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto.ProductResponse toDto(Product p) {
        if (p == null) return null;

        List<ProductDto.VariantResponse> variants = p.getVariants() != null
                ? p.getVariants().stream().map(v -> ProductDto.VariantResponse.builder()
                .id(v.getId())
                .colorName(v.getColorName())
                .colorCode(v.getColorCode())
                .size(v.getSize())
                .sku(v.getSku())
                .price(v.getPrice() != null ? v.getPrice() : p.getPrice())
                .stockQuantity(v.getStockQuantity())
                .imageUrl(v.getImageUrl())
                .build()).collect(Collectors.toList())
                : List.of();

        List<ProductDto.ImageResponse> images = p.getImages() != null
                ? p.getImages().stream().map(img -> ProductDto.ImageResponse.builder()
                .id(img.getId())
                .imageUrl(img.getImageUrl())
                .isPrimary(img.getIsPrimary())
                .displayOrder(img.getDisplayOrder())
                .build()).collect(Collectors.toList())
                : List.of();

        List<ProductDto.SpecResponse> specs = p.getSpecs() != null
                ? p.getSpecs().stream().map(s -> ProductDto.SpecResponse.builder()
                .key(s.getSpecKey())
                .value(s.getSpecValue())
                .build()).collect(Collectors.toList())
                : List.of();

        return ProductDto.ProductResponse.builder()
                .id(p.getId())
                .sku(p.getSku())
                .name(p.getName())
                .slug(p.getSlug())
                .shortDescription(p.getShortDescription())
                .description(p.getDescription())
                .price(p.getPrice())
                .originalPrice(p.getOriginalPrice())
                .sport(p.getSport() != null ? p.getSport().getName() : null)
                .sportSlug(p.getSport() != null ? p.getSport().getSlug() : null)
                .category(p.getCategory() != null ? p.getCategory().getName() : null)
                .categorySlug(p.getCategory() != null ? p.getCategory().getSlug() : null)
                .series(p.getSeries())
                .badge(p.getBadge())
                .rating(p.getRating())
                .reviewsCount(p.getReviewsCount())
                .isFeatured(p.getIsFeatured())
                .isNew(p.getIsNew())
                .variants(variants)
                .images(images)
                .specs(specs)
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ProductDto.ProductResponse> search(
            String keyword,
            String sport,
            String category,
            String series,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction != null ? direction : "ASC"),
                (sortBy != null && !sortBy.isBlank()) ? sortBy : "id");
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), sort);

        Page<Product> productPage = productRepository.searchProducts(
                keyword != null && !keyword.isBlank() ? keyword.trim() : null,
                sport != null && !sport.isBlank() ? sport.trim().toLowerCase() : null,
                category != null && !category.isBlank() ? category.trim().toLowerCase() : null,
                series != null && !series.isBlank() ? series.trim() : null,
                minPrice,
                maxPrice,
                pageable
        );

        return productPage.map(this::toDto);
    }

    @Transactional(readOnly = true)
    public ProductDto.ProductResponse getById(Long id) {
        Product product = productRepository.findById(id)
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm", "id", id));
        return toDto(product);
    }

    @Transactional(readOnly = true)
    public ProductDto.ProductResponse getBySlug(String slug) {
        Product product = productRepository.findBySlugAndActiveTrue(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm", "slug", slug));
        return toDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDto.ProductResponse> getFeatured() {
        return productRepository.findTop8ByIsFeaturedTrueAndActiveTrueOrderByCreatedAtDesc()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto.ProductResponse> getNewArrivals() {
        return productRepository.findTop8ByIsNewTrueAndActiveTrueOrderByCreatedAtDesc()
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto.ProductResponse> compare(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new ApiException("Vui lòng cung cấp danh sách ID sản phẩm để so sánh");
        }
        if (ids.size() > 5) {
            throw new ApiException("Chỉ cho phép so sánh tối đa 5 sản phẩm cùng lúc");
        }
        List<Product> products = productRepository.findByIdInAndActiveTrue(ids);
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto.RecommendationResult recommendRacquet(ProductDto.RacquetSelectorRequest req) {
        // Logic dựa trên triết học dòng vợt Yonex chính hãng:
        // ASTROX -> Head-heavy, Power, Steep Smash (Offensive)
        // NANOFLARE -> Head-light, Lightning Speed, Rapid Drive (Speed / Counter)
        // ARCSABER -> Even-balance, Shuttle-hold, Ultimate Control (Control / All-Around)
        // EZONE -> Easy Power & Comfort (Tennis All-Around / Power)
        // VCORE -> Spin Monster & Precision Control (Tennis Spin / Precision)
        String preferredSeries = null;
        String note = "Gợi ý được tính toán dựa trên phong cách chơi và ưu tiên của bạn:";

        if ("badminton".equalsIgnoreCase(req.getSport())) {
            if ("power".equalsIgnoreCase(req.getPriority()) || "offensive".equalsIgnoreCase(req.getPlayStyle())) {
                preferredSeries = "ASTROX";
                note = "Dành cho lối đánh tấn công uy lực: Dòng ASTROX với công nghệ Rotational Generator System tạo ra những cú đập cầu cắm dốc và uy lực tối đa.";
            } else if ("speed".equalsIgnoreCase(req.getPriority())) {
                preferredSeries = "NANOFLARE";
                note = "Dành cho lối đánh tốc độ, phản tạt nhanh: Dòng NANOFLARE đầu nhẹ với Sonic Flare System giúp tăng tốc vung vợt và phản xạ chớp nhoáng.";
            } else {
                preferredSeries = "ARCSABER";
                note = "Dành cho lối đánh công thủ toàn diện và kiểm soát: Dòng ARCSABER giữ cầu lâu hơn trên mặt vợt để điều cầu chính xác tuyệt đối.";
            }
        } else if ("tennis".equalsIgnoreCase(req.getSport())) {
            if ("power".equalsIgnoreCase(req.getPriority())) {
                preferredSeries = "EZONE";
                note = "Dành cho người chơi tìm kiếm sức mạnh bùng nổ và sự êm ái: Dòng EZONE tối ưu điểm ngọt Isometric và giảm chấn VDM.";
            } else {
                preferredSeries = "VCORE";
                note = "Dành cho người chơi chuộng lối đánh bóng xoáy (spin) và kiểm soát đường bóng: Dòng VCORE tạo quỹ đạo bóng xoáy cắm hiểm hóc.";
            }
        }

        List<Product> matched;
        if (preferredSeries != null) {
            Page<Product> paged = productRepository.searchProducts(
                    null,
                    req.getSport() != null ? req.getSport().toLowerCase() : null,
                    "racquets",
                    preferredSeries,
                    null,
                    req.getMaxBudget(),
                    PageRequest.of(0, 4)
            );
            matched = paged.getContent();
        } else {
            Page<Product> paged = productRepository.searchProducts(
                    null,
                    req.getSport() != null ? req.getSport().toLowerCase() : null,
                    "racquets",
                    null,
                    null,
                    req.getMaxBudget(),
                    PageRequest.of(0, 4)
            );
            matched = paged.getContent();
        }

        return ProductDto.RecommendationResult.builder()
                .recommendationNote(note)
                .recommendedRacquets(matched.stream().map(this::toDto).collect(Collectors.toList()))
                .build();
    }
}
