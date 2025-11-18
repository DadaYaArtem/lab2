package com.banking.domain.employee;

import com.banking.domain.customer.BasePerson;

import java.time.LocalDate;

public class Employee extends BasePerson {
    private String employeeId;
    private String position;
    private String department;
    private double salary;
    private LocalDate hireDate;
    private String branchId;
    private boolean isActive;

    public Employee(String firstName, String lastName, String email, String phoneNumber,
                    String position, String department, double salary) {
        super(firstName, lastName, email, phoneNumber);
        this.employeeId = this.id;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.isActive = true;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Employee: %s (ID: %s, Position: %s)", getFullName(), employeeId, position);
    }

    public void assignToBranch(String branchId) {
        this.branchId = branchId;
    }

    public void promote(String newPosition, double newSalary) {
        this.position = newPosition;
        this.salary = newSalary;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void adjustSalary(double amount) {
        this.salary += amount;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void terminate() {
        this.isActive = false;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    // Getters and Setters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public String getBranchId() {
        return branchId;
    }

    public boolean isActive() {
        return isActive;
    }
}
