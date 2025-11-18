package com.banking.service;

import com.banking.domain.Authenticatable;
import com.banking.domain.customer.Customer;
import com.banking.exception.CustomerNotAuthorizedException;
import com.banking.exception.InvalidPasswordException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private Map<String, String> customerCredentials;
    private Map<String, LocalDateTime> loginAttempts;
    private Map<String, Integer> failedAttempts;
    private static final int MAX_ATTEMPTS = 3;

    public AuthenticationService() {
        this.customerCredentials = new HashMap<>();
        this.loginAttempts = new HashMap<>();
        this.failedAttempts = new HashMap<>();
    }

    public void registerCustomer(Customer customer, String password) {
        customerCredentials.put(customer.getCustomerId(), hashPassword(password));
        failedAttempts.put(customer.getCustomerId(), 0);
    }

    public boolean authenticateCustomer(String customerId, String password) throws CustomerNotAuthorizedException, InvalidPasswordException {
        if (!customerCredentials.containsKey(customerId)) {
            throw new CustomerNotAuthorizedException(customerId, "system");
        }

        int attempts = failedAttempts.getOrDefault(customerId, 0);
        if (attempts >= MAX_ATTEMPTS) {
            throw new InvalidPasswordException(0);
        }

        String hashedPassword = hashPassword(password);
        if (customerCredentials.get(customerId).equals(hashedPassword)) {
            loginAttempts.put(customerId, LocalDateTime.now());
            failedAttempts.put(customerId, 0);
            return true;
        } else {
            failedAttempts.put(customerId, attempts + 1);
            throw new InvalidPasswordException(MAX_ATTEMPTS - attempts - 1);
        }
    }

    public boolean authenticateCard(Authenticatable card, String pin) throws InvalidPasswordException {
        return card.authenticate(pin);
    }

    public void changePassword(String customerId, String oldPassword, String newPassword) throws Exception {
        if (!authenticateCustomer(customerId, oldPassword)) {
            throw new InvalidPasswordException(0);
        }
        customerCredentials.put(customerId, hashPassword(newPassword));
    }

    public void resetPassword(String customerId, String newPassword) {
        customerCredentials.put(customerId, hashPassword(newPassword));
        failedAttempts.put(customerId, 0);
    }

    public boolean isCustomerLoggedIn(String customerId) {
        if (!loginAttempts.containsKey(customerId)) {
            return false;
        }
        LocalDateTime lastLogin = loginAttempts.get(customerId);
        return lastLogin.plusHours(24).isAfter(LocalDateTime.now());
    }

    public void logout(String customerId) {
        loginAttempts.remove(customerId);
    }

    private String hashPassword(String password) {
        // Simple hash for demonstration (in real app use BCrypt or similar)
        return Integer.toString(password.hashCode());
    }

    public void unlockAccount(String customerId) {
        failedAttempts.put(customerId, 0);
    }
}
