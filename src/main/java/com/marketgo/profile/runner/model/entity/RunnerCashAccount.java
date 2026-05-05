package com.marketgo.profile.runner.model.entity;

import com.marketgo.common.entity.BaseEntity;
import  jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "runner_cash_accounts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RunnerCashAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id")
    private RunnerProfile runner;

    private BigDecimal outstandingBalance = BigDecimal.ZERO;

    private LocalDateTime updatedAt;
}
