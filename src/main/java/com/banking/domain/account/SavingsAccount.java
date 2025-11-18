package com.banking.domain.account;

import com.banking.domain.Transferable;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.*;

public class SavingsAccount extends BaseAccount {
    private double interestRate;
    private int maxWithdrawalsPerMonth;
    private int withdrawalCount;
    private double minimumBalance;

    public SavingsAccount(Currency currency, double interestRate, double minimumBalance) {
        super(currency, AccountType.SAVINGS);
        this.interestRate = interestRate;
        this.maxWithdrawalsPerMonth = 6;
        this.withdrawalCount = 0;
        this.minimumBalance = minimumBalance;
    }

    @Override
    public double getAvailableBalance() {
        return Math.max(0, balance - minimumBalance);
    }

    @Override
    public boolean canWithdraw(double amount) {
        return balance - amount >= minimumBalance && withdrawalCount < maxWithdrawalsPerMonth;
    }

    @Override
    public void applyInterest() {
        double interest = balance * (interestRate / 100.0 / 12.0);
        balance += interest;
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public void transfer(Transferable target, double amount) throws InsufficientFundsException,
            InvalidTransactionException, InvalidCurrencyException, DailyLimitExceededException {
        if (withdrawalCount >= maxWithdrawalsPerMonth) {
            throw new DailyLimitExceededException(maxWithdrawalsPerMonth, withdrawalCount);
        }
        if (target instanceof BaseAccount) {
            BaseAccount targetAccount = (BaseAccount) target;
            if (!this.currency.equals(targetAccount.getCurrency())) {
                throw new InvalidCurrencyException(this.currency.name(), targetAccount.getCurrency().name());
            }
        }
        withdraw(amount);
        target.deposit(amount);
        withdrawalCount++;
    }

    public void resetMonthlyWithdrawals() {
        withdrawalCount = 0;
    }

    public double calculateProjectedBalance(int months) {
        double projectedBalance = balance;
        for (int i = 0; i < months; i++) {
            projectedBalance += projectedBalance * (interestRate / 100.0 / 12.0);
        }
        return projectedBalance;
    }

    // Getters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getWithdrawalCount() {
        return withdrawalCount;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
}
