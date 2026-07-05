package com.marketgo.profile.model.dto.request.runner;

import com.marketgo.profile.model.entity.runner.RunnerProfile;

import java.util.UUID;

public record RunnerProfileCreateRequest(
        UUID userId, RunnerProfile.VehicleType vehicleType
) {
}
