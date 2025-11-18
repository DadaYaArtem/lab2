package com.banking.domain.account;

import com.banking.domain.Auditable;
import com.banking.domain.Transferable;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.*;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseAccount implements Transferable, Auditable {
    protected String accountId;
    protected String accountNumber;
    protected double balance;
    protected Currency currency;
    protected AccountType accountType;
    protected boolean isActive;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected String createdBy;
    protected String lastModifiedBy;

    public BaseAccount(Currency currency, AccountType accountType) {
        this.accountId = UUID.randomUUID().toString();
        this.accountNumber = generateAccountNumber();
        this.balance = 0.0;
        this.currency = currency;
        this.accountType = accountType;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.lastModifiedBy = "SYSTEM";
    }

    protected String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() + (int) (Math.random() * 1000);
    }

    public abstract double getAvailableBalance();

    public abstract boolean canWithdraw(double amount);

    public abstract void applyInterest();

    @Override
    public void deposit(double amount) throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException(accountId, "Amount must be positive");
        }
        if (!isActive) {
            throw new InvalidTransactionException(accountId, "Account is not active");
        }
        this.balance += amount;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException(accountId, "Amount must be positive");
        }
        if (!isActive) {
            throw new InvalidTransactionException(accountId, "Account is not active");
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(amount, getAvailableBalance());
        }
        this.balance -= amount;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAccountId() {
        return accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String getAuditId() {
        return accountId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }
}
