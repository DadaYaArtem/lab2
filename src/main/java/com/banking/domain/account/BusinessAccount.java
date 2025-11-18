package com.banking.domain.account;

import com.banking.domain.Transferable;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.*;

import java.util.ArrayList;
import java.util.List;

public class BusinessAccount extends BaseAccount {
    private String businessName;
    private String taxId;
    private double transactionFeeRate;
    private List<String> authorizedUsers;
    private double creditLine;
    private double usedCredit;

    public BusinessAccount(Currency currency, String businessName, String taxId, double creditLine) {
        super(currency, AccountType.BUSINESS);
        this.businessName = businessName;
        this.taxId = taxId;
        this.transactionFeeRate = 0.3;
        this.authorizedUsers = new ArrayList<>();
        this.creditLine = creditLine;
        this.usedCredit = 0.0;
    }

    @Override
    public double getAvailableBalance() {
        return balance + (creditLine - usedCredit);
    }

    @Override
    public boolean canWithdraw(double amount) {
        return getAvailableBalance() >= amount;
    }

    @Override
    public void applyInterest() {
        // Apply interest on used credit
        if (usedCredit > 0) {
            double interest = usedCredit * (0.08 / 12.0); // 8% annual rate
            usedCredit += interest;
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

        double fee = amount * (transactionFeeRate / 100.0);
        double totalAmount = amount + fee;

        withdraw(totalAmount);
        target.deposit(amount);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (!isActive) {
            throw new InvalidTransactionException(accountId, "Account is not active");
        }

        if (balance >= amount) {
            balance -= amount;
        } else {
            double needed = amount - balance;
            if (usedCredit + needed > creditLine) {
                throw new InsufficientFundsException(amount, getAvailableBalance());
            }
            balance = 0;
            usedCredit += needed;
        }
        updatedAt = java.time.LocalDateTime.now();
    }

    public void addAuthorizedUser(String userId) {
        if (!authorizedUsers.contains(userId)) {
            authorizedUsers.add(userId);
        }
    }

    public void removeAuthorizedUser(String userId) {
        authorizedUsers.remove(userId);
    }

    public boolean isUserAuthorized(String userId) {
        return authorizedUsers.contains(userId);
    }

    public void payOffCredit(double amount) throws InvalidTransactionException {
        if (amount > usedCredit) {
            throw new InvalidTransactionException(accountId, "Payment exceeds used credit");
        }
        usedCredit -= amount;
    }

    // Getters
    public String getBusinessName() {
        return businessName;
    }

    public String getTaxId() {
        return taxId;
    }

    public List<String> getAuthorizedUsers() {
        return new ArrayList<>(authorizedUsers);
    }

    public double getCreditLine() {
        return creditLine;
    }

    public double getUsedCredit() {
        return usedCredit;
    }
}
