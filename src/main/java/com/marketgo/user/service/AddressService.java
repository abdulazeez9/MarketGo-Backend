package com.marketgo.user.service;

import com.marketgo.exception.AppException;
import com.marketgo.profile.repository.BuyerProfileRepository;
import com.marketgo.user.model.dto.request.AddressRequest;
import com.marketgo.user.model.dto.response.AddressResponse;
import com.marketgo.user.model.entity.Address;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.AddressRepository;
import com.marketgo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final BuyerProfileRepository bupRepository;

    //Get my Addresses
    public List<AddressResponse> getMyAddresses(UUID userId) {
        return addressRepository.findAllByUserId(userId).stream()
                .map(this::toResponse).toList();
    }

    // Create new address
    @Transactional
    public AddressResponse createAddress(UUID userId, AddressRequest request) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> AppException.notFound("User not found"));

        boolean hasNoAddresses = addressRepository.findAllByUserId(userId).isEmpty();
        boolean shouldBeDefault = hasNoAddresses || request.isDefault();

        if (shouldBeDefault) {
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(existing -> {
                        existing.setDefault(false);
                        addressRepository.save(existing);
                    });
        }

        Address address = Address.builder()
                .user(user)
                .label(request.label())
                .street(request.street())
                .city(request.city())
                .state(request.state())
                .lat(request.lat())
                .lon(request.lon())
                .isDefault(shouldBeDefault)
                .build();

        Address saved = addressRepository.save(address);

        if (shouldBeDefault) {
            bupRepository.findByUserId(userId)
                    .ifPresent(profile -> {
                        profile.setDefaultAddress(saved);
                        bupRepository.save(profile);
                    });
        }

        return toResponse(saved);
    }

    // Update request
    public AddressResponse updateAddress(UUID userId, UUID addressId, AddressRequest request) {
        Address address = findOwnedAddressOrThrow(userId, addressId);

        if (request.label() != null) address.setLabel(request.label());
        if (request.street() != null) address.setStreet(request.street());
        if (request.city() != null) address.setCity(request.city());
        if (request.state() != null) address.setState(request.state());
        if (request.lat() != null) address.setLat(request.lat());
        if (request.lon() != null) address.setLon(request.lon());

        return toResponse(addressRepository.save(address));
    }

    // Delete address
    public void delete(UUID userId, UUID addressId) {
        Address address = findOwnedAddressOrThrow(userId, addressId);
        addressRepository.delete(address);
    }

    // Find own address
    private Address findOwnedAddressOrThrow(UUID userId, UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> AppException.notFound("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw AppException.notFound("Address not found");
        }
        return address;
    }


    // Private response
    private AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getLabel(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getLat(),
                address.getLon(),
                address.isDefault()
        );
    }

}
