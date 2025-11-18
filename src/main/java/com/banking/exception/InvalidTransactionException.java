package com.banking.exception;

public class InvalidTransactionException extends Exception {
    private String transactionId;
    private String reason;

    public InvalidTransactionException(String transactionId, String reason) {
        super(String.format("Invalid transaction %s: %s", transactionId, reason));
        this.transactionId = transactionId;
        this.reason = reason;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getReason() {
        return reason;
    }
}
