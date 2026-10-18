package com.yonex.controller;

import com.yonex.dto.ApiResponse;
import com.yonex.dto.AuthDto;
import com.yonex.entity.User;
import com.yonex.entity.UserAddress;
import com.yonex.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Quản lý xác thực, đăng ký, đăng nhập và thông tin cá nhân")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản CUSTOMER mới")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> register(@Valid @RequestBody AuthDto.RegisterRequest req) {
        AuthDto.AuthResponse response = authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Đăng ký tài khoản thành công", response));
    }

    @PostMapping("/login")
    @Operation(summary = "Đăng nhập và nhận JWT token")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> login(@Valid @RequestBody AuthDto.LoginRequest req) {
        AuthDto.AuthResponse response = authService.login(req);
        return ResponseEntity.ok(ApiResponse.ok("Đăng nhập thành công", response));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Làm mới access token bằng refresh token")
    public ResponseEntity<ApiResponse<AuthDto.AuthResponse>> refresh(@Valid @RequestBody AuthDto.RefreshRequest req) {
        AuthDto.AuthResponse response = authService.refreshToken(req.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.ok("Làm mới token thành công", response));
    }

    @GetMapping("/me")
    @Operation(summary = "Lấy thông tin tài khoản đang đăng nhập")
    public ResponseEntity<ApiResponse<User>> getCurrentUser() {
        User user = authService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.ok("Lấy thông tin cá nhân thành công", user));
    }

    @PutMapping("/profile")
    @Operation(summary = "Cập nhật thông tin cá nhân")
    public ResponseEntity<ApiResponse<User>> updateProfile(@Valid @RequestBody AuthDto.UpdateProfileRequest req) {
        User user = authService.updateProfile(req);
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật thông tin thành công", user));
    }

    @PutMapping("/change-password")
    @Operation(summary = "Đổi mật khẩu")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody AuthDto.ChangePasswordRequest req) {
        authService.changePassword(req);
        return ResponseEntity.ok(ApiResponse.ok("Đổi mật khẩu thành công", null));
    }

    @GetMapping("/addresses")
    @Operation(summary = "Lấy danh sách sổ địa chỉ nhận hàng")
    public ResponseEntity<ApiResponse<List<UserAddress>>> getAddresses() {
        List<UserAddress> addresses = authService.getAddresses();
        return ResponseEntity.ok(ApiResponse.ok("Lấy danh sách địa chỉ thành công", addresses));
    }

    @PostMapping("/addresses")
    @Operation(summary = "Thêm địa chỉ nhận hàng mới")
    public ResponseEntity<ApiResponse<UserAddress>> addAddress(@Valid @RequestBody AuthDto.AddressRequest req) {
        UserAddress address = authService.addAddress(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Thêm địa chỉ thành công", address));
    }

    @DeleteMapping("/addresses/{id}")
    @Operation(summary = "Xóa địa chỉ nhận hàng")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable Long id) {
        authService.deleteAddress(id);
        return ResponseEntity.ok(ApiResponse.ok("Xóa địa chỉ thành công", null));
    }
}
