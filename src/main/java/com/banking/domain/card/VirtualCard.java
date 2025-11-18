package com.banking.domain.card;

import com.banking.domain.account.BaseAccount;
import com.banking.enums.CardType;
import com.banking.exception.*;

import java.time.LocalDateTime;

public class VirtualCard extends BaseCard {
    private boolean isSingleUse;
    private boolean hasBeenUsed;
    private String merchantRestriction;
    private LocalDateTime validUntil;

    public VirtualCard(BaseAccount linkedAccount, String pin, double dailyLimit, boolean isSingleUse) {
        super(CardType.VIRTUAL, linkedAccount, pin, dailyLimit);
        this.isSingleUse = isSingleUse;
        this.hasBeenUsed = false;
        this.validUntil = LocalDateTime.now().plusDays(7);
    }

    @Override
    public void processPayment(double amount) throws Exception {
        validateCard();

        if (LocalDateTime.now().isAfter(validUntil)) {
            throw new CardExpiredException(cardNumber, expirationDate);
        }

        if (isSingleUse && hasBeenUsed) {
            throw new InvalidTransactionException(cardId, "Single-use card already used");
        }

        resetDailyUsageIfNeeded();

        if (dailyUsage + amount > dailyLimit) {
            throw new DailyLimitExceededException(dailyLimit, dailyUsage + amount);
        }

        linkedAccount.withdraw(amount);
        dailyUsage += amount;

        if (isSingleUse) {
            hasBeenUsed = true;
            blockCard(); // Automatically block after single use
        }
    }

    @Override
    public boolean canMakePayment(double amount) {
        if (isSingleUse && hasBeenUsed) {
            return false;
        }
        if (LocalDateTime.now().isAfter(validUntil)) {
            return false;
        }
        resetDailyUsageIfNeeded();
        return dailyUsage + amount <= dailyLimit &&
                linkedAccount.getAvailableBalance() >= amount &&
                !isBlocked;
    }

    public void extendValidity(int days) throws InvalidTransactionException {
        if (isSingleUse && hasBeenUsed) {
            throw new InvalidTransactionException(cardId, "Cannot extend expired single-use card");
        }
        validUntil = validUntil.plusDays(days);
    }

    public void setMerchantRestriction(String merchantName) {
        this.merchantRestriction = merchantName;
    }

    public void processPaymentAtMerchant(double amount, String merchantName) throws Exception {
        if (merchantRestriction != null && !merchantRestriction.equals(merchantName)) {
            throw new InvalidTransactionException(cardId,
                    "Card restricted to merchant: " + merchantRestriction);
        }
        processPayment(amount);
    }

    public void regenerateCardNumber() {
        if (!hasBeenUsed) {
            this.cardNumber = generateCardNumber();
            this.cvv = generateCVV();
        }
    }

    // Getters
    public boolean isSingleUse() {
        return isSingleUse;
    }

    public boolean hasBeenUsed() {
        return hasBeenUsed;
    }

    public String getMerchantRestriction() {
        return merchantRestriction;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }
}
