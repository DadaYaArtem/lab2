package com.banking.exception;

import java.time.LocalDate;

public class CardExpiredException extends Exception {
    private String cardNumber;
    private LocalDate expirationDate;

    public CardExpiredException(String cardNumber, LocalDate expirationDate) {
        super(String.format("Card %s expired on %s", cardNumber, expirationDate));
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
