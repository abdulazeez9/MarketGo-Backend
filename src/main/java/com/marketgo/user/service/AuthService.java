package com.marketgo.user.service;


import com.marketgo.exception.AppException;
import com.marketgo.profile.buyer.model.entity.BuyerProfile;
import com.marketgo.profile.buyer.repository.BuyerProfileRepository;
import com.marketgo.user.mapper.UserMapper;
import com.marketgo.user.model.dto.response.AuthResponse;
import com.marketgo.user.model.dto.request.LoginRequest;
import com.marketgo.user.model.dto.request.RegisterRequest;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.utils.JwtUtil;
import com.marketgo.wallet.model.entity.Wallet;
import com.marketgo.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BuyerProfileRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final TokenBlacklistService tokenBlacklistService;

    // ========REGISTER USERS ==========
    @Transactional
    public UserResponse register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Active account already exists
            if (user.getDeletedAt() == null) {
                throw AppException.badRequest("Email already exists");
            }

            // Restore deleted account
            user.setDeletedAt(null);
            user.setName(request.name());
            user.setPhone(request.phone());
            user.setPassword(passwordEncoder.encode(request.password()));
            user.setVerified(false);

            User restoredUser = userRepository.save(user);

            return userMapper.toUserResponse(restoredUser);

        }

         // Create brand-new account
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .role(User.Role.USER)
                .verified(false)
                .build();

        // Saved User
        User saved = userRepository.save(user);

        // Auto create buyer profile
        buyerRepository.save(BuyerProfile.builder().user(saved).build());

        // Auto create wallet
        walletRepository.save(Wallet.builder().user(saved).balance(BigDecimal.ZERO).build());


        return userMapper.toUserResponse(saved);
    }


    // ========LOGIN USERS ==========
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(() -> AppException.unauthorized("Invalid email or password!"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!passwordMatches) {
            throw AppException.unauthorized("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name()
        );

        return userMapper.toAuthResponse(user, token);
    }

    // ========LOGOUT USER ==========
    public void logout(String token) {
        if (token == null || token.isBlank()) {
            throw AppException.badRequest("Token is required for logout");
        }

        try {
            // Get token expiration time remaining (in seconds)
            long expirationTimeInSeconds = jwtUtil.getTimeUntilExpiration(token);

            // Add token to blacklist with TTL matching expiration
            tokenBlacklistService.blacklistToken(token, expirationTimeInSeconds);
        } catch (Exception e) {
            throw AppException.unauthorized("Invalid token");
        }
    }
}