package com.marketgo.user.mapper;


import com.marketgo.user.model.dto.response.AuthResponse;
import com.marketgo.user.model.dto.response.UserResponse;
import com.marketgo.user.model.entity.User;
import com.marketgo.wallet.mapper.WalletMapper;
import com.marketgo.wallet.model.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final WalletMapper walletMapper;

    public UserResponse toUserResponse(User user, Wallet wallet) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getPhone(),
                user.isVerified(),
                walletMapper.toWalletResponse(wallet),
                user.getCreatedAt()
        );
    }

    public AuthResponse toAuthResponse(User user, String token) {
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                token
        );
    }
}