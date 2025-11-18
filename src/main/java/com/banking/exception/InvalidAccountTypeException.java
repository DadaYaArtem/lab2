package com.banking.exception;

public class InvalidAccountTypeException extends Exception {
    private String accountType;
    private String operationType;

    public InvalidAccountTypeException(String accountType, String operationType) {
        super(String.format("Account type %s does not support operation: %s", accountType, operationType));
        this.accountType = accountType;
        this.operationType = operationType;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getOperationType() {
        return operationType;
    }
}
