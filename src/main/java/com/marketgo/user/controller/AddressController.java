package com.marketgo.user.controller;

import com.marketgo.user.model.dto.request.AddressRequest;
import com.marketgo.utils.AuthUtils;
import org.springframework.web.bind.annotation.RestController;


import com.marketgo.user.model.dto.response.AddressResponse;
import com.marketgo.user.service.AddressService;
import com.marketgo.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getMyAddresses(Authentication auth) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        List<AddressResponse> data = addressService.getMyAddresses(userId);
        return ResponseEntity.ok(ApiResponse.success("Addresses fetched", data));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> create(
            Authentication auth,
            @RequestBody AddressRequest request) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        AddressResponse data = addressService.createAddress(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Address created", data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> update(
            Authentication auth,
            @PathVariable UUID id,
            @RequestBody AddressRequest request) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        AddressResponse data = addressService.updateAddress(userId, id, request);
        return ResponseEntity.ok(ApiResponse.success("Address updated", data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            Authentication auth,
            @PathVariable UUID id) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        addressService.delete(userId, id);
        return ResponseEntity.ok(ApiResponse.success("Address deleted"));
    }


}
