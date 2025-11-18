package com.banking.exception;

public class InvalidCurrencyException extends Exception {
    private String sourceCurrency;
    private String targetCurrency;

    public InvalidCurrencyException(String sourceCurrency, String targetCurrency) {
        super(String.format("Currency mismatch: source %s, target %s", sourceCurrency, targetCurrency));
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
