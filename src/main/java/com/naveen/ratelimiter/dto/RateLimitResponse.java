package com.naveen.ratelimiter.dto;

public class RateLimitResponse {

    private boolean allowed;
    private int remainingTokens;
    private String message;

    public RateLimitResponse(
            boolean allowed,
            int remainingTokens,
            String message
    ) {
        this.allowed = allowed;
        this.remainingTokens = remainingTokens;
        this.message = message;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public int getRemainingTokens() {
        return remainingTokens;
    }

    public String getMessage() {
        return message;
    }
}