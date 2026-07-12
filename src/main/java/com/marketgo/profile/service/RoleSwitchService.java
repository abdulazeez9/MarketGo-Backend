package com.marketgo.profile.service;

import com.marketgo.exception.AppException;
import com.marketgo.profile.model.entity.BuyerProfile;
import com.marketgo.profile.model.entity.RunnerProfile;
import com.marketgo.profile.model.entity.SellerProfile;
import com.marketgo.profile.repository.BuyerProfileRepository;
import com.marketgo.profile.repository.RunnerProfileRepository;
import com.marketgo.profile.repository.SellerProfileRepository;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleSwitchService {
    private final UserRepository userRepository;
    private final BuyerProfileRepository buyerProfileRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final RunnerProfileRepository runnerProfileRepository;
    private final UserMapper userMapper;


    public UserResponse switchRole(UUID userId, User.Role role) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> AppException.badRequest("User not found"));

        if (user.getRole().equals(role)) {
            throw AppException.forbidden("Role already exists");
        }

        if (role == User.Role.ADMIN) {
            throw AppException.forbidden("Cannot self-assign ADMIN role");
        }

        switch (role) {
            case BUYER -> {
                if (!buyerProfileRepository.existsByUserId(userId)) {
                    buyerProfileRepository.save(BuyerProfile.builder().user(user).build());
                }
            }
            case SELLER -> {
                if (!sellerProfileRepository.existsByUserId(userId)) {
                    sellerProfileRepository.save(SellerProfile.builder().user(user).build());
                }
            }
            case RUNNER -> {
                if (!runnerProfileRepository.existsByUserId(userId)) {
                    runnerProfileRepository.save(RunnerProfile.builder().user(user).build());
                }
            }
            default -> throw AppException.badRequest("Invalid Role");
        }

        user.setRole(role);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}