package com.yonex.service;

import com.yonex.dto.OrderDto;
import com.yonex.entity.Cart;
import com.yonex.entity.CartItem;
import com.yonex.entity.ProductVariant;
import com.yonex.entity.User;
import com.yonex.exception.ApiException;
import com.yonex.exception.ResourceNotFoundException;
import com.yonex.repository.CartItemRepository;
import com.yonex.repository.CartRepository;
import com.yonex.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository variantRepository;
    private final AuthService authService;

    private static final BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("99.00");

    @Transactional
    public Cart getOrCreateUserCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .user(user)
                        .items(new ArrayList<>())
                        .build()));
    }

    @Transactional(readOnly = true)
    public OrderDto.CartResponse getCart() {
        User user = authService.getCurrentUser();
        Cart cart = getOrCreateUserCart(user);
        return mapToCartResponse(cart);
    }

    @Transactional
    public OrderDto.CartResponse addToCart(OrderDto.AddCartItemRequest req) {
        User user = authService.getCurrentUser();
        Cart cart = getOrCreateUserCart(user);

        ProductVariant variant = variantRepository.findById(req.getVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("Biến thể sản phẩm không tồn tại: " + req.getVariantId()));

        if (variant.getStockQuantity() < req.getQuantity()) {
            throw new ApiException(String.format("Tồn kho không đủ (Hiện còn: %d)", variant.getStockQuantity()));
        }

        BigDecimal activePrice = variant.getPrice() != null ? variant.getPrice() : variant.getProduct().getPrice();

        CartItem item = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variant.getId())
                .orElse(null);

        if (item != null) {
            int newQty = item.getQuantity() + req.getQuantity();
            if (newQty > variant.getStockQuantity()) {
                throw new ApiException(String.format("Tổng số lượng vượt quá tồn kho (Hiện còn: %d)", variant.getStockQuantity()));
            }
            item.setQuantity(newQty);
            item.setUnitPrice(activePrice);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .variant(variant)
                    .quantity(req.getQuantity())
                    .unitPrice(activePrice)
                    .build();
            newItem = cartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        return mapToCartResponse(cart);
    }

    @Transactional
    public OrderDto.CartResponse updateQuantity(Long itemId, OrderDto.UpdateCartItemRequest req) {
        User user = authService.getCurrentUser();
        Cart cart = getOrCreateUserCart(user);

        CartItem item = cartItemRepository.findById(itemId)
                .filter(i -> i.getCart().getId().equals(cart.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mục giỏ hàng không tồn tại"));

        if (req.getQuantity() > item.getVariant().getStockQuantity()) {
            throw new ApiException(String.format("Số lượng vượt quá tồn kho hiện có (%d)", item.getVariant().getStockQuantity()));
        }

        item.setQuantity(req.getQuantity());
        cartItemRepository.save(item);

        Cart updatedCart = cartRepository.findById(cart.getId()).orElse(cart);
        return mapToCartResponse(updatedCart);
    }

    @Transactional
    public OrderDto.CartResponse removeItem(Long itemId) {
        User user = authService.getCurrentUser();
        Cart cart = getOrCreateUserCart(user);

        CartItem item = cartItemRepository.findById(itemId)
                .filter(i -> i.getCart().getId().equals(cart.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Mục giỏ hàng không tồn tại"));

        cartItemRepository.delete(item);

        Cart updatedCart = cartRepository.findById(cart.getId()).orElse(cart);
        return mapToCartResponse(updatedCart);
    }

    @Transactional
    public void clearCart() {
        User user = authService.getCurrentUser();
        Cart cart = getOrCreateUserCart(user);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    public OrderDto.CartResponse mapToCartResponse(Cart cart) {
        List<CartItem> dbItems = cartItemRepository.findByCartId(cart.getId());
        List<OrderDto.CartItemResponse> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        int totalItems = 0;

        for (CartItem item : dbItems) {
            ProductVariant v = item.getVariant();
            BigDecimal unitPrice = v.getPrice() != null ? v.getPrice() : v.getProduct().getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
            subtotal = subtotal.add(lineTotal);
            totalItems += item.getQuantity();

            items.add(OrderDto.CartItemResponse.builder()
                    .id(item.getId())
                    .variantId(v.getId())
                    .productId(v.getProduct().getId())
                    .productName(v.getProduct().getName())
                    .productSlug(v.getProduct().getSlug())
                    .colorName(v.getColorName())
                    .colorCode(v.getColorCode())
                    .size(v.getSize())
                    .sku(v.getSku())
                    .unitPrice(unitPrice)
                    .quantity(item.getQuantity())
                    .lineTotal(lineTotal)
                    .imageUrl(v.getImageUrl())
                    .availableStock(v.getStockQuantity())
                    .build());
        }

        return OrderDto.CartResponse.builder()
                .id(cart.getId())
                .items(items)
                .subtotal(subtotal)
                .totalItems(totalItems)
                .freeShippingThreshold(FREE_SHIPPING_THRESHOLD)
                .eligibleForFreeShipping(subtotal.compareTo(FREE_SHIPPING_THRESHOLD) >= 0)
                .build();
    }
}
