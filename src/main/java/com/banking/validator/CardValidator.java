package com.banking.validator;

import com.banking.domain.card.BaseCard;

import java.time.LocalDate;

public class CardValidator {
    public boolean validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return false;
        }
        return luhnCheck(cardNumber);
    }

    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    public boolean validateCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    public boolean validateExpirationDate(LocalDate expirationDate) {
        return expirationDate != null &&
                expirationDate.isAfter(LocalDate.now());
    }

    public boolean validatePin(String pin) {
        return pin != null && pin.matches("\\d{4,6}");
    }

    public boolean validateCard(BaseCard card) {
        return card != null &&
                !card.isBlocked() &&
                validateCardNumber(card.getCardNumber()) &&
                validateCVV(card.getCvv()) &&
                validateExpirationDate(card.getExpirationDate());
    }
}
