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

    @Override
    public void run(String... args) {
        if (userRepository.existsByEmail("admin@marketgo.com")) return;

        userRepository.save(User.builder()
                .name("Super Admin")
                .email("admin@marketgo.com")
                .password(passwordEncoder.encode("changeme123"))
                .role(User.Role.ADMIN)
                .verified(true)
                .build());
    }
}