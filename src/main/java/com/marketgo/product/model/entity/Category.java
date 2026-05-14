package com.marketgo.product.model.entity;

import com.marketgo.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_parent", columnList = "parent_id")
        }
)
public class Category extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Category parent;

    private String iconUrl;
}
