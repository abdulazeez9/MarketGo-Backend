package com.marketgo.user.repository;

import com.marketgo.user.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Override
    Optional<Address> findById(UUID uuid);
}
