# Документация по Unit тестам

## Статистика тестирования

### Общая информация
- **Количество тестовых файлов**: 36
- **Количество тестовых методов**: 657
- **Целевое покрытие кода**: >85%
- **Фреймворк тестирования**: JUnit 5
- **Библиотека для моков**: Mockito 5.8.0
- **Инструмент измерения покрытия**: JaCoCo 0.8.11

---

## Структура тестов

### 1. Model Tests (15 файлов, 332 теста)

#### Core Model Tests (7 файлов)
1. **OrderTest.java** - Тестирование класса Order
   - Создание заказов
   - Добавление товаров
   - Расчет стоимости
   - Применение скидок
   - Управление статусами

2. **OrderItemTest.java** - Тестирование OrderItem
   - Создание элементов заказа
   - Расчет подитогов
   - Управление количеством

3. **CustomerTest.java** - Тестирование Customer
   - Регистрация клиентов
   - Аутентификация
   - Управление данными клиента
   - Работа с картами лояльности

4. **LoyaltyCardTest.java** - Тестирование LoyaltyCard
   - Начисление баллов
   - Использование баллов
   - Расчет скидок

5. **AddressTest.java** - Тестирование Address
   - Создание адресов
   - Валидация
   - Форматирование

6. **EmailTest.java** - Тестирование Email
   - Валидация email адресов
   - Сравнение

7. **PhoneNumberTest.java** - Тестирование PhoneNumber
   - Валидация номеров
   - Различные форматы

#### Product Tests (5 файлов, 114 тестов)
8. **MargheritaPizzaTest.java** (24 теста)
   - Тестирование всех размеров (SMALL, MEDIUM, LARGE, EXTRA_LARGE)
   - Расчет цены для каждого размера
   - Проверка ингредиентов
   - Калории
   - Свойства (вегетарианская, итальянская)

9. **PepperoniPizzaTest.java** (24 теста)
   - Аналогично MargheritaPizza
   - Проверка остроты
   - Калорийность

10. **VeggiePizzaTest.java** (24 теста)
    - Вегетарианские свойства
    - Здоровая пища
    - Низкая калорийность

11. **DrinkTest.java** (23 теста)
    - Создание напитков
    - Объем
    - Температура
    - Газированность

12. **DessertTest.java** (19 тестов)
    - Вес и калории
    - Содержание сахара
    - Орехи

#### Payment Tests (3 файла, 56 тестов)
13. **CashPaymentTest.java** (15 тестов)
    - Оплата наличными
    - Расчет сдачи
    - Недостаточная сумма
    - Возврат средств

14. **CardPaymentTest.java** (20 тестов)
    - Оплата картой
    - Валидация номера карты
    - PIN-код
    - Маскировка номера карты

15. **OnlinePaymentTest.java** (21 тест)
    - Онлайн платежи
    - Различные шлюзы (PayPal, Stripe, Square)
    - Коды подтверждения
    - Валидация email

#### User Tests (4 файла, 102 теста)
16. **ChefTest.java** (26 тестов)
    - Работа повара
    - Специализация
    - Уровень навыков
    - Приготовление пицц

17. **WaiterTest.java** (25 тестов)
    - Обслуживание столиков
    - Чаевые
    - Текущие заказы
    - Занятость

18. **DeliveryDriverTest.java** (25 тестов)
    - Доставка заказов
    - Транспорт
    - Доступность
    - Бонусы за доставку

19. **ManagerTest.java** (26 тестов)
    - Управление сотрудниками
    - Расчет зарплат
    - Оценка производительности
    - Управление отделами

#### Other Model Tests (3 файла, 60 тестов)
20. **KitchenTest.java** (17 тестов)
    - Управление поварами
    - Приготовление заказов
    - Максимальная вместимость
    - Активные заказы

21. **InventoryTest.java** (22 теста)
    - Управление запасами
    - Добавление/удаление ингредиентов
    - Проверка доступности
    - Низкий уровень запасов
    - Исключение OutOfStockException

22. **MenuTest.java** (21 тест)
    - Добавление/удаление продуктов
    - Фильтрация по категориям
    - Поиск по имени
    - Диапазон цен

### 2. Service Tests (3 файла, 47 тестов)

23. **OrderServiceTest.java** (16 тестов)
    - Создание заказов
    - Получение заказов
    - Отмена заказов
    - Обновление статусов
    - OrderNotFoundException

24. **PaymentServiceTest.java** (13 тестов)
    - Обработка платежей
    - Возврат средств
    - Расчет налогов
    - Сервисные сборы
    - InvalidPaymentException

25. **DeliveryServiceTest.java** (18 тестов)
    - Назначение водителей
    - Планирование доставки
    - Валидация адресов
    - Активные доставки
    - InvalidDeliveryAddressException

### 3. Factory Tests (3 файла, 66 тестов)

26. **PizzaFactoryTest.java** (21 тест)
    - Создание всех типов пицц (MARGHERITA, PEPPERONI, VEGGIE, MEAT_LOVERS, CUSTOM)
    - Все размеры
    - Русские и английские названия
    - InvalidPizzaSizeException

27. **IngredientFactoryTest.java** (25 тестов)
    - Создание сыра (MOZZARELLA, PARMESAN, CHEDDAR)
    - Создание мяса
    - Создание овощей
    - Создание соусов
    - Создание теста

28. **PaymentFactoryTest.java** (20 тестов)
    - Создание платежей CASH, CARD, ONLINE
    - Генерация ID транзакций
    - Валидация сумм

### 4. Strategy Tests (3 файла, 52 теста)

29. **StandardPricingStrategyTest.java** (13 тестов)
    - Стандартное ценообразование
    - Базовая цена + доставка
    - Различные суммы заказов

30. **DiscountPricingStrategyTest.java** (19 тестов)
    - Ценообразование со скидками (10%, 20%, 50%, 100%)
    - Расчет финальной цены
    - Валидация скидок

31. **PremiumPricingStrategyTest.java** (20 тестов)
    - Премиум ценообразование
    - Сервисные сборы
    - Сравнение со стандартным

### 5. Util Tests (4 файла, 92 теста)

32. **PriceCalculatorTest.java** (26 тестов)
    - Расчет с налогами
    - Расчет со скидками
    - Расчет чаевых
    - Округление
    - Форматирование цен

33. **OrderValidatorTest.java** (21 тест)
    - Валидация заказов
    - Минимальная сумма заказа
    - Валидация адресов
    - Валидация товаров

34. **ReportGeneratorTest.java** (19 тестов)
    - Генерация отчетов о продажах
    - Отчеты по сотрудникам
    - Отчеты по инвентарю
    - Агрегация данных

35. **IdGeneratorTest.java** (26 тестов)
    - Генерация ID заказов
    - Генерация ID клиентов
    - Уникальность ID
    - Формат ID
    - Потокобезопасность

### 6. Exception Tests (1 файл, 38 тестов)

36. **ExceptionsTest.java** (38 тестов)
    - InsufficientIngredientsException (3 теста)
    - InvalidPizzaSizeException (2 теста)
    - InvalidPaymentException (3 теста)
    - OrderNotFoundException (2 теста)
    - EmployeeNotFoundException (2 теста)
    - CustomerNotFoundException (2 теста)
    - InvalidPriceException (3 теста)
    - InvalidDiscountException (3 теста)
    - DuplicateOrderException (2 теста)
    - OutOfStockException (3 теста)
    - InvalidAuthenticationException (3 теста)
    - InvalidDeliveryAddressException (3 теста)
    - Общие тесты на наследование (2 теста)
    - Тесты на создание экземпляров (2 теста)

---

## Покрытие кода

### Ожидаемое покрытие

Тесты покрывают следующие компоненты:

#### Полное покрытие (>95%):
- ✅ Все model классы (Order, OrderItem, Customer, Product, etc.)
- ✅ Все factory классы (PizzaFactory, IngredientFactory, PaymentFactory)
- ✅ Все strategy классы (StandardPricing, DiscountPricing, PremiumPricing)
- ✅ Все util классы (PriceCalculator, OrderValidator, ReportGenerator, IdGenerator)
- ✅ Все 12 персональных исключений

#### Высокое покрытие (85-95%):
- ✅ Service классы (OrderService, PaymentService, DeliveryService)
- ✅ Kitchen и Inventory
- ✅ Menu
- ✅ Payment иерархия (CashPayment, CardPayment, OnlinePayment)

#### Исключено из покрытия:
- ❌ Main.class (UI класс, исключен в pom.xml)

### Настройка JaCoCo

В `pom.xml` настроено:
- Минимальное покрытие инструкций: **85%**
- Минимальное покрытие веток: **85%**
- Исключения: `com/pizzeria/Main.class`

---

## Запуск тестов

### Требования
- Java 11 или выше
- Maven 3.6+
- Интернет-соединение (для первого запуска, загрузки зависимостей)

### Команды Maven

#### 1. Компиляция проекта
```bash
mvn clean compile
```

#### 2. Запуск всех тестов
```bash
mvn test
```

#### 3. Запуск тестов с отчетом о покрытии
```bash
mvn clean test jacoco:report
```

#### 4. Запуск тестов с проверкой минимального покрытия
```bash
mvn clean verify
```

#### 5. Запуск конкретного тестового класса
```bash
mvn test -Dtest=OrderTest
```

#### 6. Запуск конкретного тестового метода
```bash
mvn test -Dtest=OrderTest#shouldCreateOrderWithValidCustomer
```

### Просмотр отчета о покрытии

После выполнения `mvn test jacoco:report`, отчет будет доступен по адресу:
```
target/site/jacoco/index.html
```

Откройте файл в браузере для просмотра детального отчета о покрытии кода.

---

## Структура тестовых файлов

```
src/test/java/com/pizzeria/
├── model/
│   ├── OrderTest.java
│   ├── OrderItemTest.java
│   ├── CustomerTest.java
│   ├── LoyaltyCardTest.java
│   ├── AddressTest.java
│   ├── EmailTest.java
│   ├── PhoneNumberTest.java
│   ├── KitchenTest.java
│   ├── InventoryTest.java
│   ├── MenuTest.java
│   ├── products/
│   │   ├── MargheritaPizzaTest.java
│   │   ├── PepperoniPizzaTest.java
│   │   ├── VeggiePizzaTest.java
│   │   ├── DrinkTest.java
│   │   └── DessertTest.java
│   ├── payment/
│   │   ├── CashPaymentTest.java
│   │   ├── CardPaymentTest.java
│   │   └── OnlinePaymentTest.java
│   └── users/
│       ├── ChefTest.java
│       ├── WaiterTest.java
│       ├── DeliveryDriverTest.java
│       └── ManagerTest.java
├── service/
│   ├── OrderServiceTest.java
│   ├── PaymentServiceTest.java
│   └── DeliveryServiceTest.java
├── factory/
│   ├── PizzaFactoryTest.java
│   ├── IngredientFactoryTest.java
│   └── PaymentFactoryTest.java
├── strategy/
│   ├── StandardPricingStrategyTest.java
│   ├── DiscountPricingStrategyTest.java
│   └── PremiumPricingStrategyTest.java
├── util/
│   ├── PriceCalculatorTest.java
│   ├── OrderValidatorTest.java
│   ├── ReportGeneratorTest.java
│   └── IdGeneratorTest.java
└── exceptions/
    └── ExceptionsTest.java
```

---

## Особенности тестов

### 1. JUnit 5 Annotations
Все тесты используют современные аннотации JUnit 5:
- `@Test` - помечает тестовый метод
- `@BeforeEach` - выполняется перед каждым тестом
- `@DisplayName` - читаемое описание теста
- `@ExtendWith(MockitoExtension.class)` - для использования Mockito

### 2. Assertions
Используются разнообразные утверждения:
- `assertEquals()` - сравнение значений
- `assertNotNull()` - проверка на null
- `assertTrue()` / `assertFalse()` - булевы проверки
- `assertThrows()` - проверка исключений
- `assertDoesNotThrow()` - проверка отсутствия исключений

### 3. Mockito
Для изоляции тестов используются моки:
- `@Mock` - создание mock объектов
- `@InjectMocks` - внедрение моков
- `when().thenReturn()` - настройка поведения моков
- `verify()` - проверка вызовов методов

### 4. Test Data
- Тестовые данные создаются в `@BeforeEach`
- Используются реалистичные данные
- Тестируются граничные случаи

### 5. Exception Testing
- Проверка выбрасывания правильных исключений
- Проверка сообщений об ошибках
- Тестирование всех 12 персональных исключений

---

## Зависимости

### JUnit 5
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.10.1</version>
    <scope>test</scope>
</dependency>
```

### Mockito
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.8.0</version>
    <scope>test</scope>
</dependency>
```

### AssertJ (опционально)
```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.24.2</version>
    <scope>test</scope>
</dependency>
```

### JaCoCo
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
</plugin>
```

---

## Проблемы и решения

### Проблема: Maven не может загрузить зависимости
**Решение**: Убедитесь, что у вас есть интернет-соединение. При первом запуске Maven загружает все зависимости.

### Проблема: Тесты падают из-за кодировки
**Решение**: В pom.xml указана UTF-8 кодировка:
```xml
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```

### Проблема: OutOfMemoryError при запуске тестов
**Решение**: Увеличьте память для Maven:
```bash
export MAVEN_OPTS="-Xmx1024m"
mvn test
```

---

## Метрики качества

### Текущее состояние
- ✅ **657 тестов** в 36 файлах
- ✅ Все основные компоненты покрыты
- ✅ Тесты на happy path и error cases
- ✅ Все 12 исключений протестированы
- ✅ Использование Mockito для изоляции
- ✅ Понятные имена тестов с @DisplayName

### Цели
- 🎯 Покрытие кода >85%
- 🎯 Все тесты проходят успешно
- 🎯 Нет flaky тестов
- 🎯 Быстрое выполнение (<2 минут)

---

## Следующие шаги

1. ✅ Создать Maven конфигурацию
2. ✅ Создать тесты для model классов
3. ✅ Создать тесты для service классов
4. ✅ Создать тесты для factory классов
5. ✅ Создать тесты для strategy классов
6. ✅ Создать тесты для util классов
7. ✅ Создать тесты для exceptions
8. ⏳ Запустить тесты при наличии интернета
9. ⏳ Проверить покрытие кода
10. ⏳ Добавить недостающие тесты при необходимости
11. ✅ Закоммитить и запушить тесты

---

## Авторы
Система тестирования создана для лабораторной работы по курсу "Проектирование программного обеспечения интеллектуальных систем (ООП)".

**Дата создания**: 2025-11-18
**Версия**: 1.0
**Статус**: ✅ Готово к запуску
