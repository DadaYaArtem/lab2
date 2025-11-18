package com.banking.factory;

import com.banking.domain.account.*;
import com.banking.domain.customer.Customer;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;

public class AccountFactory {
    public static BaseAccount createAccount(AccountType type, Customer customer, Currency currency, Object... params) {
        return switch (type) {
            case CHECKING -> {
                double overdraftLimit = params.length > 0 ? (double) params[0] : 500.0;
                yield new CheckingAccount(currency, overdraftLimit);
            }
            case SAVINGS -> {
                double interestRate = params.length > 0 ? (double) params[0] : 2.5;
                double minimumBalance = params.length > 1 ? (double) params[1] : 100.0;
                yield new SavingsAccount(currency, interestRate, minimumBalance);
            }
            case INVESTMENT -> {
                String riskProfile = params.length > 0 ? (String) params[0] : "MODERATE";
                yield new InvestmentAccount(currency, riskProfile);
            }
            case BUSINESS -> {
                String businessName = params.length > 0 ? (String) params[0] : "Business";
                String taxId = params.length > 1 ? (String) params[1] : "TAX123";
                double creditLine = params.length > 2 ? (double) params[2] : 10000.0;
                yield new BusinessAccount(currency, businessName, taxId, creditLine);
            }
            case CREDIT -> {
                double creditLimit = params.length > 0 ? (double) params[0] : 5000.0;
                double interestRate = params.length > 1 ? (double) params[1] : 18.0;
                yield new CreditAccount(currency, creditLimit, interestRate);
            }
        };
    }

    public static CheckingAccount createDefaultCheckingAccount(Currency currency) {
        return new CheckingAccount(currency, 500.0);
    }

    public static SavingsAccount createDefaultSavingsAccount(Currency currency) {
        return new SavingsAccount(currency, 2.5, 100.0);
    }

    public static CreditAccount createDefaultCreditAccount(Currency currency) {
        return new CreditAccount(currency, 5000.0, 18.0);
    }
}
