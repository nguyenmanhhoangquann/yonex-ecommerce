package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.ProductDto;
import com.yonex.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Tìm kiếm, lọc, chi tiết sản phẩm và so sánh thông số")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Tìm kiếm và lọc sản phẩm có phân trang")
    public ResponseEntity<ApiResponse<Page<ProductDto.ProductResponse>>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String series,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction
    ) {
        Page<ProductDto.ProductResponse> result = productService.search(
                keyword, sport, category, series, minPrice, maxPrice, page, size, sortBy, direction
        );
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách sản phẩm thành công", result));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết sản phẩm theo ID")
    public ResponseEntity<ApiResponse<ProductDto.ProductResponse>> getById(@PathVariable Long id) {
        ProductDto.ProductResponse product = productService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết sản phẩm thành công", product));
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Lấy chi tiết sản phẩm theo slug")
    public ResponseEntity<ApiResponse<ProductDto.ProductResponse>> getBySlug(@PathVariable String slug) {
        ProductDto.ProductResponse product = productService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết sản phẩm thành công", product));
    }

    @GetMapping("/featured")
    @Operation(summary = "Lấy danh sách sản phẩm nổi bật (Featured)")
    public ResponseEntity<ApiResponse<List<ProductDto.ProductResponse>>> getFeatured() {
        List<ProductDto.ProductResponse> list = productService.getFeatured();
        return ResponseEntity.ok(ApiResponse.ok("Lấy sản phẩm nổi bật thành công", list));
    }

    @GetMapping("/new-arrivals")
    @Operation(summary = "Lấy danh sách sản phẩm mới về (New Arrivals)")
    public ResponseEntity<ApiResponse<List<ProductDto.ProductResponse>>> getNewArrivals() {
        List<ProductDto.ProductResponse> list = productService.getNewArrivals();
        return ResponseEntity.ok(ApiResponse.ok("Lấy sản phẩm mới thành công", list));
    }

    @GetMapping("/compare")
    @Operation(summary = "So sánh thông số kỹ thuật giữa các sản phẩm (tối đa 5 sản phẩm)")
    public ResponseEntity<ApiResponse<List<ProductDto.ProductResponse>>> compare(
            @RequestParam List<Long> ids
    ) {
        List<ProductDto.ProductResponse> list = productService.compare(ids);
        return ResponseEntity.ok(ApiResponse.ok("Lấy dữ liệu so sánh sản phẩm thành công", list));
    }
}
