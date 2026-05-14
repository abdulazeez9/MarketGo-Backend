package com.marketgo.wallet.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.payment.model.entity.Payment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "wallet_transactions",
        indexes = {
                @Index(name = "idx_wallet_tx_wallet", columnList = "wallet_id"),
                @Index(name = "idx_wallet_tx_reference", columnList = "referenceId, referenceType"),
                @Index(name = "idx_wallet_tx_created", columnList = "created_at")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionReason reason;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private UUID referenceId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balanceAfter;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(length = 255)
    private String note;


    public enum TransactionType { credit, debit }
    public enum TransactionReason { order_payment, refund, topUp, payout }
    public enum ReferenceType { order, payout, topUp, manual }
}
