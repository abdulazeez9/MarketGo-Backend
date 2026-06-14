package com.marketgo.config;

import com.marketgo.user.model.entity.User;
import com.marketgo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;



    @Override
    public void run(String... args) {
        if (userRepository.existsByEmail(adminProperties.getEmail())) return;

        userRepository.save(User.builder()
                .name(adminProperties.getName())
                .email(adminProperties.getEmail())
                .password(passwordEncoder.encode(adminProperties.getPassword()))
                .role(User.Role.ADMIN)
                .verified(true)
                .build());
    }
}