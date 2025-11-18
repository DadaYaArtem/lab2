package com.banking.domain.bank;

import com.banking.domain.card.BaseCard;

import java.util.UUID;

public class ATM {
    private String atmId;
    private String location;
    private double cashAvailable;
    private boolean isOperational;
    private String branchId;

    public ATM(String location, double cashAvailable) {
        this.atmId = UUID.randomUUID().toString();
        this.location = location;
        this.cashAvailable = cashAvailable;
        this.isOperational = true;
    }

    public void withdrawCash(BaseCard card, double amount) throws Exception {
        if (!isOperational) {
            throw new Exception("ATM is not operational");
        }
        if (cashAvailable < amount) {
            throw new Exception("Insufficient cash in ATM");
        }

        card.processPayment(amount);
        cashAvailable -= amount;
    }

    public void depositCash(double amount) {
        cashAvailable += amount;
    }

    public void refillCash(double amount) {
        cashAvailable += amount;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }

    // Getters
    public String getAtmId() {
        return atmId;
    }

    public String getLocation() {
        return location;
    }

    public double getCashAvailable() {
        return cashAvailable;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
