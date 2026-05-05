package com.marketgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MarketGoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarketGoApplication.class, args);
    }
}