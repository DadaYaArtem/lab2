package com.banking.exception;

import java.time.LocalDateTime;

public class CardBlockedException extends Exception {
    private String cardNumber;
    private LocalDateTime blockedAt;

    public CardBlockedException(String cardNumber, LocalDateTime blockedAt) {
        super(String.format("Card %s is blocked since %s", cardNumber, blockedAt));
        this.cardNumber = cardNumber;
        this.blockedAt = blockedAt;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }
}
