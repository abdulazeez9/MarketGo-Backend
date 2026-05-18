package com.marketgo.wallet.service;


import com.marketgo.exception.AppException;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.wallet.model.dto.response.WalletResponse;
import com.marketgo.wallet.model.entity.Wallet;
import com.marketgo.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;


    public WalletResponse getWalletByUserId(UUID userId) {

        Wallet wallet = walletRepository.findByUserId(userId).orElseThrow(() -> AppException.notFound("Wallet not found"));
        return new WalletResponse(
                wallet.getId(),
                wallet.getUser().getId(),
                wallet.getBalance(),
                wallet.getCurrency().name(),
                wallet.isFrozen(),
                wallet.getCreatedAt()
        )
                ;
    }

}
