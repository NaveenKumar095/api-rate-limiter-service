package com.naveen.ratelimiter.service;

import com.naveen.ratelimiter.redis.RedisTokenService;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    private final RedisTokenService redisTokenService;

    private static final int CAPACITY = 5;

    public RateLimiterService(
            RedisTokenService redisTokenService
    ) {
        this.redisTokenService = redisTokenService;
    }

    public synchronized boolean allowRequest(
            String userId
    ) {

        int tokens =
                redisTokenService.getTokens(userId);

        if (tokens == -1) {
            tokens = CAPACITY;
        }

        if (tokens > 0) {

            tokens--;

            redisTokenService.saveTokens(
                    userId,
                    tokens
            );

            return true;
        }

        return false;
    }

    public int getRemainingTokens(
            String userId
    ) {

        int tokens =
                redisTokenService.getTokens(userId);

        if (tokens == -1) {
            return CAPACITY;
        }

        return tokens;
    }
}