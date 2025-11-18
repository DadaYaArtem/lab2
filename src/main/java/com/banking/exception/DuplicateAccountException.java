package com.banking.exception;

public class DuplicateAccountException extends Exception {
    private String accountId;

    public DuplicateAccountException(String accountId) {
        super("Account already exists: " + accountId);
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
