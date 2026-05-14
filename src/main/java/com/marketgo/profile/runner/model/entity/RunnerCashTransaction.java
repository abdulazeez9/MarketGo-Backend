package com.marketgo.profile.runner.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.order.model.entity.Order;
import com.marketgo.payment.model.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "runner_cash_transactions",
        indexes = {
                @Index(name="idx_runner_cash_runner", columnList="runner_id"),
                @Index(name="idx_runner_cash_order", columnList="order_id"),
                @Index(name="idx_runner_cash_created", columnList="created_at")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RunnerCashTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id")
    private RunnerProfile runner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balanceAfter;

    public enum TransactionType { cod_collection, settlement, payout }
}