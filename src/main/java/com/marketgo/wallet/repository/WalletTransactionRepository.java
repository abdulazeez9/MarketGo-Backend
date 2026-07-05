package com.marketgo.wallet.repository;

import com.marketgo.wallet.model.entity.WalletTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {
    Optional<WalletTransaction> findByWallet_Id(UUID walletId);

    List<WalletTransaction> findAllByWallet_Id(UUID walletId);

    Page<WalletTransaction> findAllByWallet_Id(UUID walletId, Pageable pageable);
}
