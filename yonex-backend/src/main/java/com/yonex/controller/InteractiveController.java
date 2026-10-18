package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.ProductDto;
import com.yonex.entity.*;
import com.yonex.service.InteractiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Content & Social", description = "Tin tức, Vận động viên, Cửa hàng, FAQ, Yêu thích và Đánh giá")
public class InteractiveController {

    private final InteractiveService interactiveService;

    // Wishlist
    @GetMapping("/wishlist")
    @Operation(summary = "Xem danh sách sản phẩm yêu thích của tôi")
    public ResponseEntity<ApiResponse<List<ProductDto.ProductResponse>>> getWishlist() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách yêu thích thành công", interactiveService.getWishlist()));
    }

    @PostMapping("/wishlist/{productId}")
    @Operation(summary = "Thêm sản phẩm vào danh sách yêu thích")
    public ResponseEntity<ApiResponse<Void>> addToWishlist(@PathVariable Long productId) {
        interactiveService.addToWishlist(productId);
        return ResponseEntity.ok(ApiResponse.ok("Đã thêm vào danh sách yêu thích", null));
    }

    @DeleteMapping("/wishlist/{productId}")
    @Operation(summary = "Xóa sản phẩm khỏi danh sách yêu thích")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable Long productId) {
        interactiveService.removeFromWishlist(productId);
        return ResponseEntity.ok(ApiResponse.ok("Đã xóa khỏi danh sách yêu thích", null));
    }

    // Reviews
    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "Xem danh sách đánh giá của sản phẩm")
    public ResponseEntity<ApiResponse<List<ProductReview>>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy đánh giá thành công", interactiveService.getReviews(productId)));
    }

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "Gửi đánh giá và nhận xét sản phẩm")
    public ResponseEntity<ApiResponse<ProductReview>> createReview(
            @PathVariable Long productId,
            @RequestBody CreateReviewBody body
    ) {
        ProductReview review = interactiveService.createReview(productId, body.getRating(), body.getComment());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Gửi đánh giá thành công", review));
    }

    // News
    @GetMapping("/news")
    @Operation(summary = "Xem danh sách tin tức có phân trang và lọc theo môn")
    public ResponseEntity<ApiResponse<Page<NewsArticle>>> getNews(
            @RequestParam(required = false) String sport,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy tin tức thành công", interactiveService.getNews(sport, page, size)));
    }

    @GetMapping("/news/{slug}")
    @Operation(summary = "Xem chi tiết bài viết tin tức theo slug")
    public ResponseEntity<ApiResponse<NewsArticle>> getNewsBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy chi tiết tin tức thành công", interactiveService.getNewsBySlug(slug)));
    }

    @GetMapping("/news/featured")
    @Operation(summary = "Xem tin tức nổi bật")
    public ResponseEntity<ApiResponse<List<NewsArticle>>> getFeaturedNews() {
        return ResponseEntity.ok(ApiResponse.ok("Lấy tin nổi bật thành công", interactiveService.getFeaturedNews()));
    }

    // Athletes
    @GetMapping("/athletes")
    @Operation(summary = "Xem danh sách vận động viên Team Yonex")
    public ResponseEntity<ApiResponse<List<Athlete>>> getAthletes(@RequestParam(required = false) String sport) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách vận động viên thành công", interactiveService.getAthletes(sport)));
    }

    @GetMapping("/athletes/{id}")
    @Operation(summary = "Xem hồ sơ chi tiết vận động viên")
    public ResponseEntity<ApiResponse<Athlete>> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin vận động viên thành công", interactiveService.getAthleteById(id)));
    }

    // Stores
    @GetMapping("/stores")
    @Operation(summary = "Xem danh sách cửa hàng đại lý và showroom")
    public ResponseEntity<ApiResponse<List<Store>>> getStores(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách cửa hàng thành công", interactiveService.getStores(city)));
    }

    @GetMapping("/stores/{id}")
    @Operation(summary = "Xem thông tin chi tiết một cửa hàng")
    public ResponseEntity<ApiResponse<Store>> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin cửa hàng thành công", interactiveService.getStoreById(id)));
    }

    // FAQs
    @GetMapping("/faqs")
    @Operation(summary = "Xem danh sách câu hỏi thường gặp")
    public ResponseEntity<ApiResponse<List<Faq>>> getFaqs(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách FAQ thành công", interactiveService.getFaqs(category)));
    }

    @Data
    public static class CreateReviewBody {
        private int rating;
        private String comment;
    }
}
