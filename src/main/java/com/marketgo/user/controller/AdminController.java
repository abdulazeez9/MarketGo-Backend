package com.marketgo.user.controller;

import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.service.UserService;
import com.marketgo.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers() {
        List<UserResponse> data = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users fetched", data));
    };

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        userService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("User fetched", userService.getById(id)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        userService.softDelete(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted"));
    }

}
