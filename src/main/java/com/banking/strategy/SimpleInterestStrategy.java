package com.banking.strategy;

public class SimpleInterestStrategy implements InterestStrategy {
    @Override
    public double calculateInterest(double balance, double rate) {
        return balance * (rate / 100.0 / 12.0);
    }
}
