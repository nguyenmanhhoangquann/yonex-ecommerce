package com.yonex.service;

import com.yonex.dto.OrderDto;
import com.yonex.entity.*;
import com.yonex.enums.OrderStatus;
import com.yonex.enums.PaymentMethod;
import com.yonex.enums.PaymentStatus;
import com.yonex.exception.ApiException;
import com.yonex.exception.ResourceNotFoundException;
import com.yonex.repository.CartItemRepository;
import com.yonex.repository.OrderItemRepository;
import com.yonex.repository.OrderRepository;
import com.yonex.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository variantRepository;
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final AuthService authService;

    private static final BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("99.00");
    private static final BigDecimal STANDARD_SHIPPING_FEE = new BigDecimal("10.00");

    @Transactional
    public OrderDto.OrderResponse createOrderFromCart(OrderDto.CreateOrderRequest req) {
        User user = authService.getCurrentUser();
        Cart cart = cartService.getOrCreateUserCart(user);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems == null || cartItems.isEmpty()) {
            throw new ApiException("Giỏ hàng của bạn đang trống");
        }

        // 1. Verify all stock and calculate fresh total
        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cartItems) {
            ProductVariant variant = variantRepository.findById(ci.getVariant().getId())
                    .orElseThrow(() -> new ApiException("Sản phẩm không còn khả dụng"));

            if (variant.getStockQuantity() < ci.getQuantity()) {
                throw new ApiException(String.format("Sản phẩm '%s (%s)' không đủ tồn kho (Còn lại: %d, Yêu cầu: %d)",
                        variant.getProduct().getName(), variant.getColorName(), variant.getStockQuantity(), ci.getQuantity()));
            }

            BigDecimal unitPrice = variant.getPrice() != null ? variant.getPrice() : variant.getProduct().getPrice();
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity()));
            subtotal = subtotal.add(itemTotal);

            // Deduct stock atomically
            variant.setStockQuantity(variant.getStockQuantity() - ci.getQuantity());
            variantRepository.save(variant);

            OrderItem orderItem = OrderItem.builder()
                    .variant(variant)
                    .productName(variant.getProduct().getName())
                    .colorName(variant.getColorName())
                    .size(variant.getSize())
                    .sku(variant.getSku() != null ? variant.getSku() : variant.getProduct().getSku())
                    .unitPrice(unitPrice)
                    .quantity(ci.getQuantity())
                    .subtotal(itemTotal)
                    .build();

            orderItems.add(orderItem);
        }

        BigDecimal shippingFee = subtotal.compareTo(FREE_SHIPPING_THRESHOLD) >= 0 ? BigDecimal.ZERO : STANDARD_SHIPPING_FEE;
        BigDecimal totalAmount = subtotal.add(shippingFee);

        String orderCode = "YNX-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
                + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        PaymentStatus initialPaymentStatus = req.getPaymentMethod() == PaymentMethod.TEST_GATEWAY
                ? PaymentStatus.PAID
                : PaymentStatus.UNPAID;

        Order order = Order.builder()
                .orderCode(orderCode)
                .user(user)
                .status(OrderStatus.PENDING)
                .paymentStatus(initialPaymentStatus)
                .paymentMethod(req.getPaymentMethod())
                .totalAmount(totalAmount)
                .shippingFee(shippingFee)
                .recipientName(req.getRecipientName().trim())
                .phone(req.getPhone().trim())
                .shippingAddress(req.getShippingAddress().trim())
                .note(req.getNote())
                .build();

        order = orderRepository.save(order);

        for (OrderItem oi : orderItems) {
            oi.setOrder(order);
        }
        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);

        // Clear cart after checkout
        cartItemRepository.deleteByCartId(cart.getId());

        return mapToOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto.OrderResponse> getMyOrders() {
        User user = authService.getCurrentUser();
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return orders.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto.OrderResponse getOrderById(Long id) {
        User user = authService.getCurrentUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng", "id", id));

        // Verify ownership (or Admin)
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().name().equals("ROLE_ADMIN")) {
            throw new ApiException("Bạn không có quyền xem đơn hàng này");
        }

        return mapToOrderResponse(order);
    }

    @Transactional
    public OrderDto.OrderResponse cancelOrder(Long id) {
        User user = authService.getCurrentUser();
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng", "id", id));

        // Must be owner or admin
        if (!order.getUser().getId().equals(user.getId()) && !user.getRole().name().equals("ROLE_ADMIN")) {
            throw new ApiException("Bạn không có quyền thao tác trên đơn hàng này");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ApiException("Đơn hàng này đã bị hủy trước đó");
        }

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new ApiException("Không thể hủy đơn hàng ở trạng thái " + order.getStatus());
        }

        // Return stock atomically without duplicate rollback
        for (OrderItem oi : order.getItems()) {
            if (oi.getVariant() != null) {
                ProductVariant variant = oi.getVariant();
                variant.setStockQuantity(variant.getStockQuantity() + oi.getQuantity());
                variantRepository.save(variant);
            }
        }

        order.setStatus(OrderStatus.CANCELLED);
        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            order.setPaymentStatus(PaymentStatus.REFUNDED);
        }

        order = orderRepository.save(order);
        return mapToOrderResponse(order);
    }

    @Transactional
    public OrderDto.OrderResponse updateOrderStatus(Long id, OrderStatus newStatus, PaymentStatus paymentStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng", "id", id));

        // If transitioning to CANCELLED from non-cancelled, restock
        if (newStatus == OrderStatus.CANCELLED && order.getStatus() != OrderStatus.CANCELLED) {
            for (OrderItem oi : order.getItems()) {
                if (oi.getVariant() != null) {
                    ProductVariant v = oi.getVariant();
                    v.setStockQuantity(v.getStockQuantity() + oi.getQuantity());
                    variantRepository.save(v);
                }
            }
        }

        order.setStatus(newStatus);
        if (paymentStatus != null) {
            order.setPaymentStatus(paymentStatus);
        }

        order = orderRepository.save(order);
        return mapToOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderDto.OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAllByOrderByCreatedAtDesc(pageable).map(this::mapToOrderResponse);
    }

    public OrderDto.OrderResponse mapToOrderResponse(Order order) {
        List<OrderDto.OrderItemResponse> itemResponses = order.getItems() != null
                ? order.getItems().stream().map(oi -> OrderDto.OrderItemResponse.builder()
                .id(oi.getId())
                .variantId(oi.getVariant() != null ? oi.getVariant().getId() : null)
                .productName(oi.getProductName())
                .colorName(oi.getColorName())
                .size(oi.getSize())
                .sku(oi.getSku())
                .unitPrice(oi.getUnitPrice())
                .quantity(oi.getQuantity())
                .subtotal(oi.getSubtotal())
                .build()).collect(Collectors.toList())
                : List.of();

        return OrderDto.OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .totalAmount(order.getTotalAmount())
                .shippingFee(order.getShippingFee())
                .recipientName(order.getRecipientName())
                .phone(order.getPhone())
                .shippingAddress(order.getShippingAddress())
                .note(order.getNote())
                .items(itemResponses)
                .createdAt(order.getCreatedAt())
                .build();
    }
}
