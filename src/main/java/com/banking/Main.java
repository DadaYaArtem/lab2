package com.banking;

import com.banking.domain.account.*;
import com.banking.domain.bank.ATM;
import com.banking.domain.bank.Bank;
import com.banking.domain.bank.Branch;
import com.banking.domain.card.*;
import com.banking.domain.customer.Customer;
import com.banking.domain.employee.BankManager;
import com.banking.domain.employee.Teller;
import com.banking.domain.insurance.Insurance;
import com.banking.domain.investment.Investment;
import com.banking.domain.loan.Loan;
import com.banking.enums.CardType;
import com.banking.enums.Currency;
import com.banking.service.*;
import com.banking.util.*;
import com.banking.validator.*;

import java.time.LocalDate;

/**
 * Главный класс банковской системы - демонстрация функционала
 * Система реализует принципы ООП: наследование, полиморфизм, инкапсуляцию, абстракцию
 * Соответствует принципам SOLID и GRASP
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Банковская Система - Демонстрация ===\n");

        // Инициализация сервисов
        AuditService auditService = new AuditService();
        NotificationService notificationService = new NotificationService();
        AuthenticationService authService = new AuthenticationService();
        TransactionService transactionService = new TransactionService(auditService);
        AccountService accountService = new AccountService(auditService);
        LoanService loanService = new LoanService(auditService);
        CardService cardService = new CardService(auditService);
        PaymentProcessor paymentProcessor = new PaymentProcessor(auditService);
        FraudDetectionService fraudService = new FraudDetectionService();

        // Утилиты
        CurrencyConverter currencyConverter = new CurrencyConverter();
        InterestCalculator interestCalculator = new InterestCalculator();
        SecurityUtil securityUtil = new SecurityUtil();
        ReportGenerator reportGenerator = new ReportGenerator();

        // Валидаторы
        CustomerValidator customerValidator = new CustomerValidator();
        AccountValidator accountValidator = new AccountValidator();
        CardValidator cardValidator = new CardValidator();

        System.out.println("1. Создание банка и филиалов");
        System.out.println("=====================================");
        Bank bank = new Bank("TechBank", "TECHRUS33", "Moscow, Russia");
        Branch mainBranch = new Branch("BR001", "Main Branch", "Red Square, 1", "+7-495-123-4567");
        bank.addBranch(mainBranch);

        // Создание сотрудников
        BankManager manager = new BankManager("Ivan", "Petrov", "ivan.petrov@techbank.com",
                "+7-495-111-1111", "Retail Banking", 150000, 1000000);
        Teller teller = new Teller("Anna", "Ivanova", "anna.ivanova@techbank.com",
                "+7-495-222-2222", 60000);

        mainBranch.assignManager(manager);
        mainBranch.addStaff(teller);
        bank.hireEmployee(manager);
        bank.hireEmployee(teller);

        System.out.println("Банк создан: " + bank.getBankName());
        System.out.println("Филиал: " + mainBranch.getBranchName());
        System.out.println("Менеджер: " + manager.getFullName());
        System.out.println("Кассир: " + teller.getFullName());
        System.out.println();

        System.out.println("2. Регистрация клиентов");
        System.out.println("=====================================");
        Customer customer1 = new Customer("Alexey", "Smirnov", "alexey@email.com",
                "+7-916-111-1111", LocalDate.of(1990, 5, 15), "PASS4500123456");
        Customer customer2 = new Customer("Maria", "Kuznetsova", "maria@email.com",
                "+7-916-222-2222", LocalDate.of(1985, 8, 20), "PASS4500654321");

        customer1.setAddress("Moscow, Lenina St, 10");
        customer1.setNationality("RU");
        customer1.updateCreditScore(720);

        customer2.setAddress("Moscow, Pushkina St, 5");
        customer2.setNationality("RU");
        customer2.updateCreditScore(680);
        customer2.upgradeToPremium();

        bank.registerCustomer(customer1);
        bank.registerCustomer(customer2);

        // Регистрация в системе аутентификации
        authService.registerCustomer(customer1, "password123");
        authService.registerCustomer(customer2, "securepass456");

        System.out.println("Клиент 1: " + customer1.getFullName() + " (Score: " + customer1.getCreditScore() + ")");
        System.out.println("Клиент 2: " + customer2.getFullName() + " (Tier: " + customer2.getCustomerTier() + ")");
        System.out.println();

        System.out.println("3. Создание счетов");
        System.out.println("=====================================");

        // Создание различных типов счетов для первого клиента
        CheckingAccount checking1 = accountService.createCheckingAccount(customer1, Currency.RUB, 10000);
        SavingsAccount savings1 = accountService.createSavingsAccount(customer1, Currency.RUB, 5.5, 1000);
        CreditAccount credit1 = accountService.createCreditAccount(customer1, Currency.RUB, 100000, 19.9);

        // Создание счетов для второго клиента
        CheckingAccount checking2 = accountService.createCheckingAccount(customer2, Currency.USD, 1000);
        InvestmentAccount investment2 = accountService.createInvestmentAccount(customer2, Currency.USD, "AGGRESSIVE");
        BusinessAccount business2 = accountService.createBusinessAccount(customer2, Currency.USD,
                "Maria's Consulting", "TAX123456", 50000);

        System.out.println("Создано счетов для " + customer1.getFullName() + ": " + customer1.getAccounts().size());
        System.out.println("  - Checking: " + checking1.getAccountNumber());
        System.out.println("  - Savings: " + savings1.getAccountNumber());
        System.out.println("  - Credit: " + credit1.getAccountNumber());
        System.out.println();
        System.out.println("Создано счетов для " + customer2.getFullName() + ": " + customer2.getAccounts().size());
        System.out.println("  - Checking (USD): " + checking2.getAccountNumber());
        System.out.println("  - Investment: " + investment2.getAccountNumber());
        System.out.println("  - Business: " + business2.getAccountNumber());
        System.out.println();

        System.out.println("4. Выпуск карт");
        System.out.println("=====================================");

        DebitCard debitCard1 = (DebitCard) cardService.issueCard(customer1, CardType.DEBIT, checking1, "1234", 50000);
        CreditCard creditCard1 = (CreditCard) cardService.issueCard(customer1, CardType.CREDIT, credit1, "5678", 30000);
        VirtualCard virtualCard2 = (VirtualCard) cardService.issueCard(customer2, CardType.VIRTUAL, checking2, "9999", 5000);

        System.out.println("Карты клиента " + customer1.getFullName() + ": " + customer1.getCards().size());
        System.out.println("  - Debit: " + securityUtil.maskCardNumber(debitCard1.getCardNumber()));
        System.out.println("  - Credit: " + securityUtil.maskCardNumber(creditCard1.getCardNumber()));
        System.out.println();
        System.out.println("Карты клиента " + customer2.getFullName() + ": " + customer2.getCards().size());
        System.out.println("  - Virtual: " + securityUtil.maskCardNumber(virtualCard2.getCardNumber()));
        System.out.println();

        System.out.println("5. Операции с депозитами и снятием средств");
        System.out.println("=====================================");

        try {
            // Депозиты
            transactionService.executeDeposit(checking1, 50000, "Cash");
            transactionService.executeDeposit(savings1, 30000, "Transfer");
            transactionService.executeDeposit(checking2, 10000, "Cash");

            System.out.println("Депозит выполнен на счет " + checking1.getAccountNumber() + ": 50,000 RUB");
            System.out.println("Баланс: " + checking1.getBalance() + " " + checking1.getCurrency());
            System.out.println();

            // Снятие средств
            transactionService.executeWithdrawal(checking1, 5000, "ATM");
            System.out.println("Снятие со счета " + checking1.getAccountNumber() + ": 5,000 RUB");
            System.out.println("Новый баланс: " + checking1.getBalance() + " " + checking1.getCurrency());
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка операции: " + e.getMessage());
        }

        System.out.println("6. Переводы между счетами");
        System.out.println("=====================================");

        try {
            // Перевод между счетами одного клиента
            transactionService.executeTransfer(checking1, savings1, 10000, customer1.getFullName());
            System.out.println("Перевод: 10,000 RUB с " + checking1.getAccountNumber() +
                    " на " + savings1.getAccountNumber());
            System.out.println("Checking баланс: " + checking1.getBalance());
            System.out.println("Savings баланс: " + savings1.getBalance());
            System.out.println();

            // Демонстрация полиморфизма - transfer принимает любой Transferable
            savings1.transfer(checking1, 5000);
            System.out.println("Обратный перевод: 5,000 RUB");
            System.out.println("Checking баланс: " + checking1.getBalance());
            System.out.println("Savings баланс: " + savings1.getBalance());
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка перевода: " + e.getMessage());
        }

        System.out.println("7. Работа с кредитными картами");
        System.out.println("=====================================");

        try {
            // Аутентификация карты
            if (creditCard1.authenticate("5678")) {
                System.out.println("Карта аутентифицирована успешно");
            }

            // Покупка по кредитной карте
            creditCard1.processPayment(15000);
            System.out.println("Покупка по кредитной карте: 15,000 RUB");
            System.out.println("Использованный кредит: " + credit1.getUsedCredit());
            System.out.println("Доступный кредит: " + credit1.getAvailableBalance());
            System.out.println("Накопленный кэшбэк: " + creditCard1.getTotalCashback() + " RUB");
            System.out.println();

            // Погашение кредита
            credit1.makePayment(5000);
            System.out.println("Погашение кредита: 5,000 RUB");
            System.out.println("Остаток задолженности: " + credit1.getUsedCredit());
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка работы с картой: " + e.getMessage());
        }

        System.out.println("8. Инвестиционный счет");
        System.out.println("=====================================");

        try {
            transactionService.executeDeposit(investment2, 50000, "Initial Investment");
            System.out.println("Начальный депозит на инвестиционный счет: 50,000 USD");

            // Инвестирование в активы
            investment2.investInAsset("Tech Stocks", 20000);
            investment2.investInAsset("Bonds", 15000);

            System.out.println("Инвестировано в Tech Stocks: 20,000 USD");
            System.out.println("Инвестировано в Bonds: 15,000 USD");
            System.out.println("Портфель: " + investment2.getPortfolio());
            System.out.println("Общая стоимость портфеля: " + investment2.getPortfolioValue() + " USD");
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка инвестирования: " + e.getMessage());
        }

        System.out.println("9. Кредиты (Loans)");
        System.out.println("=====================================");

        try {
            // Создание заявки на кредит
            Loan loan = loanService.createLoanApplication(customer1, 500000, 12.5, 60, "Home Renovation");
            System.out.println("Заявка на кредит создана:");
            System.out.println("  Сумма: " + loan.getLoanAmount() + " RUB");
            System.out.println("  Срок: " + loan.getTermInMonths() + " месяцев");
            System.out.println("  Ставка: " + loan.getInterestRate() + "%");
            System.out.println("  Ежемесячный платеж: " + String.format("%.2f", loan.getMonthlyPayment()) + " RUB");

            // Одобрение кредита менеджером
            loanService.approveLoan(loan, manager);
            System.out.println("Кредит одобрен менеджером " + manager.getFullName());
            System.out.println("Статус: " + loan.getStatus());
            System.out.println();

            // Платеж по кредиту
            loanService.processLoanPayment(loan, loan.getMonthlyPayment());
            System.out.println("Произведен первый платеж: " + String.format("%.2f", loan.getMonthlyPayment()) + " RUB");
            System.out.println("Остаток задолженности: " + String.format("%.2f", loan.getRemainingBalance()) + " RUB");
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка работы с кредитом: " + e.getMessage());
        }

        System.out.println("10. Страхование");
        System.out.println("=====================================");

        Insurance insurance = new Insurance(customer1, "Life Insurance", 1000000, 5000, 10);
        System.out.println("Страховой полис оформлен:");
        System.out.println("  Номер полиса: " + insurance.getPolicyNumber());
        System.out.println("  Тип: " + insurance.getInsuranceType());
        System.out.println("  Покрытие: " + insurance.getCoverageAmount() + " RUB");
        System.out.println("  Ежемесячная премия: " + insurance.getPremiumAmount() + " RUB");
        System.out.println("  Годовая премия: " + insurance.calculateAnnualPremium() + " RUB");
        System.out.println();

        System.out.println("11. Инвестиционные продукты");
        System.out.println("=====================================");

        Investment investment = new Investment(customer2, "Fixed Deposit", 100000, 7.5, 12);
        System.out.println("Инвестиция создана:");
        System.out.println("  Тип: " + investment.getInvestmentType());
        System.out.println("  Сумма: " + investment.getPrincipalAmount() + " USD");
        System.out.println("  Доходность: " + investment.getReturnRate() + "%");
        System.out.println("  Прогноз через 12 месяцев: " +
                String.format("%.2f", investment.projectFutureValue(12)) + " USD");
        System.out.println();

        System.out.println("12. Работа с банкоматом");
        System.out.println("=====================================");

        ATM atm = new ATM("Moscow, Tverskaya St", 1000000);
        atm.setBranchId(mainBranch.getBranchId());

        try {
            System.out.println("Банкомат: " + atm.getLocation());
            System.out.println("Наличных в банкомате: " + atm.getCashAvailable() + " RUB");

            atm.withdrawCash(debitCard1, 10000);
            System.out.println("Снятие через банкомат: 10,000 RUB");
            System.out.println("Остаток в банкомате: " + atm.getCashAvailable() + " RUB");
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка работы с банкоматом: " + e.getMessage());
        }

        System.out.println("13. Обработка платежей и мерчанты");
        System.out.println("=====================================");

        paymentProcessor.registerMerchant("MERCHANT001", "ACC123456");
        System.out.println("Мерчант зарегистрирован: MERCHANT001");

        try {
            paymentProcessor.processPayment(debitCard1, "MERCHANT001", 2500);
            System.out.println("Платеж обработан: 2,500 RUB в пользу MERCHANT001");
            System.out.println("Баланс checking счета: " + checking1.getBalance());
            System.out.println("Бонусные баллы: " + debitCard1.getRewardPoints());
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка обработки платежа: " + e.getMessage());
        }

        System.out.println("14. Конвертация валют");
        System.out.println("=====================================");

        try {
            double rubAmount = 100000;
            double usdAmount = currencyConverter.convert(rubAmount, Currency.RUB, Currency.USD);
            System.out.println(rubAmount + " RUB = " + String.format("%.2f", usdAmount) + " USD");

            double eurAmount = currencyConverter.convert(rubAmount, Currency.RUB, Currency.EUR);
            System.out.println(rubAmount + " RUB = " + String.format("%.2f", eurAmount) + " EUR");

            double exchangeRate = currencyConverter.getExchangeRate(Currency.RUB, Currency.USD);
            System.out.println("Курс RUB/USD: " + String.format("%.4f", exchangeRate));
            System.out.println();

        } catch (Exception e) {
            System.err.println("Ошибка конвертации: " + e.getMessage());
        }

        System.out.println("15. Начисление процентов");
        System.out.println("=====================================");

        System.out.println("Баланс Savings до начисления: " + savings1.getBalance() + " RUB");
        savings1.applyInterest();
        System.out.println("Баланс Savings после начисления: " + savings1.getBalance() + " RUB");

        double projectedBalance = savings1.calculateProjectedBalance(12);
        System.out.println("Прогноз баланса через 12 месяцев: " + String.format("%.2f", projectedBalance) + " RUB");
        System.out.println();

        System.out.println("16. Отчеты и статистика");
        System.out.println("=====================================");

        System.out.println(reportGenerator.generateCustomerSummary(customer1));
        System.out.println(reportGenerator.generateAccountStatement(checking1,
                checking1.getCreatedAt(), checking1.getUpdatedAt()));

        // Обновление общих активов банка
        bank.updateTotalAssets();
        System.out.println("Общие активы банка: " + String.format("%.2f", bank.getTotalAssets()) + " RUB");
        System.out.println("Всего клиентов: " + bank.getCustomers().size());
        System.out.println("Всего сотрудников: " + bank.getEmployees().size());
        System.out.println();

        System.out.println("17. Аудит и журналирование");
        System.out.println("=====================================");

        System.out.println("Всего записей в журнале аудита: " + auditService.getAllAuditLogs().size());
        System.out.println("\nПоследние 5 записей аудита:");
        auditService.getAllAuditLogs().stream()
                .skip(Math.max(0, auditService.getAllAuditLogs().size() - 5))
                .forEach(log -> System.out.println("  " + log));
        System.out.println();

        System.out.println("18. Уведомления");
        System.out.println("=====================================");

        notificationService.sendTransactionNotification(customer1,
                "Transfer of 10,000 RUB completed");
        notificationService.sendLowBalanceWarning(customer1, checking1.getBalance());
        notificationService.sendPaymentReminder(customer1,
                credit1.getMinimumPayment(), credit1.getPaymentDueDate().toString());
        System.out.println();

        System.out.println("19. Проверка безопасности");
        System.out.println("=====================================");

        String token = securityUtil.generateSecureToken();
        String otp = securityUtil.generateOTP();

        System.out.println("Сгенерирован токен безопасности: " + token.substring(0, 20) + "...");
        System.out.println("Сгенерирован OTP: " + otp);
        System.out.println("Замаскированный email: " + securityUtil.maskEmail(customer1.getEmail()));
        System.out.println("Замаскированная карта: " + securityUtil.maskCardNumber(debitCard1.getCardNumber()));
        System.out.println();

        System.out.println("20. Демонстрация исключений");
        System.out.println("=====================================");

        // Попытка превышения лимита
        try {
            checking1.withdraw(1000000);
        } catch (Exception e) {
            System.out.println("Перехвачено исключение: " + e.getClass().getSimpleName());
            System.out.println("  Сообщение: " + e.getMessage());
        }

        // Попытка неверного PIN
        try {
            debitCard1.authenticate("0000");
        } catch (Exception e) {
            System.out.println("Перехвачено исключение: " + e.getClass().getSimpleName());
            System.out.println("  Сообщение: " + e.getMessage());
        }

        // Попытка перевода на неактивный счет
        try {
            checking1.setActive(false);
            transactionService.executeTransfer(checking1, savings1, 1000, "Test");
        } catch (Exception e) {
            System.out.println("Перехвачено исключение: " + e.getClass().getSimpleName());
            System.out.println("  Сообщение: " + e.getMessage());
        } finally {
            checking1.setActive(true);
        }

        System.out.println();
        System.out.println("=== Демонстрация завершена ===");
        System.out.println("\nСистема успешно демонстрирует:");
        System.out.println("- ООП принципы: наследование, полиморфизм, инкапсуляцию, абстракцию");
        System.out.println("- SOLID принципы: SRP, OCP, LSP, ISP, DIP");
        System.out.println("- GRASP принципы: Information Expert, Creator, Controller, Low Coupling, High Cohesion");
        System.out.println("- Паттерны проектирования: Factory, Strategy, Observer");
        System.out.println("- 12 персональных исключений для обработки ошибок");
        System.out.println("- 30+ ассоциаций между классами");
        System.out.println("- 100+ уникальных поведений (методов)");
        System.out.println("- 150+ полей");
        System.out.println("- 50+ классов");
    }
}
