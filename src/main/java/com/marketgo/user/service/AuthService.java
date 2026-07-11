package com.marketgo.user.service;


import com.marketgo.exception.AppException;
import com.marketgo.profile.model.entity.buyer.BuyerProfile;
import com.marketgo.profile.repository.BuyerProfileRepository;
import com.marketgo.profile.repository.RunnerProfileRepository;
import com.marketgo.profile.repository.SellerProfileRepository;
import com.marketgo.user.mapper.UserMapper;
import com.marketgo.user.model.dto.response.AuthResponse;
import com.marketgo.user.model.dto.request.LoginRequest;
import com.marketgo.user.model.dto.request.RegisterRequest;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.utils.JwtUtil;
import com.marketgo.wallet.model.entity.Wallet;
import com.marketgo.wallet.model.entity.WalletTransaction;
import com.marketgo.wallet.repository.WalletRepository;
import com.marketgo.wallet.repository.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {

    //Injected Repository
    private final UserRepository userRepository;
    private final BuyerProfileRepository buyerProfileRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final RunnerProfileRepository runnerProfileRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    //Injected Utils
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final TokenBlacklistService tokenBlacklistService;

    // ========REGISTER USERS ==========
    @Transactional
    public UserResponse register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.email());

        // If user exist in the database
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
            user.setRole(User.Role.BUYER);

            User restoredUser = userRepository.save(user);

            //Clean up any leftover seller/runner profile from before deletion
            sellerProfileRepository.findByUserId(restoredUser.getId())
                    .ifPresent(sellerProfileRepository::delete);
            runnerProfileRepository.findByUserId(restoredUser.getId())
                    .ifPresent(runnerProfileRepository::delete);

            //Recreate buyer profile if it doesn't already exist
            if (!buyerProfileRepository.existsByUserId(restoredUser.getId())) {
                buyerProfileRepository.save(BuyerProfile.builder().user(restoredUser).build());
            }

            // Reset wallet balance to zero, with an audit transaction if funds existed
            Wallet wallet = walletRepository.findByUserId(restoredUser.getId())
                    .orElseGet(() -> Wallet.builder().user(restoredUser).balance(BigDecimal.ZERO).build());

            if (wallet.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                walletTransactionRepository.save(WalletTransaction.builder()
                        .wallet(wallet)
                        .type(WalletTransaction.TransactionType.debit)
                        .reason(WalletTransaction.TransactionReason.refund)
                        .amount(wallet.getBalance())
                        .referenceId(UUID.randomUUID())
                        .referenceType(WalletTransaction.ReferenceType.manual)
                        .balanceAfter(BigDecimal.ZERO)
                        .note("Balance reset on account restore")
                        .build()
                );

            }

            wallet.setBalance(BigDecimal.ZERO);
            walletRepository.save(wallet);
            return userMapper.toUserResponse(restoredUser);

        }


        // Create brand-new account
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .verified(false)
                .build();

        // Saved User
        User saved = userRepository.save(user);

        // Auto create buyer profile
        buyerProfileRepository.save(BuyerProfile.builder().user(saved).build());

        // Auto create wallet
       Wallet wallet = walletRepository.save(Wallet.builder().user(saved).balance(BigDecimal.ZERO).build());


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