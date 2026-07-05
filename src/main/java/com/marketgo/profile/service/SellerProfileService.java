package com.marketgo.profile.service;


import com.marketgo.exception.AppException;
import com.marketgo.profile.model.dto.request.seller.SellerProfileCreateRequest;
import com.marketgo.profile.model.dto.request.seller.SellerProfileUpdateRequest;
import com.marketgo.profile.model.dto.response.seller.SellerProfileResponse;
import com.marketgo.profile.model.entity.seller.SellerProfile;
import com.marketgo.profile.repository.SellerProfileRepository;
import com.marketgo.user.model.entity.Address;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.user.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerProfileService {

    private final SellerProfileRepository sellerProfileRepo;
    private final UserRepository userRepo;
    private final AddressRepository addressRepo;

    public SellerProfileResponse getByUserId(UUID userId) {
        return toResponse(findByUserIdOrThrow(userId));
    }

    public SellerProfileResponse create(SellerProfileCreateRequest request) {
        if (sellerProfileRepo.existsByUserId(request.userId())) {
            throw AppException.conflict("Seller profile already exists");
        }
        User user = userRepo.findById(request.userId())
                .orElseThrow(() -> AppException.notFound("User not found"));

        Address shopLocation = request.shopLocationId() != null
                ? addressRepo.findById(request.shopLocationId())
                  .orElseThrow(() -> AppException.notFound("Address not found"))
                : null;

        SellerProfile profile = SellerProfile.builder()
                .user(user)
                .shopName(request.shopName())
                .shopLocation(shopLocation)
                .active(true)
                .build();

        return toResponse(sellerProfileRepo.save(profile));
    }

    public SellerProfileResponse update(UUID userId, SellerProfileUpdateRequest request) {
        SellerProfile profile = findByUserIdOrThrow(userId);

        profile.setShopName(request.shopName());
        if (request.shopLocationId() != null) {
            Address address = addressRepo.findById(request.shopLocationId())
                    .orElseThrow(() -> AppException.notFound("Address not found"));
            profile.setShopLocation(address);
        }

        return toResponse(sellerProfileRepo.save(profile));
    }

    public SellerProfileResponse toggleActiveStatus(UUID userId, boolean active) {
        SellerProfile profile = findByUserIdOrThrow(userId);
        profile.setActive(active);
        return toResponse(sellerProfileRepo.save(profile));
    }

    public boolean existsByUserId(UUID userId) {
        return sellerProfileRepo.existsByUserId(userId);
    }

    private SellerProfile findByUserIdOrThrow(UUID userId) {
        return sellerProfileRepo.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Seller profile not found"));
    }

    private SellerProfileResponse toResponse(SellerProfile p) {
        Address location = p.getShopLocation();
        return new SellerProfileResponse(
                p.getId(),
                p.getUser().getId(),
                p.getUser().getName(),
                p.getShopName(),
                location != null ? location.getId() : null,
                location != null ? location.getLabel() : null,
                p.getRating(),
                p.isActive(),
                p.getCreatedAt()
        );
    }
}
