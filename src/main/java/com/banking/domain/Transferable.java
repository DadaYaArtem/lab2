package com.banking.domain;

import com.banking.exception.*;

public interface Transferable {
    void transfer(Transferable target, double amount) throws InsufficientFundsException,
            InvalidTransactionException, InvalidCurrencyException, DailyLimitExceededException;

    void deposit(double amount) throws InvalidTransactionException;

    void withdraw(double amount) throws InsufficientFundsException, InvalidTransactionException;
}
