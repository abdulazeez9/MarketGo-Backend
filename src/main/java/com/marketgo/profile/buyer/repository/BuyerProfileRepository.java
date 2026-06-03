package com.marketgo.profile.buyer.repository;

import com.marketgo.profile.buyer.model.entity.BuyerProfile;
import com.marketgo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface BuyerProfileRepository extends JpaRepository<BuyerProfile, UUID> {

    Optional<BuyerProfile> findByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

    List<BuyerProfile> User(User user);
}
