package com.marketgo.user.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String role,
        String phone,
        boolean verified,
        LocalDateTime createdAt
) {
}
