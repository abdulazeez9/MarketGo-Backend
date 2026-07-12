package com.marketgo.profile.service;

import com.marketgo.exception.AppException;
import com.marketgo.profile.model.dto.request.BuyerProfileUpdateRequest;
import com.marketgo.profile.model.dto.response.BuyerProfileResponse;
import com.marketgo.profile.model.entity.BuyerProfile;
import com.marketgo.profile.repository.BuyerProfileRepository;
import com.marketgo.user.model.entity.Address;
import com.marketgo.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuyerProfileService {

    private final BuyerProfileRepository buyerProfileRepo;
    private final AddressRepository addressRepo;

    public BuyerProfileResponse getByUserId(UUID userId) {
        return toResponse(findByUserIdOrThrow(userId));
    }

    public BuyerProfileResponse updateRequest(UUID userId, BuyerProfileUpdateRequest request) {
        BuyerProfile profile = findByUserIdOrThrow(userId);

        if (request.defaultAddressId() != null) {
            Address address = addressRepo.findById(request.defaultAddressId())
                    .orElseThrow(() -> AppException.notFound("Address not found"));
            profile.setDefaultAddress(address);
        }

        if (request.mobilityLimited() != null) {
            profile.setMobilityLimited(request.mobilityLimited());
        }

        return toResponse(buyerProfileRepo.save(profile));
    }

    private BuyerProfile findByUserIdOrThrow(UUID userId) {
        return buyerProfileRepo.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Buyer profile not found"));
    }

    private BuyerProfileResponse toResponse(BuyerProfile profile) {
        Address address = profile.getDefaultAddress();
        return new BuyerProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getUser().getName(),
                profile.getUser().getEmail(),
                address != null ? address.getId() : null,
                address != null ? address.getLabel() : null,
                address != null ? address.getStreet() : null,
                address != null ? address.getCity() : null,
                profile.isMobilityLimited(),
                profile.getCreatedAt()
        );
    }
}