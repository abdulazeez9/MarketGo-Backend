package com.marketgo.user.service;


import com.marketgo.exception.AppException;
import com.marketgo.user.mapper.UserMapper;
import com.marketgo.user.model.dto.request.UpdateProfileRequest;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.wallet.model.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    // Get all users (Admin)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAllByDeletedAtIsNull(pageable)
                .map(userMapper::toUserResponse);
    }

    // Get current user profile
    public UserResponse getById(String userId) {
        User user = findActiveUserById(userId);
        Wallet wallet = user.getWallet();
        return userMapper.toUserResponse(user);
    }

    // Update profile
    public UserResponse updateProfile(String userId, UpdateProfileRequest request) {
        User user = findActiveUserById(userId);
        if (request.name() != null) user.setName(request.name());
        if (request.phone() != null) user.setPhone(request.phone());

        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    ;

    //Soft delete
    public void softDelete(String userId) {
        User user = findActiveUserById(userId);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    ;

    // Private method to find userID
    private User findActiveUserById(String userId) {
        return userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(userId)).orElseThrow(() -> AppException.notFound("User not found!"));
    }

}
