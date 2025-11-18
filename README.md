# Банковская Система - Лабораторная Работа 2

## Описание проекта

Комплексная банковская система, разработанная на Java с применением принципов объектно-ориентированного программирования (ООП), паттернов проектирования SOLID и GRASP.

## Технические требования

- ✅ **50+ классов** (всего 75+ классов включая внутренние классы)
- ✅ **150+ полей** во всех классах
- ✅ **100+ уникальных методов** (поведений)
- ✅ **30+ ассоциаций** между классами
- ✅ **12 персональных исключений**
- ✅ **Интерфейсы, абстрактные классы, наследование, полиморфизм**
- ✅ **SOLID принципы**
- ✅ **GRASP принципы**

## Структура проекта

```
src/main/java/com/banking/
├── Main.java                    # Главный класс с демонстрацией функционала
├── domain/                      # Доменные модели
│   ├── account/                 # Типы счетов
│   │   ├── BaseAccount.java
│   │   ├── CheckingAccount.java
│   │   ├── SavingsAccount.java
│   │   ├── InvestmentAccount.java
│   │   ├── BusinessAccount.java
│   │   └── CreditAccount.java
│   ├── card/                    # Типы карт
│   │   ├── BaseCard.java
│   │   ├── DebitCard.java
│   │   ├── CreditCard.java
│   │   ├── PrepaidCard.java
│   │   └── VirtualCard.java
│   ├── customer/                # Клиенты
│   │   ├── BasePerson.java
│   │   └── Customer.java
│   ├── employee/                # Сотрудники
│   │   ├── Employee.java
│   │   ├── BankManager.java
│   │   └── Teller.java
│   ├── transaction/             # Транзакции
│   │   ├── BaseTransaction.java
│   │   ├── TransferTransaction.java
│   │   ├── DepositTransaction.java
│   │   └── WithdrawalTransaction.java
│   ├── bank/                    # Банковские структуры
│   │   ├── Bank.java
│   │   ├── Branch.java
│   │   └── ATM.java
│   ├── loan/                    # Кредиты
│   │   └── Loan.java
│   ├── insurance/               # Страхование
│   │   └── Insurance.java
│   ├── investment/              # Инвестиции
│   │   └── Investment.java
│   ├── Transferable.java        # Интерфейс для переводов
│   ├── Authenticatable.java     # Интерфейс аутентификации
│   ├── Auditable.java           # Интерфейс аудита
│   ├── Notifiable.java          # Интерфейс уведомлений
│   └── Reportable.java          # Интерфейс отчетности
├── service/                     # Сервисные классы
│   ├── TransactionService.java
│   ├── AuthenticationService.java
│   ├── AuditService.java
│   ├── NotificationService.java
│   ├── LoanService.java
│   ├── AccountService.java
│   ├── CardService.java
│   ├── PaymentProcessor.java
│   └── FraudDetectionService.java
├── validator/                   # Валидаторы
│   ├── AccountValidator.java
│   ├── CustomerValidator.java
│   ├── CardValidator.java
│   └── TransactionValidator.java
├── factory/                     # Фабрики (Factory Pattern)
│   ├── AccountFactory.java
│   └── CardFactory.java
├── strategy/                    # Стратегии (Strategy Pattern)
│   ├── InterestStrategy.java
│   ├── SimpleInterestStrategy.java
│   └── CompoundInterestStrategy.java
├── observer/                    # Наблюдатели (Observer Pattern)
│   ├── AccountObserver.java
│   └── AlertObserver.java
├── util/                        # Утилиты
│   ├── CurrencyConverter.java
│   ├── InterestCalculator.java
│   ├── DateTimeUtil.java
│   ├── ReportGenerator.java
│   └── SecurityUtil.java
├── enums/                       # Перечисления
│   ├── AccountType.java
│   ├── CardType.java
│   ├── Currency.java
│   ├── TransactionType.java
│   ├── TransactionStatus.java
│   └── LoanStatus.java
└── exception/                   # Персональные исключения (12 шт.)
    ├── InsufficientFundsException.java
    ├── InvalidPasswordException.java
    ├── CardBlockedException.java
    ├── AccountNotFoundException.java
    ├── InvalidTransactionException.java
    ├── DailyLimitExceededException.java
    ├── LoanNotApprovedException.java
    ├── InvalidCurrencyException.java
    ├── CustomerNotAuthorizedException.java
    ├── CardExpiredException.java
    ├── InvalidAccountTypeException.java
    └── DuplicateAccountException.java
```

## Применяемые принципы ООП

### 1. Наследование
- `BaseAccount` → `CheckingAccount`, `SavingsAccount`, `InvestmentAccount`, `BusinessAccount`, `CreditAccount`
- `BaseCard` → `DebitCard`, `CreditCard`, `PrepaidCard`, `VirtualCard`
- `BasePerson` → `Customer`, `Employee`
- `Employee` → `BankManager`, `Teller`
- `BaseTransaction` → `TransferTransaction`, `DepositTransaction`, `WithdrawalTransaction`

### 2. Полиморфизм
- Интерфейсы `Transferable`, `Authenticatable`, `Auditable`, `Notifiable`, `Reportable`
- Переопределение методов `transfer()`, `authenticate()`, `applyInterest()`
- Стратегии расчета процентов (`InterestStrategy`)

### 3. Инкапсуляция
- Все поля классов `private`
- Доступ через геттеры и сеттеры
- Скрытие реализации в абстрактных классах

### 4. Абстракция
- Абстрактные классы: `BasePerson`, `BaseAccount`, `BaseCard`, `BaseTransaction`
- Интерфейсы для определения контрактов

## SOLID принципы

### S - Single Responsibility Principle (Принцип единственной ответственности)
- `AccountService` - только управление счетами
- `TransactionService` - только обработка транзакций
- `AuthenticationService` - только аутентификация

### O - Open/Closed Principle (Принцип открытости/закрытости)
- Расширение функциональности через наследование (`BaseAccount` → новые типы счетов)
- Использование стратегий для расчета процентов

### L - Liskov Substitution Principle (Принцип подстановки Барбары Лисков)
- Любой подкласс `BaseAccount` может использоваться вместо базового класса
- `DebitCard`, `CreditCard` заменяемы в контексте `BaseCard`

### I - Interface Segregation Principle (Принцип разделения интерфейсов)
- Отдельные интерфейсы: `Transferable`, `Authenticatable`, `Auditable`, `Notifiable`
- Классы реализуют только нужные им интерфейсы

### D - Dependency Inversion Principle (Принцип инверсии зависимостей)
- Зависимость от абстракций (`Transferable`, а не конкретных классов)
- Инъекция зависимостей в сервисах (`AuditService` → `TransactionService`)

## GRASP принципы

### Information Expert
- `Account` знает свой баланс и может выполнять операции с ним
- `Loan` знает свои параметры и может рассчитывать платежи

### Creator
- `AccountFactory` создает счета
- `CardFactory` создает карты

### Controller
- Сервисные классы (`TransactionService`, `AccountService`) координируют операции

### Low Coupling
- Использование интерфейсов для снижения связанности
- Зависимость от абстракций

### High Cohesion
- Каждый класс имеет четко определенную ответственность
- Связанные методы находятся в одном классе

## Паттерны проектирования

### 1. Factory (Фабрика)
- `AccountFactory` - создание различных типов счетов
- `CardFactory` - создание различных типов карт

### 2. Strategy (Стратегия)
- `InterestStrategy` с реализациями `SimpleInterestStrategy` и `CompoundInterestStrategy`

### 3. Observer (Наблюдатель)
- `AccountObserver` для мониторинга изменений в счетах
- `AlertObserver` для оповещений

## Функциональность системы

### Управление счетами
- Создание счетов разных типов (Checking, Savings, Investment, Business, Credit)
- Депозиты и снятия средств
- Переводы между счетами
- Начисление процентов

### Управление картами
- Выпуск карт (Debit, Credit, Prepaid, Virtual)
- Блокировка/разблокировка карт
- Обработка платежей
- Кэшбэк и бонусные программы

### Кредиты
- Создание заявок на кредит
- Одобрение кредитов менеджером
- Платежи по кредиту
- Расчет процентов и графика платежей

### Транзакции
- Переводы, депозиты, снятия
- Журналирование всех операций
- Возможность отката транзакций

### Безопасность
- Аутентификация клиентов и карт
- Генерация OTP
- Маскирование конфиденциальных данных
- Обнаружение мошенничества

### Уведомления
- Email уведомления
- SMS уведомления
- Оповещения о транзакциях

## Компиляция и запуск

```bash
# Компиляция
javac -d out -sourcepath src/main/java src/main/java/com/banking/Main.java

# Запуск
cd out && java com.banking.Main
```

## Примеры использования

Программа демонстрирует 20 сценариев использования:

1. Создание банка и филиалов
2. Регистрация клиентов
3. Создание счетов
4. Выпуск карт
5. Операции с депозитами и снятием средств
6. Переводы между счетами
7. Работа с кредитными картами
8. Инвестиционный счет
9. Кредиты (Loans)
10. Страхование
11. Инвестиционные продукты
12. Работа с банкоматом
13. Обработка платежей и мерчанты
14. Конвертация валют
15. Начисление процентов
16. Отчеты и статистика
17. Аудит и журналирование
18. Уведомления
19. Проверка безопасности
20. Демонстрация исключений

## Авторы

Лабораторная работа по дисциплине "Проектирование программного обеспечения интеллектуальных систем (ООП)"

## Лицензия

Учебный проект
