package com.banking.enums;

public enum Currency {
    USD(1.0),
    EUR(0.92),
    GBP(0.79),
    RUB(92.5),
    JPY(149.8);

    private final double exchangeRateToUSD;

    Currency(double exchangeRateToUSD) {
        this.exchangeRateToUSD = exchangeRateToUSD;
    }

    public double getExchangeRateToUSD() {
        return exchangeRateToUSD;
    }
}
