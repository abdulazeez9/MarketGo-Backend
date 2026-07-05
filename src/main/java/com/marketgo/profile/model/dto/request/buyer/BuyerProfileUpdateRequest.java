package com.marketgo.profile.model.dto.request.buyer;

import java.util.UUID;

public record BuyerProfileUpdateRequest(
        UUID defaultAddressId,
        Boolean mobilityLimited
) {}