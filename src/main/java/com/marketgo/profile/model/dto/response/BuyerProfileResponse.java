package com.marketgo.profile.model.dto.response;

import java.time.Instant;
import java.util.UUID;

public record BuyerProfileResponse(
        UUID id,

        UUID userId,
        String name,
        String email,

        UUID addressId,
        String addressLabel,  // "Home" or "Work"
        String street,
        String city,

        boolean mobilityLimited,
        Instant createdAt
) {
}