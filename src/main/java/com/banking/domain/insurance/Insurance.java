package com.banking.domain.insurance;

import com.banking.domain.customer.Customer;

import java.time.LocalDate;
import java.util.UUID;

public class Insurance {
    private String insuranceId;
    private String policyNumber;
    private Customer policyholder;
    private String insuranceType;
    private double coverageAmount;
    private double premiumAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    public Insurance(Customer policyholder, String insuranceType, double coverageAmount, double premiumAmount, int termInYears) {
        this.insuranceId = UUID.randomUUID().toString();
        this.policyNumber = "POL-" + System.currentTimeMillis();
        this.policyholder = policyholder;
        this.insuranceType = insuranceType;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusYears(termInYears);
        this.isActive = true;
    }

    public void renewPolicy(int additionalYears) {
        this.endDate = endDate.plusYears(additionalYears);
    }

    public void cancelPolicy() {
        this.isActive = false;
    }

    public boolean isPolicyExpired() {
        return LocalDate.now().isAfter(endDate);
    }

    public double calculateAnnualPremium() {
        return premiumAmount * 12;
    }

    public void increaseCoverage(double additionalAmount, double additionalPremium) {
        this.coverageAmount += additionalAmount;
        this.premiumAmount += additionalPremium;
    }

    // Getters
    public String getInsuranceId() {
        return insuranceId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public Customer getPolicyholder() {
        return policyholder;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return isActive;
    }
}
