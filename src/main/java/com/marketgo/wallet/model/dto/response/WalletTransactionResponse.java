package com.marketgo.wallet.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletTransactionResponse(
        UUID id,
        String type,
        String reason,
        BigDecimal amount,
        BigDecimal balanceAfter,
        String note,
        LocalDateTime createdAt
) {
}
