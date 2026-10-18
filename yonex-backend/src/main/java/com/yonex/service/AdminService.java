package com.yonex.service;

import com.yonex.dto.AdminDto;
import com.yonex.dto.OrderDto;
import com.yonex.dto.ProductDto;
import com.yonex.entity.*;
import com.yonex.enums.OrderStatus;
import com.yonex.enums.PaymentStatus;
import com.yonex.exception.ApiException;
import com.yonex.exception.ResourceNotFoundException;
import com.yonex.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final OrderRepository orderRepository;
    private final SportRepository sportRepository;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final OrderService orderService;

    @Transactional(readOnly = true)
    public AdminDto.DashboardStats getDashboard() {
        long totalUsers = userRepository.count();
        long totalProducts = productRepository.countByActiveTrue();
        long totalOrders = orderRepository.count();

        long outOfStock = variantRepository.findAll().stream()
                .filter(v -> v.getStockQuantity() <= 0)
                .count();

        Map<String, Long> statusMap = new HashMap<>();
        for (OrderStatus st : OrderStatus.values()) {
            statusMap.put(st.name(), orderRepository.countByStatus(st));
        }

        // Only count delivered/confirmed paid orders as legitimate revenue
        BigDecimal revenue = orderRepository.calculateRevenue(List.of(OrderStatus.CONFIRMED, OrderStatus.PROCESSING, OrderStatus.SHIPPED, OrderStatus.DELIVERED));
        if (revenue == null) {
            revenue = BigDecimal.ZERO;
        }

        List<OrderDto.OrderResponse> recentOrders = orderRepository.findTop5ByOrderByCreatedAtDesc()
                .stream().map(orderService::mapToOrderResponse).collect(Collectors.toList());

        return AdminDto.DashboardStats.builder()
                .totalUsers(totalUsers)
                .totalActiveProducts(totalProducts)
                .totalOutOfStockVariants(outOfStock)
                .totalOrders(totalOrders)
                .ordersByStatus(statusMap)
                .totalRevenue(revenue)
                .recentOrders(recentOrders)
                .build();
    }

    @Transactional
    public ProductDto.ProductResponse createProduct(AdminDto.SaveProductRequest req) {
        if (productRepository.existsBySku(req.getSku())) {
            throw new ApiException("Mã SKU này đã tồn tại: " + req.getSku());
        }
        if (productRepository.existsBySlug(req.getSlug())) {
            throw new ApiException("Slug này đã tồn tại: " + req.getSlug());
        }

        Sport sport = req.getSportId() != null
                ? sportRepository.findById(req.getSportId()).orElse(null)
                : null;
        Category category = req.getCategoryId() != null
                ? categoryRepository.findById(req.getCategoryId()).orElse(null)
                : null;

        Product p = Product.builder()
                .sku(req.getSku().trim().toUpperCase())
                .name(req.getName().trim())
                .slug(req.getSlug().trim().toLowerCase())
                .shortDescription(req.getShortDescription())
                .description(req.getDescription())
                .price(req.getPrice())
                .originalPrice(req.getOriginalPrice())
                .sport(sport)
                .category(category)
                .series(req.getSeries())
                .badge(req.getBadge())
                .isFeatured(Boolean.TRUE.equals(req.getIsFeatured()))
                .isNew(Boolean.TRUE.equals(req.getIsNew()))
                .active(req.getActive() != null ? req.getActive() : true)
                .build();

        p = productRepository.save(p);
        return productService.toDto(p);
    }

    @Transactional
    public ProductDto.ProductResponse updateProduct(Long id, AdminDto.SaveProductRequest req) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + id));

        Sport sport = req.getSportId() != null
                ? sportRepository.findById(req.getSportId()).orElse(null)
                : p.getSport();
        Category category = req.getCategoryId() != null
                ? categoryRepository.findById(req.getCategoryId()).orElse(null)
                : p.getCategory();

        p.setName(req.getName().trim());
        p.setShortDescription(req.getShortDescription());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setOriginalPrice(req.getOriginalPrice());
        p.setSport(sport);
        p.setCategory(category);
        p.setSeries(req.getSeries());
        p.setBadge(req.getBadge());
        if (req.getIsFeatured() != null) p.setIsFeatured(req.getIsFeatured());
        if (req.getIsNew() != null) p.setIsNew(req.getIsNew());
        if (req.getActive() != null) p.setActive(req.getActive());

        p = productRepository.save(p);
        return productService.toDto(p);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + id));
        // Soft-delete to preserve order history
        p.setActive(false);
        productRepository.save(p);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User toggleUserStatus(Long userId, Boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại: " + userId));
        user.setActive(active);
        return userRepository.save(user);
    }
}
