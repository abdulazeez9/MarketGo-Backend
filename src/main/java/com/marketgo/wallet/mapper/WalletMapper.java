package com.marketgo.wallet.mapper;


import com.marketgo.wallet.model.dto.response.WalletResponse;
import com.marketgo.wallet.model.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletResponse toWalletResponse(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getUser().getId(),
                wallet.getBalance(),
                wallet.getCurrency().name(),
                wallet.isFrozen()
        );
    }
}
