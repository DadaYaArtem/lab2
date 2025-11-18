package com.banking.exception;

public class InvalidPasswordException extends Exception {
    private int attemptsRemaining;

    public InvalidPasswordException(int attemptsRemaining) {
        super("Invalid password. Attempts remaining: " + attemptsRemaining);
        this.attemptsRemaining = attemptsRemaining;
    }

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }
}
