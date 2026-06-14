package com.marketgo.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.admin")
@Getter
@Setter
public class AdminProperties {
    private String name;
    private String email;
    private String password;

}
