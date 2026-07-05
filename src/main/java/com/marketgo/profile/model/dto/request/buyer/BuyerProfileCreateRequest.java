package com.marketgo.profile.model.dto.request.buyer;

import java.util.UUID;

public record BuyerProfileCreateRequest(UUID userId, UUID defaultAddressId) {
}
