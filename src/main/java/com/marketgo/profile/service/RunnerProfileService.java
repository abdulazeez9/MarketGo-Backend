package com.marketgo.profile.service;


import com.marketgo.exception.AppException;
import com.marketgo.profile.model.dto.request.RunnerLocationUpdateRequest;
import com.marketgo.profile.model.dto.request.RunnerProfileCreateRequest;
import com.marketgo.profile.model.dto.response.RunnerProfileResponse;
import com.marketgo.profile.model.entity.RunnerProfile;
import com.marketgo.profile.repository.RunnerProfileRepository;
import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RunnerProfileService {

    private final RunnerProfileRepository runnerProfileRepo;
    private  final UserRepository userRepo;

    public RunnerProfileResponse getByUserId(UUID userId) {
        return toResponse(findByUserIdOrThrow(userId));
    }

    public RunnerProfileResponse create(RunnerProfileCreateRequest request) {
        if (runnerProfileRepo.existsByUserId(request.userId())) {
            throw AppException.conflict("Runner profile already exists");
        }
        User user = userRepo.findById(request.userId())
                .orElseThrow(() -> AppException.notFound("User not found"));

        RunnerProfile profile = RunnerProfile.builder()
                .user(user)
                .vehicleType(request.vehicleType())
                .available(false)
                .build();

        return toResponse(runnerProfileRepo.save(profile));
    }

    public RunnerProfileResponse updateAvailability(UUID userId, boolean available) {
        RunnerProfile profile = findByUserIdOrThrow(userId);
        profile.setAvailable(available);
        return toResponse(runnerProfileRepo.save(profile));
    }

    public RunnerProfileResponse updateLocation(UUID userId, RunnerLocationUpdateRequest request) {
        RunnerProfile profile = findByUserIdOrThrow(userId);
        profile.setCurrentLat(request.currentLat());
        profile.setCurrentLon(request.currentLon());
        return toResponse(runnerProfileRepo.save(profile));
    }

    public boolean existsByUserId(UUID userId) {
        return runnerProfileRepo.existsByUserId(userId);
    }

    private RunnerProfile findByUserIdOrThrow(UUID userId) {
        return runnerProfileRepo.findByUserId(userId)
                .orElseThrow(() -> AppException.notFound("Runner profile not found"));
    }

    private RunnerProfileResponse toResponse(RunnerProfile p) {
        return new RunnerProfileResponse(
                p.getId(),
                p.getUser().getId(),
                p.getUser().getName(),
                p.getVehicleType(),
                p.isAvailable(),
                p.getCurrentLat(),
                p.getCurrentLon(),
                p.getRating(),
                p.getCreatedAt()
        );
    }

}
