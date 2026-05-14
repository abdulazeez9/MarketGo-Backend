package com.marketgo.profile.runner.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.math.BigDecimal;

@Entity
@Table(name = "runner_profiles",
        indexes = {
                @Index(name = "idx_runner_location", columnList = "current_lat, current_lon")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RunnerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "is_available")
    private boolean available = false;

    @Column(name = "current_lat")
    private BigDecimal currentLat;

    @Column(name = "current_lon")
    private BigDecimal currentLon;


    private BigDecimal rating;


    public enum VehicleType {
        BIKE,
        CAR,
        TRICYCLE,
        WALKER
    }

}
