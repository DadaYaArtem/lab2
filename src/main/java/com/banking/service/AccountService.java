package com.banking.service;

import com.banking.domain.account.*;
import com.banking.domain.customer.Customer;
import com.banking.enums.AccountType;
import com.banking.enums.Currency;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.DuplicateAccountException;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private List<BaseAccount> accounts;
    private AuditService auditService;

    public AccountService(AuditService auditService) {
        this.accounts = new ArrayList<>();
        this.auditService = auditService;
    }

    public CheckingAccount createCheckingAccount(Customer customer, Currency currency, double overdraftLimit) {
        CheckingAccount account = new CheckingAccount(currency, overdraftLimit);
        accounts.add(account);
        customer.addAccount(account);
        auditService.logAccountCreation(account);
        return account;
    }

    public SavingsAccount createSavingsAccount(Customer customer, Currency currency,
                                               double interestRate, double minimumBalance) {
        SavingsAccount account = new SavingsAccount(currency, interestRate, minimumBalance);
        accounts.add(account);
        customer.addAccount(account);
        auditService.logAccountCreation(account);
        return account;
    }

    public InvestmentAccount createInvestmentAccount(Customer customer, Currency currency, String riskProfile) {
        InvestmentAccount account = new InvestmentAccount(currency, riskProfile);
        accounts.add(account);
        customer.addAccount(account);
        auditService.logAccountCreation(account);
        return account;
    }

    public BusinessAccount createBusinessAccount(Customer customer, Currency currency,
                                                  String businessName, String taxId, double creditLine) {
        BusinessAccount account = new BusinessAccount(currency, businessName, taxId, creditLine);
        accounts.add(account);
        customer.addAccount(account);
        auditService.logAccountCreation(account);
        return account;
    }

    public CreditAccount createCreditAccount(Customer customer, Currency currency,
                                            double creditLimit, double interestRate) {
        CreditAccount account = new CreditAccount(currency, creditLimit, interestRate);
        accounts.add(account);
        customer.addAccount(account);
        auditService.logAccountCreation(account);
        return account;
    }

    public BaseAccount findAccountById(String accountId) throws AccountNotFoundException {
        return accounts.stream()
                .filter(a -> a.getAccountId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public BaseAccount findAccountByNumber(String accountNumber) throws AccountNotFoundException {
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public void closeAccount(String accountId) throws AccountNotFoundException {
        BaseAccount account = findAccountById(accountId);
        account.setActive(false);
        auditService.logAccountModification(account, "Account closed");
    }

    public void applyInterestToAllAccounts() {
        accounts.stream()
                .filter(BaseAccount::isActive)
                .forEach(account -> {
                    account.applyInterest();
                    auditService.logAccountModification(account, "Interest applied");
                });
    }

    public List<BaseAccount> getAccountsByType(AccountType type) {
        return accounts.stream()
                .filter(a -> a.getAccountType() == type)
                .toList();
    }

    public List<BaseAccount> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
}
