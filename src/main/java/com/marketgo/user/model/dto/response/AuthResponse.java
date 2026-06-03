package com.marketgo.user.model.dto.response;


import java.util.UUID;

public record AuthResponse(
        UUID id,
        String name,
        String email,
        String role,
        String token

) {
}