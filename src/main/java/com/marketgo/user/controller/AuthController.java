package com.marketgo.user.controller;


import com.marketgo.exception.AppException;
import com.marketgo.user.model.dto.response.AuthResponse;
import com.marketgo.user.model.dto.request.LoginRequest;
import com.marketgo.user.model.dto.request.RegisterRequest;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.service.AuthService;
import com.marketgo.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST  /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        UserResponse data = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Account created successfully", data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse data = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Account login successfully", data));
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = extractTokenFromHeader(authHeader);
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Successfully logged out"));
    }

    private String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw AppException.badRequest("Invalid authorization header");
    }
};
