package com.banking.domain.card;

import com.banking.domain.Authenticatable;
import com.banking.domain.account.BaseAccount;
import com.banking.enums.CardType;
import com.banking.exception.CardBlockedException;
import com.banking.exception.CardExpiredException;
import com.banking.exception.InvalidPasswordException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseCard implements Authenticatable {
    protected String cardId;
    protected String cardNumber;
    protected String cvv;
    protected LocalDate expirationDate;
    protected String pin;
    protected CardType cardType;
    protected boolean isBlocked;
    protected LocalDateTime blockedAt;
    protected int failedAttempts;
    protected BaseAccount linkedAccount;
    protected double dailyLimit;
    protected double dailyUsage;
    protected LocalDate lastUsageReset;

    public BaseCard(CardType cardType, BaseAccount linkedAccount, String pin, double dailyLimit) {
        this.cardId = UUID.randomUUID().toString();
        this.cardNumber = generateCardNumber();
        this.cvv = generateCVV();
        this.expirationDate = LocalDate.now().plusYears(3);
        this.cardType = cardType;
        this.pin = pin;
        this.linkedAccount = linkedAccount;
        this.isBlocked = false;
        this.failedAttempts = 0;
        this.dailyLimit = dailyLimit;
        this.dailyUsage = 0.0;
        this.lastUsageReset = LocalDate.now();
    }

    protected String generateCardNumber() {
        return String.format("4%015d", (long) (Math.random() * 1000000000000000L));
    }

    protected String generateCVV() {
        return String.format("%03d", (int) (Math.random() * 1000));
    }

    public abstract void processPayment(double amount) throws Exception;

    public abstract boolean canMakePayment(double amount);

    public void validateCard() throws CardBlockedException, CardExpiredException {
        if (isBlocked) {
            throw new CardBlockedException(cardNumber, blockedAt);
        }
        if (expirationDate.isBefore(LocalDate.now())) {
            throw new CardExpiredException(cardNumber, expirationDate);
        }
    }

    public void resetDailyUsageIfNeeded() {
        if (!lastUsageReset.equals(LocalDate.now())) {
            dailyUsage = 0.0;
            lastUsageReset = LocalDate.now();
        }
    }

    @Override
    public boolean authenticate(String password) throws InvalidPasswordException {
        if (isBlocked) {
            throw new InvalidPasswordException(0);
        }

        if (this.pin.equals(password)) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                blockCard();
            }
            throw new InvalidPasswordException(3 - failedAttempts);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws InvalidPasswordException {
        if (!authenticate(oldPassword)) {
            throw new InvalidPasswordException(3 - failedAttempts);
        }
        this.pin = newPassword;
    }

    @Override
    public void resetPassword(String newPassword) {
        this.pin = newPassword;
        this.failedAttempts = 0;
    }

    @Override
    public boolean isLocked() {
        return isBlocked;
    }

    public void blockCard() {
        this.isBlocked = true;
        this.blockedAt = LocalDateTime.now();
    }

    public void unblockCard() {
        this.isBlocked = false;
        this.blockedAt = null;
        this.failedAttempts = 0;
    }

    // Getters
    public String getCardId() {
        return cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public BaseAccount getLinkedAccount() {
        return linkedAccount;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(double dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public double getDailyUsage() {
        return dailyUsage;
    }
}
