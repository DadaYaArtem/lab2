package com.banking.domain.account;

import com.banking.domain.Transferable;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.*;

import java.time.LocalDate;

public class CreditAccount extends BaseAccount {
    private double creditLimit;
    private double usedCredit;
    private double interestRate;
    private double minimumPayment;
    private LocalDate paymentDueDate;
    private int gracePeriodDays;
    private double lateFee;

    public CreditAccount(Currency currency, double creditLimit, double interestRate) {
        super(currency, AccountType.CREDIT);
        this.creditLimit = creditLimit;
        this.usedCredit = 0.0;
        this.interestRate = interestRate;
        this.gracePeriodDays = 25;
        this.lateFee = 35.0;
        this.paymentDueDate = LocalDate.now().plusDays(gracePeriodDays);
        calculateMinimumPayment();
    }

    @Override
    public double getAvailableBalance() {
        return creditLimit - usedCredit;
    }

    @Override
    public boolean canWithdraw(double amount) {
        return getAvailableBalance() >= amount;
    }

    @Override
    public void applyInterest() {
        if (usedCredit > 0) {
            double interest = usedCredit * (interestRate / 100.0 / 12.0);
            usedCredit += interest;
            calculateMinimumPayment();
        }
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException {
        if (!isActive) {
            throw new InvalidTransactionException(accountId, "Account is not active");
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException(amount, getAvailableBalance());
        }
        usedCredit += amount;
        calculateMinimumPayment();
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public void deposit(double amount) throws InvalidTransactionException {
        if (amount <= 0) {
            throw new InvalidTransactionException(accountId, "Amount must be positive");
        }
        // Deposit pays off credit
        double payment = Math.min(amount, usedCredit);
        usedCredit -= payment;

        // Any excess goes to balance (positive balance in credit account)
        if (amount > payment) {
            balance += (amount - payment);
        }
        calculateMinimumPayment();
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

    private void calculateMinimumPayment() {
        this.minimumPayment = Math.max(25.0, usedCredit * 0.02);
    }

    public void makePayment(double amount) throws InvalidTransactionException {
        if (amount < minimumPayment && amount < usedCredit) {
            throw new InvalidTransactionException(accountId,
                    String.format("Payment must be at least %.2f", minimumPayment));
        }
        deposit(amount);
        paymentDueDate = LocalDate.now().plusDays(gracePeriodDays);
    }

    public void applyLateFee() {
        if (LocalDate.now().isAfter(paymentDueDate) && usedCredit > 0) {
            usedCredit += lateFee;
            calculateMinimumPayment();
        }
    }

    public boolean isPaymentOverdue() {
        return LocalDate.now().isAfter(paymentDueDate) && usedCredit > 0;
    }

    // Getters
    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getUsedCredit() {
        return usedCredit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumPayment() {
        return minimumPayment;
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }
}
