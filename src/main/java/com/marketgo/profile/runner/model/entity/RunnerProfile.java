package com.marketgo.profile.runner.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "runner_profiles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RunnerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String vehicleType;
    private boolean isAvailable = false;

    private BigDecimal currentLat;
    private BigDecimal currentLng;
    private BigDecimal rating;

}
