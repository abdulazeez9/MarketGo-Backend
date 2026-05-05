package com.marketgo.user.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String phone;
    private  Boolean is_verified;

    @Enumerated(EnumType.STRING)
    private Role role;
}



