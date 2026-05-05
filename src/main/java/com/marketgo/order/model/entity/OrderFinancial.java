package com.marketgo.order.model.entity;

import com.marketgo.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_financials")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderFinancial extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal platformCommission;
    private BigDecimal sellerEarnings;
    private BigDecimal runnerEarnings;
}
