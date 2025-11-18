package com.banking.exception;

public class InsufficientFundsException extends Exception {
    private double requestedAmount;
    private double availableBalance;

    public InsufficientFundsException(double requestedAmount, double availableBalance) {
        super(String.format("Insufficient funds: requested %.2f, available %.2f", requestedAmount, availableBalance));
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }
}
