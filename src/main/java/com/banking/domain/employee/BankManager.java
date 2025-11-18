package com.banking.domain.employee;

import com.banking.domain.loan.Loan;

import java.util.ArrayList;
import java.util.List;

public class BankManager extends Employee {
    private List<String> managedEmployees;
    private double approvalLimit;
    private int teamSize;

    public BankManager(String firstName, String lastName, String email, String phoneNumber,
                       String department, double salary, double approvalLimit) {
        super(firstName, lastName, email, phoneNumber, "Bank Manager", department, salary);
        this.managedEmployees = new ArrayList<>();
        this.approvalLimit = approvalLimit;
        this.teamSize = 0;
    }

    public boolean canApproveLoan(Loan loan) {
        return loan.getLoanAmount() <= approvalLimit;
    }

    public void addManagedEmployee(String employeeId) {
        if (!managedEmployees.contains(employeeId)) {
            managedEmployees.add(employeeId);
            teamSize++;
        }
    }

    public void removeManagedEmployee(String employeeId) {
        if (managedEmployees.remove(employeeId)) {
            teamSize--;
        }
    }

    public void increaseApprovalLimit(double amount) {
        this.approvalLimit += amount;
    }

    // Getters
    public List<String> getManagedEmployees() {
        return new ArrayList<>(managedEmployees);
    }

    public double getApprovalLimit() {
        return approvalLimit;
    }

    public int getTeamSize() {
        return teamSize;
    }
}
