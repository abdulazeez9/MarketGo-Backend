package com.marketgo.user.repository;

import com.marketgo.user.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findByUserIdAndIsDefaultTrue(UUID userId);
    List<Address> findAllByUserId(UUID userId);
}
