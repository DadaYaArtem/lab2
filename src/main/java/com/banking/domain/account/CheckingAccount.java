package com.banking.domain.account;

import com.banking.domain.Transferable;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.*;

public class CheckingAccount extends BaseAccount {
    private double overdraftLimit;
    private double monthlyFee;
    private int freeTransactionsPerMonth;
    private int transactionCount;

    public CheckingAccount(Currency currency, double overdraftLimit) {
        super(currency, AccountType.CHECKING);
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = 5.0;
        this.freeTransactionsPerMonth = 10;
        this.transactionCount = 0;
    }

    @Override
    public double getAvailableBalance() {
        return balance + overdraftLimit;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return getAvailableBalance() >= amount;
    }

    @Override
    public void applyInterest() {
        // Checking accounts typically don't earn interest
        // but we charge monthly fee
        if (balance >= monthlyFee) {
            balance -= monthlyFee;
        }
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
        transactionCount++;
    }

    public void applyTransactionFee() {
        if (transactionCount > freeTransactionsPerMonth) {
            double fee = (transactionCount - freeTransactionsPerMonth) * 0.5;
            balance -= fee;
        }
    }

    public void resetMonthlyTransactions() {
        transactionCount = 0;
    }

    // Getters and Setters
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public int getTransactionCount() {
        return transactionCount;
    }
}
