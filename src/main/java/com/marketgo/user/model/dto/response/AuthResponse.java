package com.marketgo.user.model.dto.response;


import java.util.UUID;

public record AuthResponse(
        UUID id,
        String token,
        String name,
        String email,
        String role

) {
}