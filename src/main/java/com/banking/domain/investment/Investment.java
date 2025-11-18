package com.banking.domain.investment;

import com.banking.domain.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Investment {
    private String investmentId;
    private Customer investor;
    private String investmentType;
    private double principalAmount;
    private double currentValue;
    private double returnRate;
    private LocalDateTime purchaseDate;
    private int termInMonths;
    private boolean isMatured;

    public Investment(Customer investor, String investmentType, double principalAmount, double returnRate, int termInMonths) {
        this.investmentId = UUID.randomUUID().toString();
        this.investor = investor;
        this.investmentType = investmentType;
        this.principalAmount = principalAmount;
        this.currentValue = principalAmount;
        this.returnRate = returnRate;
        this.purchaseDate = LocalDateTime.now();
        this.termInMonths = termInMonths;
        this.isMatured = false;
    }

    public void updateValue(double marketChange) {
        this.currentValue += marketChange;
    }

    public double calculateReturns() {
        return currentValue - principalAmount;
    }

    public double calculateReturnPercentage() {
        return ((currentValue - principalAmount) / principalAmount) * 100;
    }

    public void mature() {
        this.isMatured = true;
    }

    public boolean canRedeem() {
        LocalDateTime maturityDate = purchaseDate.plusMonths(termInMonths);
        return LocalDateTime.now().isAfter(maturityDate) || isMatured;
    }

    public double projectFutureValue(int monthsAhead) {
        double monthlyRate = returnRate / 12.0 / 100.0;
        return currentValue * Math.pow(1 + monthlyRate, monthsAhead);
    }

    // Getters
    public String getInvestmentId() {
        return investmentId;
    }

    public Customer getInvestor() {
        return investor;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double getReturnRate() {
        return returnRate;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public boolean isMatured() {
        return isMatured;
    }
}
