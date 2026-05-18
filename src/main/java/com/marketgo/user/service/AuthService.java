package com.marketgo.user.service;


import com.marketgo.exception.AppException;
import com.marketgo.profile.buyer.model.entity.BuyerProfile;
import com.marketgo.profile.buyer.repository.BuyerProfileRepository;
import com.marketgo.user.mapper.UserMapper;
import com.marketgo.user.model.dto.response.AuthResponse;
import com.marketgo.user.model.dto.request.LoginRequest;
import com.marketgo.user.model.dto.request.RegisterRequest;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.utils.JwtUtil;
import com.marketgo.wallet.model.entity.Wallet;
import com.marketgo.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BuyerProfileRepository buyerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;


    // ========REGISTER USERS ==========
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw AppException.badRequest("Email already in use");
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .role(User.Role.USER)
                .verified(false)
                .build();

        User saved = userRepository.save(user);

        // Auto create buyer profile
        BuyerProfile buyerProfile = buyerRepository.save(
                BuyerProfile.builder()
                        .user(user)
                        .build()
        );

        // Auto create wallet
        Wallet wallet = walletRepository.save(
                Wallet.builder()
                        .user(user)
                        .balance(BigDecimal.ZERO)
                        .build()
        );

        String token = jwtUtil.generateToken(
                saved.getId().toString(),
                saved.getEmail(),
                saved.getRole().name()
        );

        return userMapper.toAuthResponse(saved, token);
    }


    // ========LOGIN USERS ==========
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(() -> AppException.unauthorized("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!passwordMatches) {
            throw AppException.unauthorized("Invalid email or password");
        }

        String token = jwtUtil.generateToken(
                user.getId().toString(),
                user.getEmail(),
                user.getRole().name()
        );

        return userMapper.toAuthResponse(user, token);
    }
}