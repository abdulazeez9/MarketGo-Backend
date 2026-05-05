package com.marketgo.user.model.entity;

import com.marketgo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity {
    private UUID userId;

    private String label;
    private String street;
    private String city;
    private String state;

    private Double lat;
    private Double lng;
}
