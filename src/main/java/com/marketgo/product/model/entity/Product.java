package com.marketgo.product.model.entity;


import com.marketgo.common.entity.BaseEntity;

import com.marketgo.profile.model.entity.SellerProfile;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Entity
@Table(
        name="products",
        indexes={
                @Index(name="idx_product_category", columnList="category_id"),
                @Index(name="idx_product_seller", columnList="seller_id")
        }
)
@Getter @Setter
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Product extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private SellerProfile seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(nullable=false)
    @Builder.Default
    private Integer stockQty = 0;

    @Builder.Default
    private boolean isAvailable = true;
}
