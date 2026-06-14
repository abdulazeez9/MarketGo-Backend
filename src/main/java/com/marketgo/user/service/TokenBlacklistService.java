package com.marketgo.user.service;


import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate <String, String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "token_blacklist:";

    /**
     * Add token to blacklist when user logs out
     * TTL is set to match token expiration time
     */
    public void blacklistToken(String token, long expirationTimeInSeconds) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "revoked", expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    /**
     * Check if token is blacklisted
     */
    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Remove token from blacklist (optional, for edge cases)
     */
    public void removeTokenFromBlacklist(String token) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.delete(key);
    }
}
