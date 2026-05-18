package com.marketgo.profile.buyer.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BuyerProfileResponse(
        UUID id,

        UUID userId,
        String userName,
        String userEmail,

        UUID defaultAddressId,
        String defaultAddressLabel,  // "Home" or "Work"
        String defaultAddressStreet,
        String defaultAddressCity,

        boolean mobilityLimited,
        LocalDateTime createdAt
) {
}