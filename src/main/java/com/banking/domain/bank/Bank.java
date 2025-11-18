package com.banking.domain.bank;

import com.banking.domain.customer.Customer;
import com.banking.domain.employee.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private String bankId;
    private String bankName;
    private String swiftCode;
    private String headOfficeAddress;
    private List<Branch> branches;
    private List<Customer> customers;
    private List<Employee> employees;
    private double totalAssets;
    private LocalDateTime establishedDate;

    public Bank(String bankName, String swiftCode, String headOfficeAddress) {
        this.bankId = UUID.randomUUID().toString();
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.headOfficeAddress = headOfficeAddress;
        this.branches = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.totalAssets = 0.0;
        this.establishedDate = LocalDateTime.now();
    }

    public void addBranch(Branch branch) {
        if (!branches.contains(branch)) {
            branches.add(branch);
        }
    }

    public void removeBranch(Branch branch) {
        branches.remove(branch);
    }

    public void registerCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            customers.add(customer);
        }
    }

    public void hireEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    public void updateTotalAssets() {
        this.totalAssets = customers.stream()
                .mapToDouble(Customer::getTotalBalance)
                .sum();
    }

    public Branch findBranchById(String branchId) {
        return branches.stream()
                .filter(b -> b.getBranchId().equals(branchId))
                .findFirst()
                .orElse(null);
    }

    public Customer findCustomerById(String customerId) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    // Getters
    public String getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public String getHeadOfficeAddress() {
        return headOfficeAddress;
    }

    public List<Branch> getBranches() {
        return new ArrayList<>(branches);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public LocalDateTime getEstablishedDate() {
        return establishedDate;
    }
}
