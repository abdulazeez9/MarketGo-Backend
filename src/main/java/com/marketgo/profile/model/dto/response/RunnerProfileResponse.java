package com.marketgo.profile.model.dto.response;

import com.marketgo.profile.model.entity.RunnerProfile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RunnerProfileResponse(
        UUID id,
        UUID userId,
        String name,
        RunnerProfile.VehicleType vehicleType,
        boolean available,
        BigDecimal currentLat,
        BigDecimal currentLon,
        BigDecimal rating,
        Instant createdAt
) {
}
