package com.marketgo.user.model.dto.response;

import com.marketgo.wallet.model.dto.response.WalletResponse;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String role,
        String phone,
        boolean verified,

        Instant createdAt
) {
}
