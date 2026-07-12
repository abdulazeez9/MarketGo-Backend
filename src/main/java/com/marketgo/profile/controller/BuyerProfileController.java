package com.marketgo.profile.controller;

import com.marketgo.profile.model.dto.request.BuyerProfileUpdateRequest;
import com.marketgo.profile.model.dto.response.BuyerProfileResponse;
import com.marketgo.profile.service.BuyerProfileService;
import com.marketgo.utils.ApiResponse;
import com.marketgo.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('BUYER')")
@RequestMapping("/api/profile/buyer")
public class BuyerProfileController {

    private final BuyerProfileService buyerProfileService;

    // GET the logged-in buyer's own profile
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<BuyerProfileResponse>> getMyProfile(Authentication auth) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        BuyerProfileResponse data = buyerProfileService.getByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("Profile fetched", data));
    }

    // UPDATE the logged-in buyer's own profile
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<BuyerProfileResponse>> updateMyProfile(
            Authentication auth,
            @RequestBody BuyerProfileUpdateRequest request) {
        UUID userId = AuthUtils.getCurrentUserId(auth);
        BuyerProfileResponse data = buyerProfileService.updateRequest(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated", data));
    }


}