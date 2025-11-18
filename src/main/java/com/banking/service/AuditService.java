package com.banking.service;

import com.banking.domain.Auditable;
import com.banking.domain.transaction.BaseTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuditService {
    private List<AuditLog> auditLogs;

    public AuditService() {
        this.auditLogs = new ArrayList<>();
    }

    public void logTransaction(BaseTransaction transaction) {
        AuditLog log = new AuditLog(
                "TRANSACTION",
                transaction.getTransactionId(),
                "Transaction completed: " + transaction.getDescription(),
                "SYSTEM"
        );
        auditLogs.add(log);
    }

    public void logFailedTransaction(BaseTransaction transaction, String reason) {
        AuditLog log = new AuditLog(
                "TRANSACTION_FAILED",
                transaction.getTransactionId(),
                "Transaction failed: " + reason,
                "SYSTEM"
        );
        auditLogs.add(log);
    }

    public void logTransactionRollback(BaseTransaction transaction) {
        AuditLog log = new AuditLog(
                "TRANSACTION_ROLLBACK",
                transaction.getTransactionId(),
                "Transaction rolled back",
                "SYSTEM"
        );
        auditLogs.add(log);
    }

    public void logAccountCreation(Auditable account) {
        AuditLog log = new AuditLog(
                "ACCOUNT_CREATED",
                account.getAuditId(),
                "Account created",
                account.getCreatedBy()
        );
        auditLogs.add(log);
    }

    public void logAccountModification(Auditable account, String modification) {
        AuditLog log = new AuditLog(
                "ACCOUNT_MODIFIED",
                account.getAuditId(),
                modification,
                account.getLastModifiedBy()
        );
        auditLogs.add(log);
    }

    public void logCustomEvent(String eventType, String entityId, String description, String performedBy) {
        AuditLog log = new AuditLog(eventType, entityId, description, performedBy);
        auditLogs.add(log);
    }

    public List<AuditLog> getAuditLogsByEntityId(String entityId) {
        return auditLogs.stream()
                .filter(log -> log.getEntityId().equals(entityId))
                .toList();
    }

    public List<AuditLog> getAllAuditLogs() {
        return new ArrayList<>(auditLogs);
    }

    public static class AuditLog {
        private String eventType;
        private String entityId;
        private String description;
        private String performedBy;
        private LocalDateTime timestamp;

        public AuditLog(String eventType, String entityId, String description, String performedBy) {
            this.eventType = eventType;
            this.entityId = entityId;
            this.description = description;
            this.performedBy = performedBy;
            this.timestamp = LocalDateTime.now();
        }

        public String getEventType() {
            return eventType;
        }

        public String getEntityId() {
            return entityId;
        }

        public String getDescription() {
            return description;
        }

        public String getPerformedBy() {
            return performedBy;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s - %s (by %s) - %s",
                    timestamp, eventType, entityId, performedBy, description);
        }
    }
}
