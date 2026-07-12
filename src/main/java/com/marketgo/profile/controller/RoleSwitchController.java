package com.marketgo.profile.controller;

import com.marketgo.profile.service.RoleSwitchService;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.utils.AuthUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class RoleSwitchController {
    private final RoleSwitchService roleSwitchService;


    @PatchMapping("/role")
    public ResponseEntity<UserResponse> switchRole(Authentication auth,
                                                   @RequestBody SwitchRoleRequest request) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        UserResponse newRole = roleSwitchService.switchRole(userId, request.role());
        return ResponseEntity.ok(newRole);
    }


    public record SwitchRoleRequest(@NotNull User.Role role) {
    }

}
