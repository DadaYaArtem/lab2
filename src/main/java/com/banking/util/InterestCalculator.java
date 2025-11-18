package com.banking.util;

public class InterestCalculator {
    public double calculateSimpleInterest(double principal, double rate, int periods) {
        return principal * (rate / 100.0) * periods;
    }

    public double calculateCompoundInterest(double principal, double rate, int periods, int compoundingFrequency) {
        double ratePerPeriod = rate / 100.0 / compoundingFrequency;
        int totalPeriods = periods * compoundingFrequency;
        return principal * Math.pow(1 + ratePerPeriod, totalPeriods) - principal;
    }

    public double calculateMonthlyPayment(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 100.0 / 12.0;
        return principal * (monthlyRate * Math.pow(1 + monthlyRate, months)) /
                (Math.pow(1 + monthlyRate, months) - 1);
    }

    public double calculateAPY(double apr, int compoundingFrequency) {
        return Math.pow(1 + (apr / 100.0 / compoundingFrequency), compoundingFrequency) - 1;
    }

    public double calculateFutureValue(double presentValue, double rate, int periods) {
        return presentValue * Math.pow(1 + rate / 100.0, periods);
    }
}
