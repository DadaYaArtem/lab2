package com.banking.strategy;

public class CompoundInterestStrategy implements InterestStrategy {
    private int compoundingFrequency;

    public CompoundInterestStrategy(int compoundingFrequency) {
        this.compoundingFrequency = compoundingFrequency;
    }

    @Override
    public double calculateInterest(double balance, double rate) {
        double ratePerPeriod = rate / 100.0 / compoundingFrequency;
        return balance * (Math.pow(1 + ratePerPeriod, compoundingFrequency) - 1);
    }
}
