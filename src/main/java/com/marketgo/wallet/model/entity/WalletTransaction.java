package com.marketgo.wallet.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.payment.model.entity.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;


    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionReason reason;


    private BigDecimal amount;
    private UUID referenceId;
    private BigDecimal balanceAfter;

    @Enumerated(EnumType.STRING)
    private ReferenceType referenceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;

    private String note;

    public enum TransactionType { credit, debit }
    public enum TransactionReason { order_payment, refund, topUp, payout }
    public enum ReferenceType { order, payout, topUp, manual }

}
