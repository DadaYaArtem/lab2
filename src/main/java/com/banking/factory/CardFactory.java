package com.banking.factory;

import com.banking.domain.account.BaseAccount;
import com.banking.domain.account.CreditAccount;
import com.banking.domain.card.*;
import com.banking.enums.CardType;

public class CardFactory {
    private static final String DEFAULT_PIN = "1234";
    private static final double DEFAULT_DAILY_LIMIT = 1000.0;

    public static BaseCard createCard(CardType type, BaseAccount linkedAccount, String pin, double dailyLimit) {
        return switch (type) {
            case DEBIT -> new DebitCard(linkedAccount, pin, dailyLimit);
            case CREDIT -> {
                if (linkedAccount instanceof CreditAccount) {
                    yield new CreditCard((CreditAccount) linkedAccount, pin, dailyLimit);
                }
                throw new IllegalArgumentException("Credit card requires CreditAccount");
            }
            case PREPAID -> new PrepaidCard(linkedAccount, pin, dailyLimit, true);
            case VIRTUAL -> new VirtualCard(linkedAccount, pin, dailyLimit, false);
        };
    }

    public static DebitCard createDebitCard(BaseAccount account) {
        return new DebitCard(account, DEFAULT_PIN, DEFAULT_DAILY_LIMIT);
    }

    public static CreditCard createCreditCard(CreditAccount account) {
        return new CreditCard(account, DEFAULT_PIN, DEFAULT_DAILY_LIMIT);
    }

    public static PrepaidCard createPrepaidCard(BaseAccount account, boolean isReloadable) {
        return new PrepaidCard(account, DEFAULT_PIN, DEFAULT_DAILY_LIMIT, isReloadable);
    }

    public static VirtualCard createVirtualCard(BaseAccount account, boolean isSingleUse) {
        return new VirtualCard(account, DEFAULT_PIN, DEFAULT_DAILY_LIMIT, isSingleUse);
    }
}
