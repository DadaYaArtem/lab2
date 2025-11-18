package com.banking.validator;

import com.banking.domain.transaction.BaseTransaction;
import com.banking.enums.TransactionStatus;

public class TransactionValidator {
    private static final double MAX_TRANSACTION_AMOUNT = 100000.0;
    private static final double MIN_TRANSACTION_AMOUNT = 0.01;

    public boolean validateAmount(double amount) {
        return amount >= MIN_TRANSACTION_AMOUNT &&
                amount <= MAX_TRANSACTION_AMOUNT;
    }

    public boolean validateTransaction(BaseTransaction transaction) {
        return transaction != null &&
                transaction.validate() &&
                validateAmount(transaction.getAmount());
    }

    public boolean canRollback(BaseTransaction transaction) {
        return transaction != null &&
                transaction.getStatus() == TransactionStatus.COMPLETED;
    }

    public boolean isTransactionPending(BaseTransaction transaction) {
        return transaction != null &&
                transaction.getStatus() == TransactionStatus.PENDING;
    }

    public boolean isTransactionSuccessful(BaseTransaction transaction) {
        return transaction != null &&
                transaction.getStatus() == TransactionStatus.COMPLETED;
    }
}
