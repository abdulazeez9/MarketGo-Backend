package com.marketgo.user.service;

import com.marketgo.user.model.dto.LoginRequest;
import com.marketgo.user.model.dto.RegisterRequest;
import com.marketgo.user.model.dto.LoginResponse;

import java.util.List;

public interface UserService {
    LoginResponse register(RegisterRequest createUserRequest);

    LoginResponse login(LoginRequest loginRequest);

    List<LoginResponse> getAllUsers();
}
