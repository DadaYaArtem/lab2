package com.banking.domain.transaction;

import com.banking.domain.Auditable;
import com.banking.enums.TransactionStatus;
import com.banking.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseTransaction implements Auditable {
    protected String transactionId;
    protected TransactionType transactionType;
    protected TransactionStatus status;
    protected double amount;
    protected String description;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime completedAt;
    protected String createdBy;
    protected String lastModifiedBy;

    public BaseTransaction(TransactionType transactionType, double amount, String description) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.status = TransactionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = "SYSTEM";
        this.lastModifiedBy = "SYSTEM";
    }

    public abstract void execute() throws Exception;

    public abstract void rollback() throws Exception;

    public abstract boolean validate();

    public void markAsCompleted() {
        this.status = TransactionStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.status = TransactionStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsCancelled() {
        this.status = TransactionStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    @Override
    public String getAuditId() {
        return transactionId;
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
}
