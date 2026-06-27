package com.marketgo.config;

import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.marketgo.chat.model.entity.ConversationParticipant.ParticipantRole.admin;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;


    @Override
    public void run(String... args) {
        log.info("Starting data seeder...");
        log.info("Admin email from config: {}", adminProperties.getEmail());

        if (userRepository.existsByEmail(adminProperties.getEmail())) {
            log.info("✓ Admin user already exists");
            return;
        }

        User admin = userRepository.save(User.builder()
                .name(adminProperties.getName())
                .email(adminProperties.getEmail())
                .password(passwordEncoder.encode(adminProperties.getPassword()))
                .role(User.Role.ADMIN)
                .verified(true)
                .build());

        log.info("✓ Admin user created: {}", admin.getEmail());
    }
}