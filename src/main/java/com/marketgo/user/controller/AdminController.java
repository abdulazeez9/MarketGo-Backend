package com.marketgo.user.controller;

import com.marketgo.common.entity.Pagination;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.service.UserService;
import com.marketgo.utils.ApiResponse;
import com.marketgo.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Pagination<UserResponse>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> data = userService.getAllUsers(pageable);
        return ResponseEntity.ok(ApiResponse.success("Users fetched", Pagination.from(data)));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("User fetched", userService.getById(id)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id, Authentication auth) {
        UUID adminId = AuthUtils.getCurrentUserId(auth);
        if (adminId.equals(id)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Admin cannot delete their own account"));
        }
        userService.softDelete(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted successfully"));
    }
}