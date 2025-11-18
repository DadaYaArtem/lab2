package com.banking.exception;

public class DailyLimitExceededException extends Exception {
    private double dailyLimit;
    private double currentUsage;

    public DailyLimitExceededException(double dailyLimit, double currentUsage) {
        super(String.format("Daily limit exceeded: limit %.2f, current usage %.2f", dailyLimit, currentUsage));
        this.dailyLimit = dailyLimit;
        this.currentUsage = currentUsage;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    public double getCurrentUsage() {
        return currentUsage;
    }
}
