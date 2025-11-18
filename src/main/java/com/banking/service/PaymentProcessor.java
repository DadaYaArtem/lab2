package com.banking.service;

import com.banking.domain.card.BaseCard;

import java.util.HashMap;
import java.util.Map;

public class PaymentProcessor {
    private Map<String, String> merchantAccounts;
    private AuditService auditService;

    public PaymentProcessor(AuditService auditService) {
        this.merchantAccounts = new HashMap<>();
        this.auditService = auditService;
    }

    public void registerMerchant(String merchantId, String accountNumber) {
        merchantAccounts.put(merchantId, accountNumber);
    }

    public void processPayment(BaseCard card, String merchantId, double amount) throws Exception {
        if (!merchantAccounts.containsKey(merchantId)) {
            throw new Exception("Merchant not registered");
        }

        card.processPayment(amount);
        auditService.logCustomEvent("PAYMENT_PROCESSED", card.getCardId(),
                "Payment of " + amount + " to merchant " + merchantId, "SYSTEM");
    }

    public void processRefund(BaseCard card, String merchantId, double amount) throws Exception {
        if (!merchantAccounts.containsKey(merchantId)) {
            throw new Exception("Merchant not registered");
        }

        card.getLinkedAccount().deposit(amount);
        auditService.logCustomEvent("REFUND_PROCESSED", card.getCardId(),
                "Refund of " + amount + " from merchant " + merchantId, "SYSTEM");
    }

    public boolean isMerchantRegistered(String merchantId) {
        return merchantAccounts.containsKey(merchantId);
    }
}
