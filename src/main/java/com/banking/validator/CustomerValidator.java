package com.banking.validator;

import com.banking.domain.customer.Customer;

import java.time.LocalDate;
import java.time.Period;

public class CustomerValidator {
    private static final int MINIMUM_AGE = 18;
    private static final int MAXIMUM_AGE = 100;

    public boolean validateEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null &&
                phoneNumber.matches("^\\+?\\d{10,15}$");
    }

    public boolean validateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return false;
        }
        int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
        return age >= MINIMUM_AGE && age <= MAXIMUM_AGE;
    }

    public boolean validateCustomer(Customer customer) {
        return customer != null &&
                validateEmail(customer.getEmail()) &&
                validatePhoneNumber(customer.getPhoneNumber()) &&
                validateAge(customer.getDateOfBirth());
    }

    public boolean validateCreditScore(double creditScore) {
        return creditScore >= 300 && creditScore <= 850;
    }

    public boolean validateIdentificationNumber(String idNumber) {
        return idNumber != null &&
                idNumber.matches("^[A-Z0-9]{8,15}$");
    }
}
