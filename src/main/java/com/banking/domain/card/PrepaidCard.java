package com.banking.domain.card;

import com.banking.domain.account.BaseAccount;
import com.banking.enums.CardType;
import com.banking.exception.*;

import java.time.LocalDateTime;

public class PrepaidCard extends BaseCard {
    private double loadedBalance;
    private double reloadFee;
    private LocalDateTime lastReloadDate;
    private boolean isReloadable;

    public PrepaidCard(BaseAccount linkedAccount, String pin, double dailyLimit, boolean isReloadable) {
        super(CardType.PREPAID, linkedAccount, pin, dailyLimit);
        this.loadedBalance = 0.0;
        this.reloadFee = 2.5;
        this.isReloadable = isReloadable;
    }

    @Override
    public void processPayment(double amount) throws Exception {
        validateCard();
        resetDailyUsageIfNeeded();

        if (dailyUsage + amount > dailyLimit) {
            throw new DailyLimitExceededException(dailyLimit, dailyUsage + amount);
        }

        if (loadedBalance < amount) {
            throw new InsufficientFundsException(amount, loadedBalance);
        }

        loadedBalance -= amount;
        dailyUsage += amount;
    }

    @Override
    public boolean canMakePayment(double amount) {
        resetDailyUsageIfNeeded();
        return dailyUsage + amount <= dailyLimit &&
                loadedBalance >= amount &&
                !isBlocked;
    }

    public void reloadCard(double amount) throws Exception {
        if (!isReloadable) {
            throw new InvalidTransactionException(cardId, "Card is not reloadable");
        }

        validateCard();

        double totalAmount = amount + reloadFee;
        linkedAccount.withdraw(totalAmount);

        loadedBalance += amount;
        lastReloadDate = LocalDateTime.now();
    }

    public double checkBalance() {
        return loadedBalance;
    }

    public void transferToAccount() throws InvalidTransactionException {
        if (loadedBalance <= 0) {
            throw new InvalidTransactionException(cardId, "No balance to transfer");
        }

        try {
            linkedAccount.deposit(loadedBalance);
            loadedBalance = 0.0;
        } catch (Exception e) {
            throw new InvalidTransactionException(cardId, "Failed to transfer to account");
        }
    }

    // Getters
    public double getLoadedBalance() {
        return loadedBalance;
    }

    public boolean isReloadable() {
        return isReloadable;
    }

    public LocalDateTime getLastReloadDate() {
        return lastReloadDate;
    }
}
