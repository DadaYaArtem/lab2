package com.banking.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {
    private static final SecureRandom random = new SecureRandom();

    public String generateSecureToken() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String generateOTP() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public boolean validateOTP(String otp, String expected) {
        return otp != null && otp.equals(expected);
    }

    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return cardNumber;
        }
        int length = cardNumber.length();
        return "**** **** **** " + cardNumber.substring(length - 4);
    }

    public String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String username = parts[0];
        String masked = username.charAt(0) + "***" + username.charAt(username.length() - 1);
        return masked + "@" + parts[1];
    }
}
