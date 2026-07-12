package com.marketgo.profile.model.entity;

import com.marketgo.common.entity.BaseEntity;
import  jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "runner_cash_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RunnerCashAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id", nullable = false, unique = true)
    private RunnerProfile runner;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal outstandingBalance = BigDecimal.ZERO;
}
