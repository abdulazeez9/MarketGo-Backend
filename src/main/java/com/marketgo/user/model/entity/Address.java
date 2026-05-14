package com.marketgo.user.model.entity;

import com.marketgo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id", nullable = false)
    private User user;

    @Column(length = 50)
    private String label;

    private String street;
    private String city;
    private String state;

    private BigDecimal lat;
    private BigDecimal lon;

    @Column(name= "is_default")
    private boolean isDefault;
}
