package com.marketgo.user.model.dto.request;

import java.math.BigDecimal;

public record AddressRequest(String label,
                             String street,
                             String city,
                             String state,
                             boolean isDefault,
                             BigDecimal lat,
                             BigDecimal lon) {

}
