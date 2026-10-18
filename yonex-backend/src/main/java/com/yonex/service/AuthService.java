package com.yonex.service;

import com.yonex.dto.AuthDto;
import com.yonex.entity.User;
import com.yonex.entity.UserAddress;
import com.yonex.enums.Role;
import com.yonex.exception.ApiException;
import com.yonex.exception.ResourceNotFoundException;
import com.yonex.repository.UserAddressRepository;
import com.yonex.repository.UserRepository;
import com.yonex.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ApiException("Email này đã được sử dụng: " + req.getEmail());
        }

        User user = User.builder()
                .email(req.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(req.getPassword()))
                .fullName(req.getFullName().trim())
                .phone(req.getPhone() != null ? req.getPhone().trim() : null)
                .role(Role.ROLE_CUSTOMER) // strictly customer
                .active(true)
                .build();

        user = userRepository.save(user);

        String accessToken = jwtUtils.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return AuthDto.AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

    public AuthDto.AuthResponse login(AuthDto.LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail().trim().toLowerCase(), req.getPassword())
        );

        User user = userRepository.findByEmail(req.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "email", req.getEmail()));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new ApiException("Tài khoản đã bị vô hiệu hóa");
        }

        String accessToken = jwtUtils.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return AuthDto.AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

    public AuthDto.AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new ApiException("Refresh token không hợp lệ hoặc đã hết hạn");
        }

        String email = jwtUtils.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng", "email", email));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new ApiException("Tài khoản đã bị vô hiệu hóa");
        }

        String newAccessToken = jwtUtils.generateAccessToken(user.getEmail(), user.getRole().name());

        return AuthDto.AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new ApiException("Người dùng chưa được xác thực");
        }
        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại: " + auth.getName()));
    }

    @Transactional
    public User updateProfile(AuthDto.UpdateProfileRequest req) {
        User user = getCurrentUser();
        user.setFullName(req.getFullName().trim());
        if (req.getPhone() != null) {
            user.setPhone(req.getPhone().trim());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(AuthDto.ChangePasswordRequest req) {
        User user = getCurrentUser();
        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            throw new ApiException("Mật khẩu hiện tại không đúng");
        }
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    public List<UserAddress> getAddresses() {
        User user = getCurrentUser();
        return addressRepository.findByUserId(user.getId());
    }

    @Transactional
    public UserAddress addAddress(AuthDto.AddressRequest req) {
        User user = getCurrentUser();

        boolean isDefault = Boolean.TRUE.equals(req.getIsDefault());
        if (isDefault) {
            List<UserAddress> existing = addressRepository.findByUserId(user.getId());
            for (UserAddress addr : existing) {
                addr.setIsDefault(false);
            }
            addressRepository.saveAll(existing);
        }

        UserAddress address = UserAddress.builder()
                .user(user)
                .recipientName(req.getRecipientName().trim())
                .phone(req.getPhone().trim())
                .street(req.getStreet().trim())
                .ward(req.getWard())
                .district(req.getDistrict())
                .city(req.getCity().trim())
                .isDefault(isDefault)
                .build();

        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        User user = getCurrentUser();
        UserAddress address = addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Địa chỉ không tồn tại"));
        addressRepository.delete(address);
    }
}
