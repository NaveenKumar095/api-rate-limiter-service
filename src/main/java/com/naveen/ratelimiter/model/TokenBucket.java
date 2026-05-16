package com.naveen.ratelimiter.model;

public class TokenBucket {

    private int capacity;
    private int tokens;
    private long lastRefillTime;

    public TokenBucket(int capacity) {
        this.capacity = capacity;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTokens() {
        return tokens;
    }

    public int getRemainingTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public long getLastRefillTime() {
        return lastRefillTime;
    }

    public void setLastRefillTime(long lastRefillTime) {
        this.lastRefillTime = lastRefillTime;
    }
}