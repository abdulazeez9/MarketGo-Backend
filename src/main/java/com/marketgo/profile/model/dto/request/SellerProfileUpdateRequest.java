package com.marketgo.profile.model.dto.request;

import java.util.UUID;

public record SellerProfileUpdateRequest(String shopName,
                                         UUID shopLocationId) {
}
