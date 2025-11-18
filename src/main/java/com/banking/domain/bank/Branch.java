package com.banking.domain.bank;

import com.banking.domain.employee.BankManager;
import com.banking.domain.employee.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Branch {
    private String branchId;
    private String branchCode;
    private String branchName;
    private String address;
    private String phoneNumber;
    private BankManager manager;
    private List<Employee> staff;
    private boolean isOpen;
    private String operatingHours;

    public Branch(String branchCode, String branchName, String address, String phoneNumber) {
        this.branchId = UUID.randomUUID().toString();
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.staff = new ArrayList<>();
        this.isOpen = true;
        this.operatingHours = "9:00 AM - 5:00 PM";
    }

    public void assignManager(BankManager manager) {
        this.manager = manager;
        manager.assignToBranch(this.branchId);
    }

    public void addStaff(Employee employee) {
        if (!staff.contains(employee)) {
            staff.add(employee);
            employee.assignToBranch(this.branchId);
        }
    }

    public void removeStaff(Employee employee) {
        staff.remove(employee);
    }

    public void openBranch() {
        this.isOpen = true;
    }

    public void closeBranch() {
        this.isOpen = false;
    }

    public void updateOperatingHours(String hours) {
        this.operatingHours = hours;
    }

    public int getStaffCount() {
        return staff.size();
    }

    // Getters
    public String getBranchId() {
        return branchId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BankManager getManager() {
        return manager;
    }

    public List<Employee> getStaff() {
        return new ArrayList<>(staff);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String getOperatingHours() {
        return operatingHours;
    }
}
