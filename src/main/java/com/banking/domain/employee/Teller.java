package com.banking.domain.employee;

public class Teller extends Employee {
    private int transactionsProcessed;
    private double cashDrawerBalance;
    private String tillNumber;

    public Teller(String firstName, String lastName, String email, String phoneNumber, double salary) {
        super(firstName, lastName, email, phoneNumber, "Teller", "Customer Service", salary);
        this.transactionsProcessed = 0;
        this.cashDrawerBalance = 5000.0;
        this.tillNumber = "TILL-" + System.currentTimeMillis();
    }

    public void processTransaction() {
        transactionsProcessed++;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void addCashToDrawer(double amount) {
        cashDrawerBalance += amount;
    }

    public void removeCashFromDrawer(double amount) throws Exception {
        if (cashDrawerBalance < amount) {
            throw new Exception("Insufficient cash in drawer");
        }
        cashDrawerBalance -= amount;
    }

    public void balanceDrawer() {
        // Reset to standard amount at end of day
        cashDrawerBalance = 5000.0;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    // Getters
    public int getTransactionsProcessed() {
        return transactionsProcessed;
    }

    public double getCashDrawerBalance() {
        return cashDrawerBalance;
    }

    public String getTillNumber() {
        return tillNumber;
    }
}
