package com.banking.domain.card;

import com.banking.domain.account.BaseAccount;
import com.banking.domain.account.CreditAccount;
import com.banking.enums.CardType;
import com.banking.exception.*;

public class CreditCard extends BaseCard {
    private double cashbackRate;
    private double totalCashback;
    private boolean internationalUsageEnabled;
    private double foreignTransactionFee;

    public CreditCard(CreditAccount linkedAccount, String pin, double dailyLimit) {
        super(CardType.CREDIT, linkedAccount, pin, dailyLimit);
        this.cashbackRate = 1.5; // 1.5%
        this.totalCashback = 0.0;
        this.internationalUsageEnabled = true;
        this.foreignTransactionFee = 3.0; // 3%
    }

    @Override
    public void processPayment(double amount) throws Exception {
        validateCard();
        resetDailyUsageIfNeeded();

        if (dailyUsage + amount > dailyLimit) {
            throw new DailyLimitExceededException(dailyLimit, dailyUsage + amount);
        }

        if (!(linkedAccount instanceof CreditAccount)) {
            throw new InvalidAccountTypeException(linkedAccount.getAccountType().name(), "CREDIT");
        }

        linkedAccount.withdraw(amount);
        dailyUsage += amount;

        // Calculate and track cashback
        double cashback = amount * (cashbackRate / 100.0);
        totalCashback += cashback;
    }

    @Override
    public boolean canMakePayment(double amount) {
        resetDailyUsageIfNeeded();
        return dailyUsage + amount <= dailyLimit &&
                linkedAccount.getAvailableBalance() >= amount &&
                !isBlocked;
    }

    public void processInternationalPayment(double amount, String currency) throws Exception {
        if (!internationalUsageEnabled) {
            throw new InvalidTransactionException(cardId, "International usage disabled");
        }

        double fee = amount * (foreignTransactionFee / 100.0);
        double totalAmount = amount + fee;

        processPayment(totalAmount);
    }

    public double redeemCashback() throws InvalidTransactionException {
        if (totalCashback <= 0) {
            throw new InvalidTransactionException(cardId, "No cashback available");
        }
        double cashback = totalCashback;
        totalCashback = 0.0;

        try {
            linkedAccount.deposit(cashback);
        } catch (Exception e) {
            totalCashback = cashback; // Restore if deposit fails
            throw new InvalidTransactionException(cardId, "Failed to redeem cashback");
        }

        return cashback;
    }

    // Getters and Setters
    public double getCashbackRate() {
        return cashbackRate;
    }

    public void setCashbackRate(double cashbackRate) {
        this.cashbackRate = cashbackRate;
    }

    public double getTotalCashback() {
        return totalCashback;
    }

    public boolean isInternationalUsageEnabled() {
        return internationalUsageEnabled;
    }

    public void setInternationalUsageEnabled(boolean internationalUsageEnabled) {
        this.internationalUsageEnabled = internationalUsageEnabled;
    }
}
