package com.marketgo.profile.runner.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.order.model.entity.Order;
import com.marketgo.payment.model.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "runner_cash_transactions")
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

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;

    public enum TransactionType { cod_collection, settlement, payout }
}