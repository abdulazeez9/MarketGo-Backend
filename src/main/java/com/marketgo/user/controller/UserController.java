package com.marketgo.user.controller;

import com.marketgo.user.model.dto.request.UpdateProfileRequest;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.service.UserService;
import com.marketgo.utils.ApiResponse;
import com.marketgo.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(Authentication auth) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        UserResponse data = userService.getById(userId);
        return ResponseEntity.ok(ApiResponse.success("Profile fetched", data));
    }

    // PATCH /api/users/me
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMe(
            Authentication auth,
            @RequestBody UpdateProfileRequest request
    ) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        UserResponse data = userService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated", data));
    }

    // DELETE /api/users/me — soft delete
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMe(Authentication auth) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        if(auth.getPrincipal().equals("admin")) {

        userService.softDelete(userId);
        }

        return ResponseEntity.ok(ApiResponse.success("Account deleted"));
    }



}
