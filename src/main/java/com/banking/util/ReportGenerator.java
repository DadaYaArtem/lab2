package com.banking.util;

import com.banking.domain.Reportable;
import com.banking.domain.account.BaseAccount;
import com.banking.domain.customer.Customer;
import com.banking.domain.transaction.BaseTransaction;

import java.time.LocalDateTime;
import java.util.List;

public class ReportGenerator {
    public String generateAccountStatement(BaseAccount account, LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder report = new StringBuilder();
        report.append("ACCOUNT STATEMENT\n");
        report.append("==================\n");
        report.append("Account Number: ").append(account.getAccountNumber()).append("\n");
        report.append("Account Type: ").append(account.getAccountType()).append("\n");
        report.append("Current Balance: ").append(account.getBalance()).append(" ").append(account.getCurrency()).append("\n");
        report.append("Period: ").append(startDate).append(" to ").append(endDate).append("\n");
        return report.toString();
    }

    public String generateCustomerSummary(Customer customer) {
        StringBuilder report = new StringBuilder();
        report.append("CUSTOMER SUMMARY\n");
        report.append("=================\n");
        report.append("Customer: ").append(customer.getFullName()).append("\n");
        report.append("Customer ID: ").append(customer.getCustomerId()).append("\n");
        report.append("Email: ").append(customer.getEmail()).append("\n");
        report.append("Phone: ").append(customer.getPhoneNumber()).append("\n");
        report.append("Tier: ").append(customer.getCustomerTier()).append("\n");
        report.append("Total Accounts: ").append(customer.getAccounts().size()).append("\n");
        report.append("Total Balance: ").append(customer.getTotalBalance()).append("\n");
        return report.toString();
    }

    public String generateTransactionReport(List<BaseTransaction> transactions) {
        StringBuilder report = new StringBuilder();
        report.append("TRANSACTION REPORT\n");
        report.append("===================\n");
        report.append("Total Transactions: ").append(transactions.size()).append("\n");

        double totalAmount = transactions.stream()
                .mapToDouble(BaseTransaction::getAmount)
                .sum();

        report.append("Total Amount: ").append(totalAmount).append("\n");
        return report.toString();
    }
}
