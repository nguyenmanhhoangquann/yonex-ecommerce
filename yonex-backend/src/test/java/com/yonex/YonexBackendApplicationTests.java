package com.yonex;

import com.yonex.dto.AuthDto;
import com.yonex.dto.OrderDto;
import com.yonex.dto.ProductDto;
import com.yonex.entity.Product;
import com.yonex.entity.ProductVariant;
import com.yonex.entity.User;
import com.yonex.enums.OrderStatus;
import com.yonex.enums.PaymentMethod;
import com.yonex.exception.ApiException;
import com.yonex.repository.ProductRepository;
import com.yonex.repository.ProductVariantRepository;
import com.yonex.repository.UserRepository;
import com.yonex.service.AuthService;
import com.yonex.service.CartService;
import com.yonex.service.OrderService;
import com.yonex.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional
class YonexBackendApplicationTests {

    @Autowired
    private AuthService authService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository variantRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private void authenticateAsCustomer() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("customer@yonex-demo.com");
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DisplayName("1. Đăng ký tài khoản CUSTOMER mới và kiểm tra mật khẩu đã mã hóa BCrypt")
    void testRegisterNewCustomer() {
        AuthDto.RegisterRequest req = AuthDto.RegisterRequest.builder()
                .email("newuser@yonex-test.com")
                .password("Password@123")
                .fullName("Test Player")
                .phone("0912345678")
                .build();

        AuthDto.AuthResponse resp = authService.register(req);

        assertNotNull(resp.getAccessToken());
        assertNotNull(resp.getRefreshToken());
        assertEquals("ROLE_CUSTOMER", resp.getRole());

        User saved = userRepository.findByEmail("newuser@yonex-test.com").orElse(null);
        assertNotNull(saved);
        assertNotEquals("Password@123", saved.getPassword());
    }

    @Test
    @DisplayName("2. Đăng nhập thành công và chặn đăng nhập với mật khẩu sai")
    void testLoginSuccessAndFailure() {
        AuthDto.LoginRequest successReq = new AuthDto.LoginRequest("customer@yonex-demo.com", "Customer@123456");
        AuthDto.AuthResponse resp = authService.login(successReq);
        assertNotNull(resp.getAccessToken());

        AuthDto.LoginRequest failReq = new AuthDto.LoginRequest("customer@yonex-demo.com", "WrongPassword!");
        assertThrows(BadCredentialsException.class, () -> authService.login(failReq));
    }

    @Test
    @DisplayName("3. Tìm kiếm, lọc sản phẩm theo từ khóa, môn thể thao và phân trang")
    void testSearchAndFilterProducts() {
        Page<ProductDto.ProductResponse> results = productService.search(
                "EZONE", "tennis", null, null, null, null, 0, 10, "price", "ASC"
        );

        assertNotNull(results);
        assertTrue(results.getTotalElements() > 0);
        assertTrue(results.getContent().stream().anyMatch(p -> p.getName().contains("EZONE")));
    }

    @Test
    @DisplayName("4. So sánh thông số kỹ thuật (Chặn khi vượt quá 5 sản phẩm)")
    void testCompareProductsLimit() {
        List<Product> all = productRepository.findAll();
        List<Long> ids = all.stream().map(Product::getId).limit(2).toList();

        List<ProductDto.ProductResponse> compared = productService.compare(ids);
        assertEquals(2, compared.size());

        List<Long> sixIds = List.of(1L, 2L, 3L, 4L, 5L, 6L);
        assertThrows(ApiException.class, () -> productService.compare(sixIds));
    }

    @Test
    @DisplayName("5. Thêm sản phẩm vào giỏ, cập nhật số lượng và tính tiền phía server")
    void testCartOperations() {
        authenticateAsCustomer();

        ProductVariant variant = variantRepository.findAll().stream()
                .filter(v -> v.getStockQuantity() > 5)
                .findFirst()
                .orElseThrow();

        // Add 2
        cartService.addToCart(new OrderDto.AddCartItemRequest(variant.getId(), 2));
        OrderDto.CartResponse cart = cartService.getCart();

        assertNotNull(cart);
        assertEquals(1, cart.getItems().size());
        assertEquals(2, cart.getTotalItems());
        assertEquals(0, cart.getSubtotal().compareTo(variant.getPrice().multiply(BigDecimal.valueOf(2))));

        // Update quantity to 3
        Long itemId = cart.getItems().get(0).getId();
        cartService.updateQuantity(itemId, new OrderDto.UpdateCartItemRequest(3));

        OrderDto.CartResponse updated = cartService.getCart();
        assertEquals(3, updated.getTotalItems());
    }

    @Test
    @DisplayName("6. Đặt hàng thành công, trừ tồn kho nguyên tử và rollback hoàn kho khi hủy đơn")
    void testOrderCheckoutAndCancelWithStockRollback() {
        authenticateAsCustomer();

        ProductVariant variant = variantRepository.findAll().stream()
                .filter(v -> v.getStockQuantity() >= 10)
                .findFirst()
                .orElseThrow();

        int originalStock = variant.getStockQuantity();

        // 1. Thêm vào giỏ 3 chiếc
        cartService.addToCart(new OrderDto.AddCartItemRequest(variant.getId(), 3));

        // 2. Checkout tạo đơn hàng
        OrderDto.CreateOrderRequest orderReq = OrderDto.CreateOrderRequest.builder()
                .recipientName("Hoàng Quân")
                .phone("0987654321")
                .shippingAddress("123 Phố Huế, Hà Nội")
                .note("Giao giờ hành chính")
                .paymentMethod(PaymentMethod.COD)
                .build();

        OrderDto.OrderResponse order = orderService.createOrderFromCart(orderReq);
        assertNotNull(order.getOrderCode());
        assertEquals(OrderStatus.PENDING, order.getStatus());

        // 3. Kiểm tra tồn kho đã bị trừ đúng 3 chiếc
        ProductVariant afterCheckout = variantRepository.findById(variant.getId()).orElseThrow();
        assertEquals(originalStock - 3, afterCheckout.getStockQuantity());

        // 4. Hủy đơn hàng và kiểm tra tồn kho được hoàn trả nguyên vẹn
        OrderDto.OrderResponse cancelled = orderService.cancelOrder(order.getId());
        assertEquals(OrderStatus.CANCELLED, cancelled.getStatus());

        ProductVariant afterCancel = variantRepository.findById(variant.getId()).orElseThrow();
        assertEquals(originalStock, afterCancel.getStockQuantity());
    }

    @Test
    @DisplayName("7. Đề xuất vợt Racquet Selector hoạt động chính xác theo quy tắc")
    void testRacquetSelector() {
        ProductDto.RacquetSelectorRequest req = new ProductDto.RacquetSelectorRequest(
                "badminton", "advanced", "offensive", "power", new BigDecimal("350.00")
        );

        ProductDto.RecommendationResult result = productService.recommendRacquet(req);
        assertNotNull(result);
        assertNotNull(result.getRecommendationNote());
        assertTrue(result.getRecommendationNote().contains("ASTROX"));
    }
}
