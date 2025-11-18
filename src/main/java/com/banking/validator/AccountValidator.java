package com.banking.validator;

import com.banking.domain.account.BaseAccount;

public class AccountValidator {
    public boolean validateAccountNumber(String accountNumber) {
        return accountNumber != null &&
                accountNumber.matches("ACC\\d{13,16}") &&
                accountNumber.length() >= 16;
    }

    public boolean validateBalance(double balance) {
        return balance >= 0;
    }

    public boolean validateTransactionAmount(double amount) {
        return amount > 0 && amount <= 1000000;
    }

    public boolean validateAccount(BaseAccount account) {
        return account != null &&
                account.isActive() &&
                validateAccountNumber(account.getAccountNumber()) &&
                validateBalance(account.getBalance());
    }

    public boolean validateMinimumBalance(double balance, double minimum) {
        return balance >= minimum;
    }
}
