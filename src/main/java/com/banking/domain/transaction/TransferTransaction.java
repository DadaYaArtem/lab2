package com.banking.domain.transaction;

import com.banking.domain.account.BaseAccount;
import com.banking.enums.TransactionType;
import com.banking.exception.*;

public class TransferTransaction extends BaseTransaction {
    private BaseAccount sourceAccount;
    private BaseAccount targetAccount;
    private String recipientName;

    public TransferTransaction(BaseAccount sourceAccount, BaseAccount targetAccount,
                               double amount, String recipientName) {
        super(TransactionType.TRANSFER, amount, "Transfer to " + recipientName);
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.recipientName = recipientName;
    }

    @Override
    public void execute() throws Exception {
        if (!validate()) {
            markAsFailed();
            throw new InvalidTransactionException(transactionId, "Transaction validation failed");
        }

        status = com.banking.enums.TransactionStatus.PROCESSING;

        try {
            sourceAccount.transfer(targetAccount, amount);
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

        targetAccount.transfer(sourceAccount, amount);
        status = com.banking.enums.TransactionStatus.REFUNDED;
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public boolean validate() {
        return sourceAccount != null &&
                targetAccount != null &&
                amount > 0 &&
                sourceAccount.isActive() &&
                targetAccount.isActive() &&
                !sourceAccount.equals(targetAccount);
    }

    // Getters
    public BaseAccount getSourceAccount() {
        return sourceAccount;
    }

    public BaseAccount getTargetAccount() {
        return targetAccount;
    }

    public String getRecipientName() {
        return recipientName;
    }
}
