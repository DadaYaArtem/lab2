package com.banking.domain.loan;

import com.banking.domain.Auditable;
import com.banking.domain.customer.Customer;
import com.banking.enums.LoanStatus;
import com.banking.exception.InvalidTransactionException;
import com.banking.exception.LoanNotApprovedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Loan implements Auditable {
    private String loanId;
    private Customer borrower;
    private double loanAmount;
    private double interestRate;
    private int termInMonths;
    private double remainingBalance;
    private LoanStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private double monthlyPayment;
    private List<LoanPayment> payments;
    private String purpose;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;

    public Loan(Customer borrower, double loanAmount, double interestRate, int termInMonths, String purpose) {
        this.loanId = UUID.randomUUID().toString();
        this.borrower = borrower;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.termInMonths = termInMonths;
        this.remainingBalance = loanAmount;
        this.status = LoanStatus.PENDING_APPROVAL;
        this.purpose = purpose;
        this.payments = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.lastModifiedBy = "SYSTEM";
        calculateMonthlyPayment();
    }

    private void calculateMonthlyPayment() {
        double monthlyRate = interestRate / 100.0 / 12.0;
        this.monthlyPayment = loanAmount * (monthlyRate * Math.pow(1 + monthlyRate, termInMonths)) /
                (Math.pow(1 + monthlyRate, termInMonths) - 1);
    }

    public void approve() throws LoanNotApprovedException {
        if (borrower.getCreditScore() < 600) {
            status = LoanStatus.REJECTED;
            throw new LoanNotApprovedException(loanId, "Insufficient credit score");
        }
        this.status = LoanStatus.APPROVED;
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusMonths(termInMonths);
        this.updatedAt = LocalDateTime.now();
    }

    public void disburse() throws LoanNotApprovedException {
        if (status != LoanStatus.APPROVED) {
            throw new LoanNotApprovedException(loanId, "Loan must be approved before disbursement");
        }
        this.status = LoanStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void makePayment(double amount) throws InvalidTransactionException {
        if (status != LoanStatus.ACTIVE) {
            throw new InvalidTransactionException(loanId, "Loan is not active");
        }
        if (amount <= 0) {
            throw new InvalidTransactionException(loanId, "Payment amount must be positive");
        }

        LoanPayment payment = new LoanPayment(amount, LocalDate.now());
        payments.add(payment);
        remainingBalance -= amount;

        if (remainingBalance <= 0) {
            remainingBalance = 0;
            status = LoanStatus.PAID_OFF;
        }

        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDefault() {
        this.status = LoanStatus.DEFAULTED;
        this.updatedAt = LocalDateTime.now();
    }

    public double calculateTotalInterest() {
        return (monthlyPayment * termInMonths) - loanAmount;
    }

    public int getRemainingPayments() {
        return (int) Math.ceil(remainingBalance / monthlyPayment);
    }

    // Getters
    public String getLoanId() {
        return loanId;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTermInMonths() {
        return termInMonths;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public List<LoanPayment> getPayments() {
        return new ArrayList<>(payments);
    }

    public String getPurpose() {
        return purpose;
    }

    @Override
    public String getAuditId() {
        return loanId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    // Inner class for loan payments
    public static class LoanPayment {
        private double amount;
        private LocalDate paymentDate;

        public LoanPayment(double amount, LocalDate paymentDate) {
            this.amount = amount;
            this.paymentDate = paymentDate;
        }

        public double getAmount() {
            return amount;
        }

        public LocalDate getPaymentDate() {
            return paymentDate;
        }
    }
}
