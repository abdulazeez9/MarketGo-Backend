package com.marketgo.order.model.entity;

import com.marketgo.common.entity.BaseEntity;

import com.marketgo.product.model.entity.Product;
import com.marketgo.profile.seller.model.entity.SellerProfile;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Table(name = "orders_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SellerProfile seller;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}