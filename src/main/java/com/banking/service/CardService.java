package com.banking.service;

import com.banking.domain.account.BaseAccount;
import com.banking.domain.card.BaseCard;
import com.banking.domain.customer.Customer;
import com.banking.factory.CardFactory;
import com.banking.enums.CardType;

import java.util.ArrayList;
import java.util.List;

public class CardService {
    private List<BaseCard> cards;
    private AuditService auditService;

    public CardService(AuditService auditService) {
        this.cards = new ArrayList<>();
        this.auditService = auditService;
    }

    public BaseCard issueCard(Customer customer, CardType cardType, BaseAccount account, String pin, double dailyLimit) {
        BaseCard card = CardFactory.createCard(cardType, account, pin, dailyLimit);
        cards.add(card);
        customer.addCard(card);
        auditService.logCustomEvent("CARD_ISSUED", card.getCardId(), "Card issued", customer.getCustomerId());
        return card;
    }

    public void blockCard(String cardId) throws Exception {
        BaseCard card = findCardById(cardId);
        card.blockCard();
        auditService.logCustomEvent("CARD_BLOCKED", cardId, "Card blocked", "SYSTEM");
    }

    public void unblockCard(String cardId) throws Exception {
        BaseCard card = findCardById(cardId);
        card.unblockCard();
        auditService.logCustomEvent("CARD_UNBLOCKED", cardId, "Card unblocked", "SYSTEM");
    }

    public BaseCard findCardById(String cardId) throws Exception {
        return cards.stream()
                .filter(c -> c.getCardId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new Exception("Card not found"));
    }

    public BaseCard findCardByNumber(String cardNumber) throws Exception {
        return cards.stream()
                .filter(c -> c.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElseThrow(() -> new Exception("Card not found"));
    }

    public List<BaseCard> getAllCards() {
        return new ArrayList<>(cards);
    }
}
