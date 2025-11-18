package com.banking.domain.card;

import com.banking.domain.account.BaseAccount;
import com.banking.enums.CardType;
import com.banking.exception.*;

public class DebitCard extends BaseCard {
    private boolean contactlessEnabled;
    private double contactlessLimit;
    private int rewardPoints;

    public DebitCard(BaseAccount linkedAccount, String pin, double dailyLimit) {
        super(CardType.DEBIT, linkedAccount, pin, dailyLimit);
        this.contactlessEnabled = true;
        this.contactlessLimit = 100.0;
        this.rewardPoints = 0;
    }

    @Override
    public void processPayment(double amount) throws Exception {
        validateCard();
        resetDailyUsageIfNeeded();

        if (dailyUsage + amount > dailyLimit) {
            throw new DailyLimitExceededException(dailyLimit, dailyUsage + amount);
        }

        linkedAccount.withdraw(amount);
        dailyUsage += amount;

        // Earn reward points (1 point per dollar)
        rewardPoints += (int) amount;
    }

    @Override
    public boolean canMakePayment(double amount) {
        resetDailyUsageIfNeeded();
        return dailyUsage + amount <= dailyLimit &&
                linkedAccount.getAvailableBalance() >= amount &&
                !isBlocked;
    }

    public void processContactlessPayment(double amount) throws Exception {
        if (!contactlessEnabled) {
            throw new InvalidTransactionException(cardId, "Contactless payments disabled");
        }
        if (amount > contactlessLimit) {
            throw new InvalidTransactionException(cardId, "Amount exceeds contactless limit");
        }
        processPayment(amount);
    }

    public int redeemRewardPoints(int points) throws InvalidTransactionException {
        if (points > rewardPoints) {
            throw new InvalidTransactionException(cardId, "Insufficient reward points");
        }
        rewardPoints -= points;
        return points;
    }

    // Getters and Setters
    public boolean isContactlessEnabled() {
        return contactlessEnabled;
    }

    public void setContactlessEnabled(boolean contactlessEnabled) {
        this.contactlessEnabled = contactlessEnabled;
    }

    public double getContactlessLimit() {
        return contactlessLimit;
    }

    public void setContactlessLimit(double contactlessLimit) {
        this.contactlessLimit = contactlessLimit;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }
}
