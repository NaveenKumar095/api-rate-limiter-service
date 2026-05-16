package com.naveen.ratelimiter.controller;

import com.naveen.ratelimiter.dto.RateLimitResponse;
import com.naveen.ratelimiter.service.RateLimiterService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateLimiterController {

    private final RateLimiterService rateLimiterService;

    public RateLimiterController(
            RateLimiterService rateLimiterService
    ) {
        this.rateLimiterService = rateLimiterService;
    }

    @GetMapping("/check")
    public ResponseEntity<RateLimitResponse> checkRateLimit(
            @RequestParam String userId
    ) {

        boolean allowed =
                rateLimiterService.allowRequest(userId);

        int remainingTokens =
                rateLimiterService.getRemainingTokens(userId);

        RateLimitResponse response;

        if (allowed) {

            response = new RateLimitResponse(
                    true,
                    remainingTokens,
                    "Request Allowed"
            );

            return ResponseEntity.ok()
                    .header("X-RateLimit-Limit", "5")
                    .header(
                            "X-RateLimit-Remaining",
                            String.valueOf(remainingTokens)
                    )
                    .body(response);
        }

        response = new RateLimitResponse(
                false,
                remainingTokens,
                "429 Too Many Requests"
        );

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .header("X-RateLimit-Limit", "5")
                .header(
                        "X-RateLimit-Remaining",
                        String.valueOf(remainingTokens)
                )
                .body(response);
    }
}