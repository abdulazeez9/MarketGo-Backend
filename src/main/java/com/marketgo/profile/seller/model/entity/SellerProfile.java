package com.marketgo.profile.seller.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.Address;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seller_profiles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String shopName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_location_id")
    private Address defaultAddress;

    private boolean isMobilityLimited;

}
