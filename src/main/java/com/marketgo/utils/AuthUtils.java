package com.marketgo.utils;


import org.springframework.security.core.Authentication;

import java.util.UUID;

public final class AuthUtils {

    private AuthUtils() {}

    public static UUID getCurrentUserId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}
