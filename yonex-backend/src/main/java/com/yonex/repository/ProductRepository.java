package com.yonex.repository;

import com.yonex.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlugAndActiveTrue(String slug);
    Optional<Product> findBySku(String sku);
    boolean existsBySku(String sku);
    boolean existsBySlug(String slug);

    List<Product> findByIdInAndActiveTrue(List<Long> ids);

    List<Product> findTop8ByIsFeaturedTrueAndActiveTrueOrderByCreatedAtDesc();

    List<Product> findTop8ByIsNewTrueAndActiveTrueOrderByCreatedAtDesc();

    List<Product> findTop4ByCategoryIdAndIdNotAndActiveTrue(Long categoryId, Long productId);

    @Query("SELECT p FROM Product p " +
           "WHERE p.active = true " +
           "AND (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.shortDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:sportSlug IS NULL OR (p.sport IS NOT NULL AND p.sport.slug = :sportSlug)) " +
           "AND (:categorySlug IS NULL OR (p.category IS NOT NULL AND p.category.slug = :categorySlug)) " +
           "AND (:series IS NULL OR LOWER(p.series) = LOWER(:series)) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("sportSlug") String sportSlug,
            @Param("categorySlug") String categorySlug,
            @Param("series") String series,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable
    );

    long countByActiveTrue();
}
