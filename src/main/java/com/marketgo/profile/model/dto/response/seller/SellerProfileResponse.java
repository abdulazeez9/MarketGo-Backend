package com.marketgo.profile.model.dto.response.seller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SellerProfileResponse(
        UUID id,
        UUID userId,
        String name,
        String shopName,
        UUID shopLocationId,
        String shopLocationLabel,
        BigDecimal rating,
        boolean active,
        Instant createdAt
) {
}
