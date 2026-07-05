package com.marketgo.profile.repository;

import com.marketgo.profile.model.entity.buyer.BuyerProfile;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BuyerProfileRepository extends BaseProfileRepository<BuyerProfile> {


}
