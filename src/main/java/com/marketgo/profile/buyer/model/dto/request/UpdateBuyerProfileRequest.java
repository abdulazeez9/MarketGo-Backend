package com.marketgo.profile.buyer.model.dto.request;

import java.util.UUID;

public record UpdateBuyerProfileRequest(
        UUID defaultAddressId,
        Boolean mobilityLimited
) {}