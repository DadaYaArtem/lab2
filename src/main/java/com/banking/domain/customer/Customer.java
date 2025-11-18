package com.banking.domain.customer;

import com.banking.domain.Notifiable;
import com.banking.domain.account.BaseAccount;
import com.banking.domain.card.BaseCard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer extends BasePerson implements Notifiable {
    private String customerId;
    private LocalDate dateOfBirth;
    private String address;
    private String nationality;
    private String identificationNumber;
    private List<BaseAccount> accounts;
    private List<BaseCard> cards;
    private String customerTier;
    private double creditScore;
    private boolean isPremium;

    public Customer(String firstName, String lastName, String email, String phoneNumber,
                    LocalDate dateOfBirth, String identificationNumber) {
        super(firstName, lastName, email, phoneNumber);
        this.customerId = this.id;
        this.dateOfBirth = dateOfBirth;
        this.identificationNumber = identificationNumber;
        this.accounts = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.customerTier = "STANDARD";
        this.creditScore = 650.0;
        this.isPremium = false;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getDisplayInfo() {
        return String.format("Customer: %s (ID: %s, Tier: %s)", getFullName(), customerId, customerTier);
    }

    public void addAccount(BaseAccount account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    public void removeAccount(BaseAccount account) {
        accounts.remove(account);
    }

    public void addCard(BaseCard card) {
        if (!cards.contains(card)) {
            cards.add(card);
        }
    }

    public void removeCard(BaseCard card) {
        cards.remove(card);
    }

    public double getTotalBalance() {
        return accounts.stream()
                .mapToDouble(BaseAccount::getBalance)
                .sum();
    }

    public void upgradeToPremium() {
        this.isPremium = true;
        this.customerTier = "PREMIUM";
    }

    public void updateCreditScore(double newScore) {
        this.creditScore = Math.max(300, Math.min(850, newScore));
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("[NOTIFICATION to " + getFullName() + "]: " + message);
    }

    @Override
    public void sendEmailNotification(String subject, String body) {
        System.out.println("[EMAIL to " + email + "]");
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }

    @Override
    public void sendSMSNotification(String message) {
        System.out.println("[SMS to " + phoneNumber + "]: " + message);
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public List<BaseAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public List<BaseCard> getCards() {
        return new ArrayList<>(cards);
    }

    public String getCustomerTier() {
        return customerTier;
    }

    public double getCreditScore() {
        return creditScore;
    }

    public boolean isPremium() {
        return isPremium;
    }
}
