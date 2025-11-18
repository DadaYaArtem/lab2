package com.banking.util;

import com.banking.enums.Currency;
import com.banking.exception.InvalidCurrencyException;

public class CurrencyConverter {
    public double convert(double amount, Currency from, Currency to) throws InvalidCurrencyException {
        if (from == null || to == null) {
            throw new InvalidCurrencyException(
                    from != null ? from.name() : "NULL",
                    to != null ? to.name() : "NULL"
            );
        }

        if (from == to) {
            return amount;
        }

        // Convert to USD first, then to target currency
        double amountInUSD = amount / from.getExchangeRateToUSD();
        return amountInUSD * to.getExchangeRateToUSD();
    }

    public double getExchangeRate(Currency from, Currency to) {
        return to.getExchangeRateToUSD() / from.getExchangeRateToUSD();
    }

    public String formatAmount(double amount, Currency currency) {
        return String.format("%.2f %s", amount, currency.name());
    }
}
