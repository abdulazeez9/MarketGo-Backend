package com.marketgo.product.model.entity;

import com.marketgo.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductImage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id" ,nullable = false )
    private Product product;

    private String url;
    private boolean isPrimary = false;
    private Integer sortOrder;
}