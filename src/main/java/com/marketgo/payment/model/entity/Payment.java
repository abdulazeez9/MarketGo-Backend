package com.marketgo.payment.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.order.model.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_order", columnList = "order_id"),
        @Index(name = "idx_payment_status", columnList = "status"),
        @Index(name = "idx_payment_reference", columnList = "reference")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String provider;

    @Column(unique = true)
    private String reference;

    private LocalDateTime paidAt;

    public enum PaymentMethod {
        wallet, card, transfer, cash_on_delivery
    }

    public enum PaymentStatus {
        initiated,
        authorized,
        pending_cod,
        collected,
        settled,
        success,
        failed,
        refunded
    }
}
