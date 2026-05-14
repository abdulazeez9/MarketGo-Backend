package com.marketgo.profile.seller.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.Address;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "seller_profiles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SellerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false , unique = true )
    private User user;

    @Column(name = "shop_name")
    private String shopName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_location_id")
    private Address shopLocation;

    private BigDecimal rating;

    @Column(name = "is_active")
    private boolean active;

}
