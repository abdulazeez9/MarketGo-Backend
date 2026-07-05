package com.marketgo.profile.model.dto.request.seller;

import java.util.UUID;

public record SellerProfileUpdateRequest(String shopName,
                                         UUID shopLocationId) {
}
