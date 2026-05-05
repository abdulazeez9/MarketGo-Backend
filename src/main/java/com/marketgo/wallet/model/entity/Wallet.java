package com.marketgo.wallet.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
public class Wallet extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balance = BigDecimal.ZERO;

    @Column(columnDefinition = "varchar(10) default 'NGN'" )
    private String currency = "NGN";

    private boolean isFrozen = false;
}
