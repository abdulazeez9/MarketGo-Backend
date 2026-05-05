
package com.marketgo.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(updatable = false)
    protected  LocalDateTime created_at;

    protected LocalDateTime updated_at;

    @PrePersist
     void oncreate(){
        created_at = LocalDateTime.now();
        updated_at = LocalDateTime.now();
    }

    @PreUpdate
    void onupdate(){
        updated_at = LocalDateTime.now();
    }
};