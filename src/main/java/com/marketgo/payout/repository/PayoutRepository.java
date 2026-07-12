package com.marketgo.payout.repository;

import com.marketgo.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PayoutRepository extends JpaRepository<Payment, UUID> {
}
