package com.marketgo.config;

import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import com.marketgo.wallet.model.entity.Wallet;
import com.marketgo.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;


    @Override
    @Transactional
    public void run(String... args) {
        log.info("Starting data seeder...");
        log.info("Admin email from config: {}", adminProperties.getEmail());

        Optional<User> existingAdmin = userRepository.findByEmail(adminProperties.getEmail());

        if (existingAdmin.isPresent()) {
            User admin = existingAdmin.get();

            // Ensure the admin has a wallet
            if (walletRepository.findByUserId(admin.getId()).isEmpty()) {
                walletRepository.save(
                        Wallet.builder()
                                .user(admin)
                                .balance(BigDecimal.ZERO)
                                .build()
                );

                log.info("✓ Wallet created for existing admin");
            }

            log.info("✓ Admin user already exists");
            return;
        }

        // Create admin
        User admin = userRepository.save(
                User.builder()
                        .name(adminProperties.getName())
                        .email(adminProperties.getEmail())
                        .password(passwordEncoder.encode(adminProperties.getPassword()))
                        .role(User.Role.ADMIN)
                        .verified(true)
                        .build()
        );

        // Create wallet
        walletRepository.save(
                Wallet.builder()
                        .user(admin)
                        .balance(BigDecimal.ZERO)
                        .build()
        );

        log.info("✓ Admin user created: {}", admin.getEmail());
    }
}