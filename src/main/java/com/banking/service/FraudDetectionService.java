package com.banking.service;

import com.banking.domain.transaction.BaseTransaction;

import java.util.ArrayList;
import java.util.List;

public class FraudDetectionService {
    private List<String> suspiciousTransactions;
    private double suspiciousAmountThreshold;

    public FraudDetectionService() {
        this.suspiciousTransactions = new ArrayList<>();
        this.suspiciousAmountThreshold = 10000.0;
    }

    public boolean detectFraud(BaseTransaction transaction) {
        if (transaction.getAmount() > suspiciousAmountThreshold) {
            markAsSuspicious(transaction);
            return true;
        }
        return false;
    }

    public void markAsSuspicious(BaseTransaction transaction) {
        suspiciousTransactions.add(transaction.getTransactionId());
    }

    public boolean isSuspicious(String transactionId) {
        return suspiciousTransactions.contains(transactionId);
    }

    public void setSuspiciousAmountThreshold(double threshold) {
        this.suspiciousAmountThreshold = threshold;
    }

    public List<String> getSuspiciousTransactions() {
        return new ArrayList<>(suspiciousTransactions);
    }
}
