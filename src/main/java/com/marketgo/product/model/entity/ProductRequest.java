package com.marketgo.product.model.entity;


import com.marketgo.common.entity.BaseEntity;

import com.marketgo.profile.model.entity.BuyerProfile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_request", indexes = {
        @Index(name="idx_product_request_buyer", columnList="buyer_id")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private BuyerProfile buyer;

    private String productName;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.open;

    public enum Status { open, sourced, cancelled }
}
