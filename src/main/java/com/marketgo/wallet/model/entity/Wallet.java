package com.marketgo.wallet.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import java.math.BigDecimal;

@Entity
@Table(name = "wallets")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Wallet extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,  unique = true)
    private User user;

    @Column(precision = 19, scale = 2)
    @ColumnDefault("0")
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    @Builder.Default
    private Currency currency = Currency.NGN;

    @Column(name = "is_frozen")
    @ColumnDefault("false")
    @Builder.Default
    private boolean isFrozen = false;


    public enum Currency {
        NGN, USD, EUR
    }
}