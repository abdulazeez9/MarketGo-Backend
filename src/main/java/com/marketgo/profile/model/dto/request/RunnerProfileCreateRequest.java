package com.marketgo.profile.model.dto.request;

import com.marketgo.profile.model.entity.RunnerProfile;

import java.util.UUID;

public record RunnerProfileCreateRequest(
        UUID userId, RunnerProfile.VehicleType vehicleType
) {
}
