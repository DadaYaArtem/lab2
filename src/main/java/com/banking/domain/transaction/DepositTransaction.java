package com.banking.domain.transaction;

import com.banking.domain.account.BaseAccount;
import com.banking.enums.TransactionType;
import com.banking.exception.InvalidTransactionException;

public class DepositTransaction extends BaseTransaction {
    private BaseAccount targetAccount;
    private String depositMethod;
    private String referenceNumber;

    public DepositTransaction(BaseAccount targetAccount, double amount, String depositMethod) {
        super(TransactionType.DEPOSIT, amount, "Deposit via " + depositMethod);
        this.targetAccount = targetAccount;
        this.depositMethod = depositMethod;
        this.referenceNumber = "DEP-" + System.currentTimeMillis();
    }

    @Override
    public void execute() throws Exception {
        if (!validate()) {
            markAsFailed();
            throw new InvalidTransactionException(transactionId, "Transaction validation failed");
        }

        status = com.banking.enums.TransactionStatus.PROCESSING;

        try {
            targetAccount.deposit(amount);
            markAsCompleted();
        } catch (Exception e) {
            markAsFailed();
            throw e;
        }
    }

    @Override
    public void rollback() throws Exception {
        if (status != com.banking.enums.TransactionStatus.COMPLETED) {
            throw new InvalidTransactionException(transactionId, "Cannot rollback incomplete transaction");
        }

        targetAccount.withdraw(amount);
        status = com.banking.enums.TransactionStatus.REFUNDED;
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public boolean validate() {
        return targetAccount != null &&
                amount > 0 &&
                targetAccount.isActive() &&
                depositMethod != null;
    }

    // Getters
    public BaseAccount getTargetAccount() {
        return targetAccount;
    }

    public String getDepositMethod() {
        return depositMethod;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }
}
