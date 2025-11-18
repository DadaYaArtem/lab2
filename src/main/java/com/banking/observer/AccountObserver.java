package com.banking.observer;

import com.banking.domain.account.BaseAccount;

public interface AccountObserver {
    void onBalanceChanged(BaseAccount account, double oldBalance, double newBalance);
    void onAccountBlocked(BaseAccount account);
    void onLowBalance(BaseAccount account, double threshold);
}
