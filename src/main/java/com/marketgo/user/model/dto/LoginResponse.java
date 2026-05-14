package com.marketgo.user.model.dto;


import java.util.UUID;

public record LoginResponse(
        UUID id,
        String token,
        String name,
        String email

) {
}