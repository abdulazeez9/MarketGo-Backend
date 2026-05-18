package com.marketgo.wallet.model.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TopUpRequest(
        @NotNull(message = "Amount is required")
        @DecimalMin(value = "1.00", message = "Minimum top-up is 1.00")
        BigDecimal amount
) {
}
