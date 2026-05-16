package com.naveen.ratelimiter.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisTokenService {

    private final StringRedisTemplate redisTemplate;

    public RedisTokenService(
            StringRedisTemplate redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    public void saveTokens(
            String userId,
            int tokens
    ) {

        String key = userId + "_tokens";

        Boolean exists =
                redisTemplate.hasKey(key);

        redisTemplate.opsForValue().set(
                key,
                String.valueOf(tokens)
        );

        if (Boolean.FALSE.equals(exists)) {

            redisTemplate.expire(
                    key,
                    Duration.ofSeconds(60)
            );
        }
    }

    public int getTokens(String userId) {

        String value =
                redisTemplate.opsForValue()
                        .get(userId + "_tokens");

        if (value == null) {
            return -1;
        }

        return Integer.parseInt(value);
    }
}