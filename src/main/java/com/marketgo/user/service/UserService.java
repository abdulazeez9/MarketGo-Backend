package com.marketgo.user.service;


import com.marketgo.exception.AppException;
import com.marketgo.user.mapper.UserMapper;
import com.marketgo.user.model.dto.request.UpdateProfileRequest;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


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
    public UserResponse getById(UUID userId) {
        User user = findActiveUserById(userId);
        return userMapper.toUserResponse(user);
    }

    // Update profile
    public UserResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        User user = findActiveUserById(userId);
        if (request.name() != null) user.setName(request.name());
        if (request.phone() != null) user.setPhone(request.phone());

        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    ;

    //Soft delete
    public void softDelete(UUID userId) {
        User user = findActiveUserById(userId);
        user.setDeletedAt(Instant.now());
        userRepository.save(user);
    }

    ;

    // Private method to find userID
    private User findActiveUserById(UUID userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> AppException.notFound("User not found!"));
    }

}
