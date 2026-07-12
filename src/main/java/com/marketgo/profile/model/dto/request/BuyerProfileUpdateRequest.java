package com.marketgo.profile.model.dto.request;

import java.util.UUID;

public record BuyerProfileUpdateRequest(
        UUID defaultAddressId,
        Boolean mobilityLimited
) {}