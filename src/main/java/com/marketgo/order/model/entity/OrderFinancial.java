package com.marketgo.order.model.entity;

import com.marketgo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_financials",
        indexes = {
                @Index(name = "idx_financial_order", columnList = "order_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFinancial extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal platformCommission;
    private BigDecimal sellerEarnings;
    private BigDecimal runnerEarnings;
}