package com.marketgo.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseProfileRepository<T> extends JpaRepository<T, UUID> {

    Optional<T> findByUserId(UUID uuid);

    boolean existsByUserId(UUID uuid);
}
