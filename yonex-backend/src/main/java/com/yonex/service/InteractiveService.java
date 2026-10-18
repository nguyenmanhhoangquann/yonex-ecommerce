package com.yonex.service;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.ProductDto;
import com.yonex.entity.*;
import com.yonex.exception.ApiException;
import com.yonex.exception.ResourceNotFoundException;
import com.yonex.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InteractiveService {

    private final WishlistItemRepository wishlistRepository;
    private final ProductReviewRepository reviewRepository;
    private final NewsArticleRepository newsRepository;
    private final AthleteRepository athleteRepository;
    private final StoreRepository storeRepository;
    private final FaqRepository faqRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final AuthService authService;

    // Wishlist
    @Transactional(readOnly = true)
    public List<ProductDto.ProductResponse> getWishlist() {
        User user = authService.getCurrentUser();
        List<WishlistItem> items = wishlistRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return items.stream()
                .map(item -> productService.toDto(item.getProduct()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addToWishlist(Long productId) {
        User user = authService.getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + productId));

        if (!wishlistRepository.existsByUserIdAndProductId(user.getId(), productId)) {
            WishlistItem item = WishlistItem.builder()
                    .user(user)
                    .product(product)
                    .build();
            wishlistRepository.save(item);
        }
    }

    @Transactional
    public void removeFromWishlist(Long productId) {
        User user = authService.getCurrentUser();
        wishlistRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }

    // Reviews
    @Transactional(readOnly = true)
    public List<ProductReview> getReviews(Long productId) {
        return reviewRepository.findByProductIdAndIsApprovedTrueOrderByCreatedAtDesc(productId);
    }

    @Transactional
    public ProductReview createReview(Long productId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new ApiException("Số sao đánh giá phải từ 1 đến 5");
        }

        User user = authService.getCurrentUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + productId));

        if (reviewRepository.existsByProductIdAndUserId(productId, user.getId())) {
            throw new ApiException("Bạn đã đánh giá sản phẩm này trước đó");
        }

        ProductReview review = ProductReview.builder()
                .product(product)
                .user(user)
                .rating(rating)
                .comment(comment != null ? comment.trim() : null)
                .isApproved(true)
                .build();

        review = reviewRepository.save(review);

        // Recalculate average rating & reviewsCount on product
        Double avg = reviewRepository.calculateAverageRating(productId);
        int count = reviewRepository.countApprovedReviews(productId);

        product.setRating(avg != null ? Math.round(avg * 10.0) / 10.0 : 5.0);
        product.setReviewsCount(count);
        productRepository.save(product);

        return review;
    }

    // News
    @Transactional(readOnly = true)
    public Page<NewsArticle> getNews(String sport, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        if (sport != null && !sport.isBlank()) {
            return newsRepository.findBySportAndIsPublishedTrueOrderByPublishedAtDesc(sport.trim(), pageable);
        }
        return newsRepository.findByIsPublishedTrueOrderByPublishedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public NewsArticle getNewsBySlug(String slug) {
        return newsRepository.findBySlugAndIsPublishedTrue(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Bài viết tin tức không tồn tại: " + slug));
    }

    @Transactional(readOnly = true)
    public List<NewsArticle> getFeaturedNews() {
        return newsRepository.findTop6ByIsFeaturedTrueAndIsPublishedTrueOrderByPublishedAtDesc();
    }

    // Athletes
    @Transactional(readOnly = true)
    public List<Athlete> getAthletes(String sport) {
        if (sport != null && !sport.isBlank()) {
            return athleteRepository.findBySportAndActiveTrue(sport.trim());
        }
        return athleteRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Athlete getAthleteById(Long id) {
        return athleteRepository.findById(id)
                .filter(a -> Boolean.TRUE.equals(a.getActive()))
                .orElseThrow(() -> new ResourceNotFoundException("Vận động viên không tồn tại: " + id));
    }

    // Stores
    @Transactional(readOnly = true)
    public List<Store> getStores(String city) {
        if (city != null && !city.isBlank()) {
            return storeRepository.findByCityIgnoreCaseAndActiveTrue(city.trim());
        }
        return storeRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .filter(s -> Boolean.TRUE.equals(s.getActive()))
                .orElseThrow(() -> new ResourceNotFoundException("Cửa hàng không tồn tại: " + id));
    }

    // FAQs
    @Transactional(readOnly = true)
    public List<Faq> getFaqs(String category) {
        if (category != null && !category.isBlank()) {
            return faqRepository.findByCategoryIgnoreCaseAndActiveTrueOrderByDisplayOrderAsc(category.trim());
        }
        return faqRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }
}
