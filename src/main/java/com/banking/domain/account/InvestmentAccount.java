package com.banking.domain.account;

import com.banking.domain.Transferable;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.*;

import java.util.HashMap;
import java.util.Map;

public class InvestmentAccount extends BaseAccount {
    private Map<String, Double> portfolio;
    private double managementFee;
    private double totalInvested;
    private double totalReturns;
    private String riskProfile;

    public InvestmentAccount(Currency currency, String riskProfile) {
        super(currency, AccountType.INVESTMENT);
        this.portfolio = new HashMap<>();
        this.managementFee = 1.5;
        this.totalInvested = 0.0;
        this.totalReturns = 0.0;
        this.riskProfile = riskProfile;
    }

    @Override
    public double getAvailableBalance() {
        return balance;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return balance >= amount;
    }

    @Override
    public void applyInterest() {
        double returns = calculateReturns();
        balance += returns;
        totalReturns += returns;

        // Apply management fee
        double fee = balance * (managementFee / 100.0 / 12.0);
        balance -= fee;
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public void transfer(Transferable target, double amount) throws InsufficientFundsException,
            InvalidTransactionException, InvalidCurrencyException, DailyLimitExceededException {
        if (target instanceof BaseAccount) {
            BaseAccount targetAccount = (BaseAccount) target;
            if (!this.currency.equals(targetAccount.getCurrency())) {
                throw new InvalidCurrencyException(this.currency.name(), targetAccount.getCurrency().name());
            }
        }
        withdraw(amount);
        target.deposit(amount);
    }

    public void investInAsset(String assetName, double amount) throws InsufficientFundsException {
        if (balance < amount) {
            throw new InsufficientFundsException(amount, balance);
        }
        balance -= amount;
        portfolio.put(assetName, portfolio.getOrDefault(assetName, 0.0) + amount);
        totalInvested += amount;
    }

    public void sellAsset(String assetName, double amount) throws InvalidTransactionException {
        if (!portfolio.containsKey(assetName) || portfolio.get(assetName) < amount) {
            throw new InvalidTransactionException(accountId, "Insufficient asset holdings");
        }
        portfolio.put(assetName, portfolio.get(assetName) - amount);
        balance += amount;
    }

    private double calculateReturns() {
        double returnRate = switch (riskProfile) {
            case "CONSERVATIVE" -> 0.04;
            case "MODERATE" -> 0.07;
            case "AGGRESSIVE" -> 0.12;
            default -> 0.05;
        };
        return balance * (returnRate / 12.0);
    }

    public double getPortfolioValue() {
        return portfolio.values().stream().mapToDouble(Double::doubleValue).sum() + balance;
    }

    // Getters
    public Map<String, Double> getPortfolio() {
        return new HashMap<>(portfolio);
    }

    public double getTotalInvested() {
        return totalInvested;
    }

    public double getTotalReturns() {
        return totalReturns;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(String riskProfile) {
        this.riskProfile = riskProfile;
    }
}
