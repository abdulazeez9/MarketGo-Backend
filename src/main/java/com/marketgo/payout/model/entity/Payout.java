package com.marketgo.payout.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import com.marketgo.wallet.model.entity.Wallet;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payouts", indexes = {
        @Index(name = "idx_payout_user", columnList = "user_id"),
        @Index(name = "idx_payout_status", columnList = "status"),
        @Index(name = "idx_payout_created", columnList = "created_at")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Payout extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PayoutStatus status = PayoutStatus.pending;

    @Column(unique = true)
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    public enum PayoutStatus {
        pending, processing, paid, failed
    }
}
