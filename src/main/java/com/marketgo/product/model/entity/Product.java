package com.marketgo.product.model.entity;


import com.marketgo.common.entity.BaseEntity;

import com.marketgo.profile.seller.model.entity.SellerProfile;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Product extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SellerProfile seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private BigDecimal price;
    private Integer stockQty;
    private boolean isAvailable = true;
}
