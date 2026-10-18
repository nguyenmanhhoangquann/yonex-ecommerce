# Yonex Sports E-Commerce Backend (Java Spring Boot 3 & Java 21)

Hệ thống Backend REST API hoàn chỉnh cho nền tảng thương mại điện tử thể thao Yonex, xây dựng theo kiến trúc chuẩn Controller – Service – Repository – Entity – DTO.

---

## 1. Công nghệ sử dụng
- **Java 21 LTS** & **Spring Boot 3.4.4**
- **Spring Web**: RESTful API chuẩn HTTP Status Codes
- **Spring Security & JJWT 0.12.6**: Xác thực phi trạng thái (Stateless), mã hóa BCrypt
- **Spring Data JPA & Hibernate 6**: ORM, quản lý transaction nguyên tử
- **MySQL & H2 Database (MySQL mode)**: Hỗ trợ linh hoạt cả môi trường dev nhẹ và production
- **Bean Validation (Jakarta Validation)**: Kiểm tra dữ liệu đầu vào chặt chẽ
- **Springdoc OpenAPI 2.8.5 & Swagger UI**: Tài liệu API tương tác trực tiếp
- **JUnit 5 & Spring Boot Test**: Unit & Integration Test đầy đủ

---

## 2. Tài khoản Demo (Môi trường Development)
Hệ thống tự động khởi tạo dữ liệu mẫu Yonex khi chạy lần đầu:
- **Tài khoản Quản trị viên (ADMIN)**:
  - Email: `admin@yonex-demo.com`
  - Mật khẩu: `Admin@123456`
  - Quyền hạn: Quản trị sản phẩm, cập nhật đơn hàng, thống kê dashboard doanh thu, quản lý tài khoản.
- **Tài khoản Khách hàng (CUSTOMER)**:
  - Email: `customer@yonex-demo.com`
  - Mật khẩu: `Customer@123456`
  - Quyền hạn: Xem/lọc sản phẩm, so sánh thông số, Racquet Selector, giỏ hàng, đặt hàng, hủy đơn, đánh giá, yêu thích.

---

## 3. Hướng dẫn chạy dự án trên Windows

### Cách 1: Chạy ngay với Profile DEV (H2 tương thích MySQL - Khuyên dùng khi phát triển)
Không cần cài đặt hay bật MySQL service. Dữ liệu được lưu bền vững vào thư mục `./data/yonex`:
```bash
cd C:\Users\A\Downloads\yonex\yonex-backend
mvn spring-boot:run
```
Server sẽ khởi động tại: `http://localhost:8080`  
Swagger UI kiểm thử trực quan: `http://localhost:8080/swagger-ui.html`  
H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/yonex`, user: `sa`, password: trống)

### Cách 2: Chạy với MySQL cục bộ (XAMPP / Laragon / MySQL Server)
1. Bật MySQL trên cổng `3306` (database `yonex` sẽ tự động tạo nếu chưa có).
2. Chạy với profile `mysql`:
```bash
# PowerShell
$env:SPRING_PROFILES_ACTIVE="mysql"
mvn spring-boot:run

# Hoặc CMD
set SPRING_PROFILES_ACTIVE=mysql
mvn spring-boot:run
```

### Chạy Unit & Integration Test:
```bash
mvn test
```

---

## 4. Danh sách các nhóm API chính (/api/v1)

### A. Xác thực & Hồ sơ cá nhân (`/api/v1/auth`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| POST | `/api/v1/auth/register` | Public | Đăng ký tài khoản CUSTOMER mới |
| POST | `/api/v1/auth/login` | Public | Đăng nhập nhận Access Token & Refresh Token |
| POST | `/api/v1/auth/refresh` | Public | Cấp mới Access Token từ Refresh Token |
| GET | `/api/v1/auth/me` | Authenticated | Xem hồ sơ tài khoản hiện tại |
| PUT | `/api/v1/auth/profile` | Authenticated | Cập nhật họ tên, số điện thoại |
| PUT | `/api/v1/auth/change-password` | Authenticated | Đổi mật khẩu |
| GET | `/api/v1/auth/addresses` | Authenticated | Lấy danh sách địa chỉ nhận hàng |
| POST | `/api/v1/auth/addresses` | Authenticated | Thêm địa chỉ nhận hàng |
| DELETE | `/api/v1/auth/addresses/{id}` | Authenticated | Xóa địa chỉ nhận hàng |

### B. Môn thể thao & Danh mục (`/api/v1`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| GET | `/api/v1/sports` | Public | Lấy tất cả môn thể thao (Badminton, Tennis, Golf, Running...) |
| GET | `/api/v1/categories` | Public | Lấy tất cả danh mục sản phẩm (Racquets, Shoes, Apparel, Strings...) |
| GET | `/api/v1/categories/sport/{sportId}` | Public | Lấy danh mục theo môn thể thao |

### C. Sản phẩm & So sánh (`/api/v1/products`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| GET | `/api/v1/products` | Public | Lọc & tìm kiếm theo `keyword`, `sport`, `category`, `series`, `minPrice`, `maxPrice`, phân trang `page`, `size`, sắp xếp `sortBy`, `direction` |
| GET | `/api/v1/products/{id}` | Public | Chi tiết sản phẩm theo ID kèm biến thể màu/size, thông số kỹ thuật |
| GET | `/api/v1/products/slug/{slug}` | Public | Chi tiết sản phẩm theo slug URL |
| GET | `/api/v1/products/featured` | Public | Danh sách sản phẩm nổi bật |
| GET | `/api/v1/products/new-arrivals` | Public | Danh sách sản phẩm mới về |
| GET | `/api/v1/products/compare?ids=1,2` | Public | So sánh thông số kỹ thuật (giới hạn tối đa 5 sản phẩm) |

### D. Racquet Selector (`/api/v1/racquet-selector`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| POST | `/api/v1/racquet-selector/recommendations` | Public | Đề xuất vợt chuyên sâu theo môn, trình độ, phong cách (Offensive/Defensive/All-Around) và ngân sách |

### E. Giỏ hàng (`/api/v1/cart`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| GET | `/api/v1/cart` | Authenticated | Xem giỏ hàng cá nhân (tính tiền & freeship $99 ở server) |
| POST | `/api/v1/cart/items` | Authenticated | Thêm sản phẩm/biến thể vào giỏ (kiểm tra tồn kho) |
| PATCH | `/api/v1/cart/items/{itemId}` | Authenticated | Cập nhật số lượng |
| DELETE | `/api/v1/cart/items/{itemId}` | Authenticated | Xóa 1 mục khỏi giỏ hàng |
| DELETE | `/api/v1/cart` | Authenticated | Làm trống giỏ hàng |

### F. Đơn hàng & Quản lý tồn kho (`/api/v1/orders`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| POST | `/api/v1/orders` | Authenticated | Tạo đơn từ giỏ hàng (chụp giá snapshot, trừ kho nguyên tử) |
| GET | `/api/v1/orders/my-orders` | Authenticated | Lịch sử mua hàng của người dùng |
| GET | `/api/v1/orders/{id}` | Authenticated | Chi tiết đơn hàng (chỉ chủ đơn hoặc Admin) |
| POST | `/api/v1/orders/{id}/cancel` | Authenticated | Hủy đơn hàng và tự động hoàn trả tồn kho (chống hoàn kho 2 lần) |

### G. Tương tác & Nội dung (`/api/v1`)
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| GET/POST/DELETE | `/api/v1/wishlist` | Authenticated | Quản lý danh sách sản phẩm yêu thích |
| GET/POST | `/api/v1/products/{id}/reviews` | Public / Auth | Xem và gửi đánh giá sao + nhận xét sản phẩm |
| GET | `/api/v1/news` | Public | Danh sách tin tức thể thao & sự kiện |
| GET | `/api/v1/athletes` | Public | Danh sách vận động viên Team Yonex |
| GET | `/api/v1/stores` | Public | Store Locator tìm kiếm đại lý, cửa hàng chính hãng |
| GET | `/api/v1/faqs` | Public | Hỏi đáp kỹ thuật, phân biệt thật giả, bảo hành |

### H. Quản trị hệ thống (`/api/v1/admin`) - Yêu cầu ROLE_ADMIN
| Method | Endpoint | Quyền | Mô tả |
|---|---|---|---|
| GET | `/api/v1/admin/dashboard` | ADMIN | Thống kê doanh thu thực, tổng user, đơn hàng theo trạng thái, sản phẩm hết hàng |
| POST | `/api/v1/admin/products` | ADMIN | Thêm sản phẩm mới |
| PUT | `/api/v1/admin/products/{id}` | ADMIN | Chỉnh sửa sản phẩm |
| DELETE | `/api/v1/admin/products/{id}` | ADMIN | Xóa mềm / ẩn sản phẩm |
| GET | `/api/v1/admin/orders` | ADMIN | Quản lý tất cả đơn hàng trên hệ thống |
| PATCH | `/api/v1/admin/orders/{id}/status` | ADMIN | Cập nhật trạng thái đơn (CONFIRMED, SHIPPED, DELIVERED, CANCELLED) |
| GET | `/api/v1/admin/users` | ADMIN | Quản lý danh sách người dùng |
| PATCH | `/api/v1/admin/users/{id}/status` | ADMIN | Khóa hoặc mở khóa tài khoản |

---

## 5. Luồng xử lý dữ liệu chuẩn (Request Flow)
1. **Client / React Frontend**: Gửi request kèm Header `Authorization: Bearer <token>`
2. **AuthTokenFilter**: Giải mã JWT token, kiểm tra chữ ký HS256 và thời hạn, nạp `UserDetails` vào `SecurityContextHolder`.
3. **Controller**: Nhận DTO, kiểm tra `@Valid` (bắt lỗi 400 chuẩn hóa nếu không hợp lệ).
4. **Service**: Thực thi nghiệp vụ (ví dụ: kiểm tra tồn kho, snapshot giá, tính freeship threshold).
5. **Repository (Spring Data JPA)**: Tương tác với Database qua các câu truy vấn tối ưu, hỗ trợ Pageable và Native/JPQL Query.
6. **Database (MySQL / H2)**: Ánh xạ quan hệ ràng buộc khoá ngoại, Index cho SKU, Slug, Email.
7. **ApiResponse<T>**: Đóng gói kết quả đồng nhất `{"success": true, "message": "...", "data": ...}` trả về cho Frontend.
