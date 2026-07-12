package com.marketgo.profile.model.dto.request;

import java.util.UUID;

public record BuyerProfileCreateRequest(UUID userId, UUID defaultAddressId) {
}
