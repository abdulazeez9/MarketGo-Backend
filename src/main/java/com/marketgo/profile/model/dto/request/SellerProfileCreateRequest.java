package com.marketgo.profile.model.dto.request;

import java.util.UUID;

public record SellerProfileCreateRequest(UUID userId, String shopName,
                                         UUID shopLocationId) {
}
