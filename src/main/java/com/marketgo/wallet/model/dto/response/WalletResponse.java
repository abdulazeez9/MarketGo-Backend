package com.marketgo.wallet.model.dto.response;

import com.marketgo.wallet.model.entity.Wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        UUID userId,
        BigDecimal balance,
        String currency,
        boolean frozen) {
}
