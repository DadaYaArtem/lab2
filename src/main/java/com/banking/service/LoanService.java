package com.banking.service;

import com.banking.domain.customer.Customer;
import com.banking.domain.employee.BankManager;
import com.banking.domain.loan.Loan;
import com.banking.enums.LoanStatus;
import com.banking.exception.LoanNotApprovedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanService {
    private List<Loan> loans;
    private AuditService auditService;

    public LoanService(AuditService auditService) {
        this.loans = new ArrayList<>();
        this.auditService = auditService;
    }

    public Loan createLoanApplication(Customer customer, double amount, double interestRate,
                                      int termInMonths, String purpose) {
        Loan loan = new Loan(customer, amount, interestRate, termInMonths, purpose);
        loans.add(loan);
        auditService.logCustomEvent("LOAN_APPLICATION", loan.getLoanId(),
                "Loan application created", customer.getCustomerId());
        return loan;
    }

    public void approveLoan(Loan loan, BankManager manager) throws LoanNotApprovedException {
        if (!manager.canApproveLoan(loan)) {
            throw new LoanNotApprovedException(loan.getLoanId(),
                    "Exceeds manager approval limit");
        }
        loan.approve();
        loan.disburse();
        auditService.logCustomEvent("LOAN_APPROVED", loan.getLoanId(),
                "Loan approved and disbursed", manager.getEmployeeId());
    }

    public void processLoanPayment(Loan loan, double amount) throws Exception {
        loan.makePayment(amount);
        auditService.logCustomEvent("LOAN_PAYMENT", loan.getLoanId(),
                String.format("Payment of %.2f made", amount), loan.getBorrower().getCustomerId());
    }

    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loans.stream()
                .filter(l -> l.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansByCustomer(Customer customer) {
        return loans.stream()
                .filter(l -> l.getBorrower().equals(customer))
                .collect(Collectors.toList());
    }

    public double calculateTotalOutstandingDebt() {
        return loans.stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .mapToDouble(Loan::getRemainingBalance)
                .sum();
    }

    public void checkOverdueLoans() {
        loans.stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .forEach(loan -> {
                    // Logic to check if payment is overdue
                    // Mark as defaulted if necessary
                });
    }
}
