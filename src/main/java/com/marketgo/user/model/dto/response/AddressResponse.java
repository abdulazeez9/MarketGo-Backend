package com.marketgo.user.model.dto.response;


import java.math.BigDecimal;
import java.util.UUID;

public record AddressResponse(
        UUID id,
        String label,
        String street,
        String city,
        String state,
        BigDecimal lat,
        BigDecimal lon,
        boolean isDefault
) {}
