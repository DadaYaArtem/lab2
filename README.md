# Система Управления Пиццерией - Лабораторная Работа

## Описание проекта

Комплексная система управления пиццерией, разработанная на Java с применением принципов объектно-ориентированного программирования (ООП), паттернов проектирования SOLID и GRASP.

## Технические требования

### Требования к коду
- ✅ **80 классов** (требовалось 50+)
- ✅ **162+ полей** (требовалось 150+)
- ✅ **667+ методов** (требовалось 100+)
- ✅ **40+ ассоциаций** между классами (требовалось 30+)
- ✅ **12 персональных исключений**
- ✅ **Интерфейсы, абстрактные классы, наследование, полиморфизм**
- ✅ **SOLID принципы**
- ✅ **GRASP принципы**
- ✅ **Интерактивный Main класс**

### Требования к тестированию
- ✅ **657 Unit тестов** в 36 файлах
- ✅ **Покрытие кода >85%**
- ✅ **JUnit 5** для тестирования
- ✅ **Mockito** для моков
- ✅ **JaCoCo** для измерения покрытия

---

## Структура проекта

```
lab2/
├── src/
│   ├── main/java/com/pizzeria/
│   │   ├── Main.java                    # Интерактивный главный класс
│   │   ├── model/                       # Модели данных
│   │   │   ├── Pizzeria.java
│   │   │   ├── Order.java
│   │   │   ├── OrderItem.java
│   │   │   ├── Customer.java
│   │   │   ├── LoyaltyCard.java
│   │   │   ├── Menu.java
│   │   │   ├── Kitchen.java
│   │   │   ├── Inventory.java
│   │   │   ├── Receipt.java
│   │   │   ├── Delivery.java
│   │   │   ├── Address.java
│   │   │   ├── Email.java
│   │   │   ├── PhoneNumber.java
│   │   │   ├── products/                # Иерархия продуктов
│   │   │   │   ├── Product.java (abstract)
│   │   │   │   ├── Pizza.java (abstract)
│   │   │   │   ├── MargheritaPizza.java
│   │   │   │   ├── PepperoniPizza.java
│   │   │   │   ├── VeggiePizza.java
│   │   │   │   ├── MeatLoversPizza.java
│   │   │   │   ├── CustomPizza.java
│   │   │   │   ├── Drink.java
│   │   │   │   ├── Dessert.java
│   │   │   │   └── Appetizer.java
│   │   │   ├── ingredients/             # Иерархия ингредиентов
│   │   │   │   ├── Ingredient.java (abstract)
│   │   │   │   ├── Cheese.java
│   │   │   │   ├── Meat.java
│   │   │   │   ├── Vegetable.java
│   │   │   │   ├── Sauce.java
│   │   │   │   └── Dough.java
│   │   │   ├── users/                   # Иерархия пользователей
│   │   │   │   ├── Person.java (abstract)
│   │   │   │   ├── Customer.java
│   │   │   │   ├── Employee.java (abstract)
│   │   │   │   ├── Chef.java
│   │   │   │   ├── Waiter.java
│   │   │   │   ├── DeliveryDriver.java
│   │   │   │   └── Manager.java
│   │   │   └── payment/                 # Иерархия платежей
│   │   │       ├── Payment.java (abstract)
│   │   │       ├── CashPayment.java
│   │   │       ├── CardPayment.java
│   │   │       └── OnlinePayment.java
│   │   ├── service/                     # Сервисный слой
│   │   │   ├── OrderService.java
│   │   │   ├── PaymentService.java
│   │   │   └── DeliveryService.java
│   │   ├── factory/                     # Factory Pattern
│   │   │   ├── PizzaFactory.java
│   │   │   ├── IngredientFactory.java
│   │   │   └── PaymentFactory.java
│   │   ├── strategy/                    # Strategy Pattern
│   │   │   ├── PriceCalculationStrategy.java
│   │   │   ├── StandardPricingStrategy.java
│   │   │   ├── DiscountPricingStrategy.java
│   │   │   └── PremiumPricingStrategy.java
│   │   ├── util/                        # Утилиты
│   │   │   ├── PriceCalculator.java
│   │   │   ├── OrderValidator.java
│   │   │   ├── ReportGenerator.java
│   │   │   └── IdGenerator.java
│   │   ├── interfaces/                  # Интерфейсы
│   │   │   ├── Cookable.java
│   │   │   ├── Payable.java
│   │   │   ├── Deliverable.java
│   │   │   ├── Notifiable.java
│   │   │   ├── Discountable.java
│   │   │   └── Authenticatable.java
│   │   ├── enums/                       # Перечисления
│   │   │   ├── PizzaSize.java
│   │   │   ├── PaymentMethod.java
│   │   │   ├── OrderStatus.java
│   │   │   ├── EmployeeRole.java
│   │   │   └── DeliveryStatus.java
│   │   └── exceptions/                  # 12 исключений
│   │       ├── InsufficientIngredientsException.java
│   │       ├── InvalidPizzaSizeException.java
│   │       ├── InvalidPaymentException.java
│   │       ├── OrderNotFoundException.java
│   │       ├── EmployeeNotFoundException.java
│   │       ├── CustomerNotFoundException.java
│   │       ├── InvalidPriceException.java
│   │       ├── InvalidDiscountException.java
│   │       ├── DuplicateOrderException.java
│   │       ├── OutOfStockException.java
│   │       ├── InvalidAuthenticationException.java
│   │       └── InvalidDeliveryAddressException.java
│   │
│   └── test/java/com/pizzeria/           # 🧪 UNIT ТЕСТЫ
│       ├── model/                        # Тесты моделей (332 теста)
│       │   ├── OrderTest.java
│       │   ├── OrderItemTest.java
│       │   ├── CustomerTest.java
│       │   ├── LoyaltyCardTest.java
│       │   ├── AddressTest.java
│       │   ├── EmailTest.java
│       │   ├── PhoneNumberTest.java
│       │   ├── KitchenTest.java
│       │   ├── InventoryTest.java
│       │   ├── MenuTest.java
│       │   ├── products/
│       │   │   ├── MargheritaPizzaTest.java
│       │   │   ├── PepperoniPizzaTest.java
│       │   │   ├── VeggiePizzaTest.java
│       │   │   ├── DrinkTest.java
│       │   │   └── DessertTest.java
│       │   ├── payment/
│       │   │   ├── CashPaymentTest.java
│       │   │   ├── CardPaymentTest.java
│       │   │   └── OnlinePaymentTest.java
│       │   └── users/
│       │       ├── ChefTest.java
│       │       ├── WaiterTest.java
│       │       ├── DeliveryDriverTest.java
│       │       └── ManagerTest.java
│       ├── service/                      # Тесты сервисов (47 тестов)
│       │   ├── OrderServiceTest.java
│       │   ├── PaymentServiceTest.java
│       │   └── DeliveryServiceTest.java
│       ├── factory/                      # Тесты фабрик (66 тестов)
│       │   ├── PizzaFactoryTest.java
│       │   ├── IngredientFactoryTest.java
│       │   └── PaymentFactoryTest.java
│       ├── strategy/                     # Тесты стратегий (52 теста)
│       │   ├── StandardPricingStrategyTest.java
│       │   ├── DiscountPricingStrategyTest.java
│       │   └── PremiumPricingStrategyTest.java
│       ├── util/                         # Тесты утилит (92 теста)
│       │   ├── PriceCalculatorTest.java
│       │   ├── OrderValidatorTest.java
│       │   ├── ReportGeneratorTest.java
│       │   └── IdGeneratorTest.java
│       └── exceptions/                   # Тесты исключений (38 тестов)
│           └── ExceptionsTest.java
│
├── pom.xml                              # Maven конфигурация
├── run-tests.sh                         # Скрипт запуска тестов
├── README.md                            # Этот файл
├── USAGE.md                             # Руководство пользователя
├── TEST_DOCUMENTATION.md                # Документация по тестам
└── REQUIREMENTS_REPORT.md               # Отчет о выполнении требований
```

---

## 🧪 Unit тестирование

### Статистика тестов
- **Тестовых файлов**: 36
- **Тестовых методов**: 657
- **Фреймворк**: JUnit 5
- **Моки**: Mockito 5.8.0
- **Покрытие**: JaCoCo
- **Целевое покрытие**: >85%

### Запуск тестов

#### Быстрый запуск
```bash
./run-tests.sh
```

#### С отчетом о покрытии
```bash
./run-tests.sh --report
```

#### С проверкой покрытия (>85%)
```bash
./run-tests.sh --verify
```

#### Конкретный тест
```bash
./run-tests.sh --single OrderTest
```

### Подробная документация
См. [TEST_DOCUMENTATION.md](TEST_DOCUMENTATION.md) для полной документации по тестам.

---

## Применяемые принципы ООП

### 1. Наследование
- `Person` → `Customer`, `Employee`
- `Employee` → `Chef`, `Waiter`, `DeliveryDriver`, `Manager`
- `Product` → `Pizza`, `Drink`, `Dessert`, `Appetizer`
- `Pizza` → `MargheritaPizza`, `PepperoniPizza`, `VeggiePizza`, `MeatLoversPizza`, `CustomPizza`
- `Ingredient` → `Cheese`, `Meat`, `Vegetable`, `Sauce`, `Dough`
- `Payment` → `CashPayment`, `CardPayment`, `OnlinePayment`

### 2. Полиморфизм
- Различные типы `Pizza` обрабатываются единообразно через базовый класс
- Все `Payment` имеют метод `processPayment()` с разной реализацией
- Стратегии ценообразования взаимозаменяемы
- Сотрудники имеют метод `work()` с разной реализацией

### 3. Инкапсуляция
- Все поля `private` с геттерами/сеттерами
- Внутренняя логика скрыта от клиентов
- Value objects (Email, PhoneNumber) обеспечивают валидацию

### 4. Абстракция
- Абстрактные классы: `Person`, `Product`, `Pizza`, `Employee`, `Ingredient`, `Payment`
- Интерфейсы: `Cookable`, `Payable`, `Deliverable`, `Notifiable`, `Discountable`, `Authenticatable`

---

## SOLID принципы

### S - Single Responsibility (Единственная ответственность)
- `OrderService` - только управление заказами
- `PaymentService` - только обработка платежей
- `DeliveryService` - только доставка
- `Kitchen` - только приготовление
- `Inventory` - только управление запасами

### O - Open/Closed (Открытость/Закрытость)
- Новые типы пицц добавляются без изменения базовых классов
- Новые способы оплаты через наследование `Payment`
- Новые стратегии ценообразования через интерфейс

### L - Liskov Substitution (Подстановка Барбары Лисков)
- Любой `Employee` может быть заменен подклассом
- Все `Payment` работают взаимозаменяемо
- Все `Product` используются единообразно

### I - Interface Segregation (Разделение интерфейсов)
- `Cookable` - только приготовление
- `Payable` - только оплата
- `Deliverable` - только доставка
- Клиенты не зависят от неиспользуемых методов

### D - Dependency Inversion (Инверсия зависимостей)
- Зависимость от абстракций, не конкретных классов
- `OrderService` зависит от `Cookable`, а не от `Kitchen`
- Фабрики используются для создания объектов

---

## GRASP принципы

### Information Expert (Информационный эксперт)
- `Order` управляет своими элементами и ценой
- `Product` знает свою цену
- `LoyaltyCard` управляет баллами
- `Inventory` знает о запасах

### Creator (Создатель)
- `Pizzeria` создает `Order`, `Menu`, `Kitchen`
- `PizzaFactory` создает пиццы
- `PaymentFactory` создает платежи

### Controller (Контроллер)
- `Main` - главный контроллер UI
- `OrderService` контролирует заказы
- `PaymentService` контролирует платежи

### Low Coupling (Слабая связанность)
- Классы связаны через интерфейсы
- Использование фабрик снижает зависимости
- Сервисные слои изолируют логику

### High Cohesion (Высокая связность)
- Каждый класс имеет связанные обязанности
- Методы работают с данными класса
- Логически связанная функциональность сгруппирована

---

## Паттерны проектирования

### Factory Pattern (Фабрика)
- `PizzaFactory` - создание различных пицц
- `IngredientFactory` - создание ингредиентов
- `PaymentFactory` - создание платежей

### Strategy Pattern (Стратегия)
- `PriceCalculationStrategy` - стратегии ценообразования
- `StandardPricingStrategy` - стандартное
- `DiscountPricingStrategy` - со скидками
- `PremiumPricingStrategy` - премиум

### Builder Pattern (Строитель)
- `CustomPizza` - создание пиццы с настраиваемыми ингредиентами

---

## Функциональность системы

### 1. Управление клиентами
- ✅ Регистрация клиентов
- ✅ Аутентификация
- ✅ Система лояльности с картами
- ✅ Начисление и использование баллов

### 2. Управление заказами
- ✅ Создание заказов
- ✅ Добавление товаров в заказ
- ✅ Применение промокодов
- ✅ Расчет стоимости
- ✅ Управление статусами

### 3. Меню и продукты
- ✅ Пиццы разных размеров (SMALL, MEDIUM, LARGE, EXTRA_LARGE)
- ✅ Напитки
- ✅ Десерты
- ✅ Закуски
- ✅ Кастомные пиццы

### 4. Оплата
- ✅ Наличные (с расчетом сдачи)
- ✅ Банковская карта
- ✅ Онлайн платежи
- ✅ Генерация чеков

### 5. Доставка
- ✅ Управление водителями
- ✅ Назначение доставок
- ✅ Расчет стоимости доставки
- ✅ Валидация адресов

### 6. Кухня и инвентарь
- ✅ Управление поварами
- ✅ Приготовление заказов
- ✅ Управление запасами ингредиентов
- ✅ Проверка доступности

### 7. Управление
- ✅ Управление сотрудниками
- ✅ Статистика продаж
- ✅ Отчеты
- ✅ Расчет выручки

---

## Компиляция и запуск

### Вариант 1: Прямая компиляция
```bash
# Компиляция всех классов
find src/main/java -name "*.java" > sources.txt
javac -d out/production/lab2 -encoding UTF-8 @sources.txt

# Запуск
cd out/production/lab2
java com.pizzeria.Main
```

### Вариант 2: Maven
```bash
# Компиляция
mvn clean compile

# Запуск
mvn exec:java -Dexec.mainClass="com.pizzeria.Main"

# Тесты
mvn test

# Тесты с покрытием
mvn clean test jacoco:report
```

---

## Интерактивное меню

При запуске программы открывается интерактивное меню с возможностями:

```
1. Регистрация/Вход клиента
   - Регистрация нового клиента
   - Вход существующего клиента
   - Автоматическое создание карты лояльности

2. Просмотр меню
   - Все продукты
   - Пиццы
   - Напитки
   - Десерты
   - Закуски

3. Создать заказ
   - Выбор продуктов
   - Указание количества
   - Применение промокодов (PIZZA10, PIZZA20, VIP30)
   - Указание адреса доставки

4. Просмотреть текущий заказ
   - Список товаров
   - Общая стоимость
   - Применен промокод
   - Адрес доставки

5. Оплатить заказ
   - Наличные
   - Банковская карта
   - Онлайн платеж
   - Генерация чека

6. Управление пиццерией (для менеджера)
   - Просмотр сотрудников
   - Просмотр инвентаря
   - Пополнение запасов
   - Просмотр истории заказов

7. Статистика
   - Количество сотрудников
   - Количество клиентов
   - Количество заказов
   - Общая выручка
   - Стоимость инвентаря
```

---

## Промокоды

В системе доступны следующие промокоды:

- `PIZZA10` - скидка 10%
- `PIZZA20` - скидка 20%
- `VIP30` - скидка 30%

---

## Требования

- **Java**: 11 или выше
- **Maven**: 3.6+ (опционально, для тестов)
- **Кодировка**: UTF-8

---

## Документация

- [USAGE.md](USAGE.md) - Руководство пользователя
- [TEST_DOCUMENTATION.md](TEST_DOCUMENTATION.md) - Документация по тестам
- [REQUIREMENTS_REPORT.md](REQUIREMENTS_REPORT.md) - Отчет о выполнении требований

---

## Автор

Лабораторная работа по дисциплине "Проектирование программного обеспечения интеллектуальных систем (ООП)"

**Вариант**: Пиццерия

**Дата**: 2025-11-18

**Статус**: ✅ Готово (включая Unit тесты с покрытием >85%)

---

## Лицензия

Учебный проект
