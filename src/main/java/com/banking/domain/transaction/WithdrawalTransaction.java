package com.banking.domain.transaction;

import com.banking.domain.account.BaseAccount;
import com.banking.enums.TransactionType;
import com.banking.exception.InvalidTransactionException;

public class WithdrawalTransaction extends BaseTransaction {
    private BaseAccount sourceAccount;
    private String withdrawalMethod;
    private String atmId;

    public WithdrawalTransaction(BaseAccount sourceAccount, double amount, String withdrawalMethod) {
        super(TransactionType.WITHDRAWAL, amount, "Withdrawal via " + withdrawalMethod);
        this.sourceAccount = sourceAccount;
        this.withdrawalMethod = withdrawalMethod;
    }

    @Override
    public void execute() throws Exception {
        if (!validate()) {
            markAsFailed();
            throw new InvalidTransactionException(transactionId, "Transaction validation failed");
        }

        status = com.banking.enums.TransactionStatus.PROCESSING;

        try {
            sourceAccount.withdraw(amount);
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

        sourceAccount.deposit(amount);
        status = com.banking.enums.TransactionStatus.REFUNDED;
        updatedAt = java.time.LocalDateTime.now();
    }

    @Override
    public boolean validate() {
        return sourceAccount != null &&
                amount > 0 &&
                sourceAccount.isActive() &&
                withdrawalMethod != null;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    // Getters
    public BaseAccount getSourceAccount() {
        return sourceAccount;
    }

    public String getWithdrawalMethod() {
        return withdrawalMethod;
    }

    public String getAtmId() {
        return atmId;
    }
}
