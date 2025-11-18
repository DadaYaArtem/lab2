package com.banking.observer;

import com.banking.domain.account.BaseAccount;

public class AlertObserver implements AccountObserver {
    @Override
    public void onBalanceChanged(BaseAccount account, double oldBalance, double newBalance) {
        System.out.println("ALERT: Balance changed in account " + account.getAccountNumber() +
                " from " + oldBalance + " to " + newBalance);
    }

    @Override
    public void onAccountBlocked(BaseAccount account) {
        System.out.println("ALERT: Account " + account.getAccountNumber() + " has been blocked!");
    }

    @Override
    public void onLowBalance(BaseAccount account, double threshold) {
        System.out.println("ALERT: Low balance in account " + account.getAccountNumber() +
                ": " + account.getBalance() + " (threshold: " + threshold + ")");
    }
}
