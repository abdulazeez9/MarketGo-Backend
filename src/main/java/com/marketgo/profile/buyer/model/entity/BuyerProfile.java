package com.marketgo.profile.buyer.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.Address;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "buyer_profiles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_address_id")
    private Address defaultAddress;


    @Column(name = "is_mobility_limited")
    private boolean mobilityLimited = false;


}
