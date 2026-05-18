package com.marketgo.profile.buyer.service;


import com.marketgo.exception.AppException;
import com.marketgo.profile.buyer.model.dto.response.BuyerProfileResponse;
import com.marketgo.profile.buyer.model.entity.BuyerProfile;
import com.marketgo.profile.buyer.repository.BuyerProfileRepository;
import com.marketgo.user.model.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyerProfileService {

    private final BuyerProfileRepository buyerProfileRepository;


    public BuyerProfileResponse getBuyerProfileById(UUID buyerProfileId) {
        BuyerProfile buyerProfile = buyerProfileRepository.findByUserId(buyerProfileId).orElseThrow(() -> AppException.notFound("Buyer profile not found"));

        Address address = buyerProfile.getDefaultAddress();

        return new BuyerProfileResponse(
                buyerProfile.getId(),

                // User fields
                buyerProfile.getUser().getId(),
                buyerProfile.getUser().getName(),
                buyerProfile.getUser().getEmail(),

                // address fields — safely handle null
                address != null ? address.getId() : null,
                address != null ? address.getLabel() : null,
                address != null ? address.getStreet() : null,
                address != null ? address.getCity() : null,


                buyerProfile.isMobilityLimited(),
                buyerProfile.getCreatedAt()
        );
    }

}
