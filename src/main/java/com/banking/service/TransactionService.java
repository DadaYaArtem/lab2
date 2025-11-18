package com.banking.service;

import com.banking.domain.account.BaseAccount;
import com.banking.domain.transaction.*;
import com.banking.enums.TransactionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private List<BaseTransaction> transactionHistory;
    private AuditService auditService;

    public TransactionService(AuditService auditService) {
        this.transactionHistory = new ArrayList<>();
        this.auditService = auditService;
    }

    public void executeTransfer(BaseAccount source, BaseAccount target, double amount, String recipientName) throws Exception {
        TransferTransaction transaction = new TransferTransaction(source, target, amount, recipientName);
        executeTransaction(transaction);
    }

    public void executeDeposit(BaseAccount account, double amount, String method) throws Exception {
        DepositTransaction transaction = new DepositTransaction(account, amount, method);
        executeTransaction(transaction);
    }

    public void executeWithdrawal(BaseAccount account, double amount, String method) throws Exception {
        WithdrawalTransaction transaction = new WithdrawalTransaction(account, amount, method);
        executeTransaction(transaction);
    }

    private void executeTransaction(BaseTransaction transaction) throws Exception {
        try {
            transaction.execute();
            transactionHistory.add(transaction);
            auditService.logTransaction(transaction);
        } catch (Exception e) {
            transactionHistory.add(transaction);
            auditService.logFailedTransaction(transaction, e.getMessage());
            throw e;
        }
    }

    public void rollbackTransaction(String transactionId) throws Exception {
        BaseTransaction transaction = findTransactionById(transactionId);
        if (transaction != null) {
            transaction.rollback();
            auditService.logTransactionRollback(transaction);
        }
    }

    public BaseTransaction findTransactionById(String transactionId) {
        return transactionHistory.stream()
                .filter(t -> t.getTransactionId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    public List<BaseTransaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionHistory.stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<BaseTransaction> getAllTransactions() {
        return new ArrayList<>(transactionHistory);
    }

    public double calculateTotalTransactionVolume() {
        return transactionHistory.stream()
                .filter(t -> t.getStatus() == TransactionStatus.COMPLETED)
                .mapToDouble(BaseTransaction::getAmount)
                .sum();
    }
}
