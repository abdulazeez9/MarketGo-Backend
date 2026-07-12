package com.marketgo.profile.model.dto.request;

import java.math.BigDecimal;

public record RunnerLocationUpdateRequest(
        BigDecimal currentLat,
        BigDecimal currentLon
) {
}
