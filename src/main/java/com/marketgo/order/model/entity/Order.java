package com.marketgo.order.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.profile.model.entity.buyer.BuyerProfile;
import com.marketgo.profile.model.entity.runner.RunnerProfile;
import com.marketgo.user.model.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_buyer", columnList = "buyer_id"),
        @Index(name = "idx_orders_runner", columnList = "runner_id"),
        @Index(name = "idx_orders_status", columnList = "status"),
        @Index(name = "idx_orders_created", columnList = "created_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private BuyerProfile buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id")
    private RunnerProfile runner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.pending;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal total;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.unpaid;

    private LocalDateTime confirmedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;

    private String otpCode;
    private boolean otpVerified = false;

    public enum PaymentMethod {
        wallet, card, transfer, cash_on_delivery
    }

    public enum OrderStatus {
        pending,
        confirmed,
        runner_assigned,
        picked_up,
        delivered,
        completed,
        cancelled
    }

    public enum PaymentStatus {
        unpaid,
        paid,
        pending_cod,
        refunded
    }
}