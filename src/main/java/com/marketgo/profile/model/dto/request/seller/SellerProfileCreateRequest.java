package com.marketgo.profile.model.dto.request.seller;

import java.util.UUID;

public record SellerProfileCreateRequest(UUID userId, String shopName,
                                         UUID shopLocationId) {
}
