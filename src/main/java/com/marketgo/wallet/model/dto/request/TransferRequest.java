package com.marketgo.wallet.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(

        @NotNull(message = "Recipient is required")
        UUID recipientUserId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "1.00", message = "Minimum transfer is 1.00")
        BigDecimal amount,

        String note
) {
}
