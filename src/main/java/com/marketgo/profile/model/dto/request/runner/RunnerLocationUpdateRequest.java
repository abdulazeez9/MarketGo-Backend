package com.marketgo.profile.model.dto.request.runner;

import java.math.BigDecimal;

public record RunnerLocationUpdateRequest(
        BigDecimal currentLat,
        BigDecimal currentLon
) {
}
