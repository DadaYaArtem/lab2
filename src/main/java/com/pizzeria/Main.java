package com.pizzeria;

import com.pizzeria.enums.*;
import com.pizzeria.exceptions.*;
import com.pizzeria.factory.*;
import com.pizzeria.model.*;
import com.pizzeria.model.ingredients.*;
import com.pizzeria.model.payment.*;
import com.pizzeria.model.products.*;
import com.pizzeria.model.users.*;
import com.pizzeria.service.*;
import com.pizzeria.util.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Интерактивная система управления пиццерией
 * Полная реализация ООП с применением SOLID и GRASP принципов
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Pizzeria pizzeria;
    private static Inventory inventory;
    private static Kitchen kitchen;
    private static Menu menu;
    private static OrderService orderService;
    private static PaymentService paymentService;
    private static DeliveryService deliveryService;
    private static PizzaFactory pizzaFactory;
    private static IngredientFactory ingredientFactory;
    private static PaymentFactory paymentFactory;
    private static List<Customer> customers;
    private static Customer currentCustomer;
    private static Order currentOrder;

    public static void main(String[] args) {
        initializeSystem();
        runMainMenu();
    }

    /**
     * Инициализация системы
     */
    private static void initializeSystem() {
        try {
            System.out.println("=================================================");
            System.out.println("   СИСТЕМА УПРАВЛЕНИЯ ПИЦЦЕРИЕЙ");
            System.out.println("=================================================\n");
            System.out.println("Инициализация системы...\n");

            // Создание пиццерии
            Address pizzeriaAddress = new Address("Пушкина", "10", "Москва", "101000");
            pizzeriaAddress.setLatitude(55.7558);
            pizzeriaAddress.setLongitude(37.6173);
            pizzeria = new Pizzeria("Bella Italia", pizzeriaAddress);
            pizzeria.setWorkingHours("10:00 - 23:00");
            pizzeria.open();

            // Инициализация компонентов
            inventory = new Inventory();
            kitchen = new Kitchen(inventory);
            pizzeria.setKitchen(kitchen);
            menu = new Menu("Основное меню");
            pizzeria.setMenu(menu);

            // Инициализация сервисов и фабрик
            orderService = new OrderService();
            paymentService = new PaymentService();
            deliveryService = new DeliveryService();
            pizzaFactory = new PizzaFactory();
            ingredientFactory = new IngredientFactory();
            paymentFactory = new PaymentFactory();
            customers = new ArrayList<>();

            // Добавление ингредиентов
            initializeInventory();

            // Создание сотрудников
            initializeEmployees();

            // Создание меню
            initializeMenu();

            System.out.println("✓ Система успешно инициализирована!\n");

        } catch (Exception e) {
            System.err.println("Ошибка инициализации: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Инициализация инвентаря
     */
    private static void initializeInventory() throws InvalidPriceException {
        Cheese mozzarella = ingredientFactory.createCheese("Моцарелла");
        Cheese parmesan = ingredientFactory.createCheese("Пармезан");
        Meat pepperoni = ingredientFactory.createMeat("Пепперони");
        Meat chicken = ingredientFactory.createMeat("Курица");
        Meat beef = ingredientFactory.createMeat("Говядина");
        Vegetable tomatoes = ingredientFactory.createVegetable("Помидоры");
        Vegetable mushrooms = ingredientFactory.createVegetable("Грибы");
        Vegetable onion = ingredientFactory.createVegetable("Лук");
        Vegetable pepper = ingredientFactory.createVegetable("Перец");
        Sauce tomatoSauce = ingredientFactory.createSauce("Томатный");
        Sauce creamSauce = ingredientFactory.createSauce("Сливочный");
        Dough thinDough = ingredientFactory.createDough("Тонкое");
        Dough thickDough = ingredientFactory.createDough("Толстое");

        inventory.addIngredient(mozzarella);
        inventory.addIngredient(parmesan);
        inventory.addIngredient(pepperoni);
        inventory.addIngredient(chicken);
        inventory.addIngredient(beef);
        inventory.addIngredient(tomatoes);
        inventory.addIngredient(mushrooms);
        inventory.addIngredient(onion);
        inventory.addIngredient(pepper);
        inventory.addIngredient(tomatoSauce);
        inventory.addIngredient(creamSauce);
        inventory.addIngredient(thinDough);
        inventory.addIngredient(thickDough);
    }

    /**
     * Создание сотрудников
     */
    private static void initializeEmployees() {
        Manager manager = new Manager("MGR001", "Иван", "Петров", 80000);
        Chef chef1 = new Chef("CHF001", "Марио", "Росси", 60000);
        chef1.setSpecialty("Итальянская пицца");
        chef1.setExperienceYears(5);
        Chef chef2 = new Chef("CHF002", "Луиджи", "Бьянки", 55000);
        chef2.setExperienceYears(3);
        Waiter waiter = new Waiter("WTR001", "Анна", "Смирнова", 35000);
        DeliveryDriver driver = new DeliveryDriver("DRV001", "Петр", "Иванов", 40000);
        driver.setVehicleType("Мотоцикл");

        pizzeria.setManager(manager);
        pizzeria.hireEmployee(manager);
        pizzeria.hireEmployee(chef1);
        pizzeria.hireEmployee(chef2);
        pizzeria.hireEmployee(waiter);
        pizzeria.hireEmployee(driver);
        kitchen.addChef(chef1);
        kitchen.addChef(chef2);
        deliveryService.addDriver(driver);
    }

    /**
     * Инициализация меню
     */
    private static void initializeMenu() throws InvalidPriceException, InvalidPizzaSizeException {
        // Пиццы
        menu.addProduct(pizzaFactory.createPizza("маргарита", PizzaSize.SMALL));
        menu.addProduct(pizzaFactory.createPizza("маргарита", PizzaSize.MEDIUM));
        menu.addProduct(pizzaFactory.createPizza("маргарита", PizzaSize.LARGE));
        menu.addProduct(pizzaFactory.createPizza("пепперони", PizzaSize.SMALL));
        menu.addProduct(pizzaFactory.createPizza("пепперони", PizzaSize.MEDIUM));
        menu.addProduct(pizzaFactory.createPizza("пепперони", PizzaSize.LARGE));
        menu.addProduct(pizzaFactory.createPizza("вегетарианская", PizzaSize.MEDIUM));
        menu.addProduct(pizzaFactory.createPizza("мясная", PizzaSize.LARGE));
        menu.addProduct(pizzaFactory.createPizza("пользовательская", PizzaSize.MEDIUM));

        // Напитки
        Drink cola = new Drink("Кока-кола 0.5л", 100.0, 500);
        cola.setCarbonated(true);
        Drink juice = new Drink("Апельсиновый сок 0.3л", 120.0, 300);
        Drink water = new Drink("Вода 0.5л", 50.0, 500);

        menu.addProduct(cola);
        menu.addProduct(juice);
        menu.addProduct(water);

        // Десерты
        Dessert tiramisu = new Dessert("Тирамису", 250.0, 150);
        Dessert cheesecake = new Dessert("Чизкейк", 200.0, 120);

        menu.addProduct(tiramisu);
        menu.addProduct(cheesecake);

        // Закуски
        Appetizer wings = new Appetizer("Куриные крылышки", 300.0);
        wings.setHot(true);
        Appetizer salad = new Appetizer("Цезарь", 250.0);

        menu.addProduct(wings);
        menu.addProduct(salad);
    }

    /**
     * Главное меню
     */
    private static void runMainMenu() {
        while (true) {
            try {
                System.out.println("\n=================================================");
                System.out.println("           ГЛАВНОЕ МЕНЮ");
                System.out.println("=================================================");
                System.out.println("1. Регистрация/Вход клиента");
                System.out.println("2. Просмотр меню");
                System.out.println("3. Создать заказ");
                System.out.println("4. Просмотреть текущий заказ");
                System.out.println("5. Оплатить заказ");
                System.out.println("6. Управление пиццерией (для менеджера)");
                System.out.println("7. Статистика");
                System.out.println("0. Выход");
                System.out.println("=================================================");
                System.out.print("Выберите опцию: ");

                int choice = getIntInput();

                switch (choice) {
                    case 1:
                        customerMenu();
                        break;
                    case 2:
                        viewMenu();
                        break;
                    case 3:
                        createOrder();
                        break;
                    case 4:
                        viewCurrentOrder();
                        break;
                    case 5:
                        payOrder();
                        break;
                    case 6:
                        managementMenu();
                        break;
                    case 7:
                        showStatistics();
                        break;
                    case 0:
                        exitSystem();
                        return;
                    default:
                        System.out.println("Неверный выбор! Попробуйте снова.");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    /**
     * Меню клиента
     */
    private static void customerMenu() {
        System.out.println("\n--- РЕГИСТРАЦИЯ/ВХОД КЛИЕНТА ---");
        System.out.println("1. Зарегистрировать нового клиента");
        System.out.println("2. Войти как существующий клиент");
        System.out.print("Выберите опцию: ");

        int choice = getIntInput();

        if (choice == 1) {
            registerCustomer();
        } else if (choice == 2) {
            loginCustomer();
        }
    }

    /**
     * Регистрация клиента
     */
    private static void registerCustomer() {
        try {
            System.out.print("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Введите фамилию: ");
            String lastName = scanner.nextLine();

            String customerId = "CUST" + (customers.size() + 1);
            Customer customer = new Customer(customerId, firstName, lastName);

            System.out.print("Введите телефон (например, +79991234567): ");
            String phone = scanner.nextLine();
            customer.setPhoneNumber(new PhoneNumber(phone));

            System.out.print("Введите email: ");
            String emailInput = scanner.nextLine();
            String[] emailParts = emailInput.split("@");
            if (emailParts.length == 2) {
                customer.setEmail(new Email(emailParts[0], emailParts[1]));
            }

            // Создание карты лояльности
            LoyaltyCard loyaltyCard = new LoyaltyCard("LOYAL-" + customerId);
            customer.setLoyaltyCard(loyaltyCard);

            customers.add(customer);
            currentCustomer = customer;

            System.out.println("\n✓ Клиент успешно зарегистрирован!");
            System.out.println("ID: " + customer.getId());
            System.out.println("Карта лояльности: " + loyaltyCard.getCardNumber());
            System.out.println("Уровень: " + loyaltyCard.getTier());

        } catch (Exception e) {
            System.err.println("Ошибка регистрации: " + e.getMessage());
        }
    }

    /**
     * Вход клиента
     */
    private static void loginCustomer() {
        if (customers.isEmpty()) {
            System.out.println("Нет зарегистрированных клиентов!");
            return;
        }

        System.out.println("\n--- СПИСОК КЛИЕНТОВ ---");
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.printf("%d. %s (ID: %s)\n", i + 1, c.getFullName(), c.getId());
        }

        System.out.print("Выберите номер клиента: ");
        int choice = getIntInput() - 1;

        if (choice >= 0 && choice < customers.size()) {
            currentCustomer = customers.get(choice);
            System.out.println("✓ Вы вошли как: " + currentCustomer.getFullName());
        } else {
            System.out.println("Неверный выбор!");
        }
    }

    /**
     * Просмотр меню
     */
    private static void viewMenu() {
        System.out.println("\n========================================");
        System.out.println("           МЕНЮ ПИЦЦЕРИИ");
        System.out.println("========================================");

        List<Product> products = menu.getAvailableProducts();

        System.out.println("\n--- ПИЦЦЫ ---");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p instanceof Pizza) {
                Pizza pizza = (Pizza) p;
                System.out.printf("%d. %s (%s) - %.2f руб. [%d калорий]\n",
                    i + 1, pizza.getName(), pizza.getSize().getDisplayName(),
                    pizza.getPrice(), pizza.getCalories());
            }
        }

        System.out.println("\n--- НАПИТКИ ---");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p instanceof Drink) {
                System.out.printf("%d. %s - %.2f руб.\n",
                    i + 1, p.getName(), p.getPrice());
            }
        }

        System.out.println("\n--- ДЕСЕРТЫ ---");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p instanceof Dessert) {
                System.out.printf("%d. %s - %.2f руб.\n",
                    i + 1, p.getName(), p.getPrice());
            }
        }

        System.out.println("\n--- ЗАКУСКИ ---");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p instanceof Appetizer) {
                System.out.printf("%d. %s - %.2f руб.\n",
                    i + 1, p.getName(), p.getPrice());
            }
        }
        System.out.println("========================================");
    }

    /**
     * Создание заказа
     */
    private static void createOrder() {
        try {
            if (currentCustomer == null) {
                System.out.println("Сначала войдите как клиент!");
                return;
            }

            if (currentOrder != null && !currentOrder.isPaid()) {
                System.out.println("У вас уже есть активный заказ! Сначала оплатите его.");
                return;
            }

            currentOrder = orderService.createOrder(currentCustomer);
            System.out.println("✓ Заказ создан: " + currentOrder.getId());

            addItemsToOrder();

        } catch (Exception e) {
            System.err.println("Ошибка создания заказа: " + e.getMessage());
        }
    }

    /**
     * Добавление товаров в заказ
     */
    private static void addItemsToOrder() {
        List<Product> products = menu.getAvailableProducts();

        while (true) {
            System.out.println("\n--- ДОБАВЛЕНИЕ ТОВАРОВ ---");
            System.out.println("1. Показать меню и добавить товар");
            System.out.println("2. Применить промокод");
            System.out.println("3. Указать адрес доставки");
            System.out.println("0. Завершить добавление");
            System.out.print("Выберите опцию: ");

            int choice = getIntInput();

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    viewMenu();
                    System.out.print("\nВведите номер товара: ");
                    int productIndex = getIntInput() - 1;

                    if (productIndex >= 0 && productIndex < products.size()) {
                        Product product = products.get(productIndex);
                        System.out.print("Введите количество: ");
                        int quantity = getIntInput();

                        currentOrder.addItem(product, quantity);
                        System.out.printf("✓ Добавлено: %s x%d\n", product.getName(), quantity);
                    } else {
                        System.out.println("Неверный номер товара!");
                    }
                    break;

                case 2:
                    applyPromoCode();
                    break;

                case 3:
                    setDeliveryAddress();
                    break;
            }
        }
    }

    /**
     * Применение промокода
     */
    private static void applyPromoCode() {
        try {
            System.out.print("Введите промокод: ");
            String code = scanner.nextLine();

            // Простая проверка промокодов
            double discount = 0;
            if (code.equalsIgnoreCase("PIZZA10")) {
                discount = 10.0;
            } else if (code.equalsIgnoreCase("PIZZA20")) {
                discount = 20.0;
            } else if (code.equalsIgnoreCase("VIP30")) {
                discount = 30.0;
            }

            if (discount > 0) {
                currentOrder.applyDiscount(discount);
                System.out.printf("✓ Промокод применен! Скидка: %.0f%%\n", discount);
            } else {
                System.out.println("✗ Неверный промокод!");
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Установка адреса доставки
     */
    private static void setDeliveryAddress() {
        try {
            System.out.println("\n--- АДРЕС ДОСТАВКИ ---");
            System.out.print("Улица: ");
            String street = scanner.nextLine();
            System.out.print("Дом: ");
            String house = scanner.nextLine();
            System.out.print("Город: ");
            String city = scanner.nextLine();
            System.out.print("Индекс: ");
            String postal = scanner.nextLine();
            System.out.print("Квартира (необязательно): ");
            String apartment = scanner.nextLine();

            Address address = new Address(street, house, city, postal);
            if (!apartment.isEmpty()) {
                address.setApartmentNumber(apartment);
            }

            currentOrder.setDeliveryAddress(address);
            System.out.println("✓ Адрес доставки установлен");
            System.out.printf("Стоимость доставки: %.2f руб.\n", currentOrder.calculateDeliveryCost());
            System.out.printf("Время доставки: ~%d мин\n", currentOrder.calculateDeliveryTime());

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Просмотр текущего заказа
     */
    private static void viewCurrentOrder() {
        if (currentOrder == null) {
            System.out.println("Нет активного заказа!");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("        ТЕКУЩИЙ ЗАКАЗ #" + currentOrder.getId());
        System.out.println("========================================");
        System.out.println("Клиент: " + currentOrder.getCustomer().getFullName());
        System.out.println("Статус: " + currentOrder.getStatus().getDisplayName());
        System.out.println("\nТовары:");

        for (OrderItem item : currentOrder.getItems()) {
            System.out.printf("  %s x%d - %.2f руб.\n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getTotalPrice());
        }

        System.out.println("\n----------------------------------------");
        System.out.printf("Сумма товаров: %.2f руб.\n", currentOrder.getPrice());

        if (currentOrder.getDiscountPercentage() > 0) {
            System.out.printf("Скидка: %.0f%%\n", currentOrder.getDiscountPercentage());
        }

        if (currentOrder.getDeliveryAddress() != null) {
            System.out.printf("Доставка: %.2f руб.\n", currentOrder.calculateDeliveryCost());
        }

        System.out.printf("ИТОГО: %.2f руб.\n", currentOrder.getFinalPrice());
        System.out.println("========================================");
    }

    /**
     * Оплата заказа
     */
    private static void payOrder() {
        try {
            if (currentOrder == null || currentOrder.getItems().isEmpty()) {
                System.out.println("Нет товаров в заказе!");
                return;
            }

            if (currentOrder.isPaid()) {
                System.out.println("Заказ уже оплачен!");
                return;
            }

            viewCurrentOrder();

            System.out.println("\n--- ВЫБОР МЕТОДА ОПЛАТЫ ---");
            System.out.println("1. Наличные");
            System.out.println("2. Банковская карта");
            System.out.println("3. Онлайн оплата");
            System.out.print("Выберите метод: ");

            int choice = getIntInput();
            PaymentMethod method;

            switch (choice) {
                case 1: method = PaymentMethod.CASH; break;
                case 2: method = PaymentMethod.CARD; break;
                case 3: method = PaymentMethod.ONLINE; break;
                default:
                    System.out.println("Неверный выбор!");
                    return;
            }

            double totalAmount = currentOrder.getFinalPrice();
            Payment payment = null;

            if (method == PaymentMethod.CASH) {
                CashPayment cashPayment = paymentFactory.createCashPayment(totalAmount);
                System.out.print("Внесено наличными: ");
                double received = getDoubleInput();
                cashPayment.setAmountReceived(received);
                payment = cashPayment;
            } else if (method == PaymentMethod.CARD) {
                System.out.print("Номер карты (16 цифр): ");
                String cardNumber = scanner.nextLine();
                payment = paymentFactory.createCardPayment(totalAmount, cardNumber);
            } else {
                System.out.print("Email для чека: ");
                String email = scanner.nextLine();
                payment = paymentFactory.createOnlinePayment(totalAmount, email);
            }

            Receipt receipt = paymentService.processPayment(currentOrder, payment);

            System.out.println("\n✓ ОПЛАТА УСПЕШНА!");
            System.out.println("Чек сохранен: " + receipt.getReceiptNumber());

            // Добавление баллов лояльности
            if (currentCustomer.getLoyaltyCard() != null) {
                int points = (int)(totalAmount / 10); // 10 руб = 1 балл
                currentCustomer.getLoyaltyCard().addPoints(points);
                System.out.printf("Начислено бонусных баллов: %d\n", points);
            }

            // Обновление статуса и добавление в историю
            currentOrder.updateStatus(OrderStatus.CONFIRMED);
            pizzeria.addOrder(currentOrder);

            // Предложение доставки
            if (currentOrder.getDeliveryAddress() != null) {
                organizeDelivery();
            }

        } catch (Exception e) {
            System.err.println("Ошибка оплаты: " + e.getMessage());
        }
    }

    /**
     * Организация доставки
     */
    private static void organizeDelivery() {
        try {
            DeliveryDriver driver = deliveryService.findAvailableDriver();
            if (driver != null) {
                currentOrder.updateStatus(OrderStatus.IN_DELIVERY);
                DeliveryInfo delivery = deliveryService.scheduleDelivery(currentOrder, driver);

                System.out.println("\n--- ДОСТАВКА ---");
                System.out.println("Водитель: " + driver.getFullName());
                System.out.println("Транспорт: " + driver.getVehicleType());
                System.out.println("Трек-номер: " + delivery.getTrackingNumber());
                System.out.printf("Ожидаемое время: ~%d мин\n", delivery.getEstimatedTime());
            } else {
                System.out.println("Нет доступных водителей. Заказ будет доставлен позже.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка организации доставки: " + e.getMessage());
        }
    }

    /**
     * Меню управления
     */
    private static void managementMenu() {
        System.out.println("\n--- УПРАВЛЕНИЕ ПИЦЦЕРИЕЙ ---");
        System.out.println("1. Просмотр сотрудников");
        System.out.println("2. Проверка инвентаря");
        System.out.println("3. Пополнить запасы");
        System.out.println("4. История заказов");
        System.out.println("0. Назад");
        System.out.print("Выберите опцию: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                viewEmployees();
                break;
            case 2:
                checkInventory();
                break;
            case 3:
                restockInventory();
                break;
            case 4:
                viewOrderHistory();
                break;
        }
    }

    /**
     * Просмотр сотрудников
     */
    private static void viewEmployees() {
        System.out.println("\n--- СПИСОК СОТРУДНИКОВ ---");
        List<Employee> employees = pizzeria.getActiveEmployees();

        for (Employee emp : employees) {
            System.out.printf("%s - %s (Зарплата: %.2f руб.)\n",
                emp.getId(), emp.getFullName(), emp.getSalary());
        }
    }

    /**
     * Проверка инвентаря
     */
    private static void checkInventory() {
        System.out.println("\n--- СОСТОЯНИЕ ИНВЕНТАРЯ ---");

        for (Ingredient ingredient : inventory.getIngredients().values()) {
            System.out.printf("%s: %d %s (Цена: %.2f руб.)\n",
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getPricePerUnit());
        }

        System.out.printf("\nОбщая стоимость: %.2f руб.\n",
            inventory.calculateTotalInventoryValue());

        // Низкие запасы
        var lowStock = inventory.getLowStockItems();
        if (!lowStock.isEmpty()) {
            System.out.println("\n⚠ НИЗКИЕ ЗАПАСЫ:");
            for (var entry : lowStock.entrySet()) {
                System.out.printf("  %s: %d\n", entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Пополнение запасов
     */
    private static void restockInventory() {
        try {
            System.out.println("\n--- ПОПОЛНЕНИЕ ЗАПАСОВ ---");
            System.out.print("Название ингредиента: ");
            String name = scanner.nextLine();
            System.out.print("Количество: ");
            int quantity = getIntInput();

            inventory.restockIngredient(name, quantity);
            System.out.println("✓ Запасы пополнены!");

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * История заказов
     */
    private static void viewOrderHistory() {
        System.out.println("\n--- ИСТОРИЯ ЗАКАЗОВ ---");
        List<Order> orders = pizzeria.getOrderHistory();

        if (orders.isEmpty()) {
            System.out.println("Нет заказов");
            return;
        }

        for (Order order : orders) {
            System.out.printf("Заказ #%s - %s - %.2f руб. (Статус: %s)\n",
                order.getId(),
                order.getCustomer().getFullName(),
                order.getFinalPrice(),
                order.getStatus().getDisplayName());
        }
    }

    /**
     * Статистика
     */
    private static void showStatistics() {
        System.out.println("\n========================================");
        System.out.println("          СТАТИСТИКА ПИЦЦЕРИИ");
        System.out.println("========================================");
        System.out.println("Название: " + pizzeria.getName());
        System.out.println("Адрес: " + pizzeria.getAddress());
        System.out.println("Часы работы: " + pizzeria.getWorkingHours());
        System.out.println("Статус: " + (pizzeria.isOpen() ? "Открыта" : "Закрыта"));
        System.out.println("----------------------------------------");
        System.out.println("Сотрудников: " + pizzeria.getActiveEmployees().size());
        System.out.println("Клиентов: " + customers.size());
        System.out.println("Всего заказов: " + pizzeria.getTotalOrders());
        System.out.printf("Выручка: %.2f руб.\n", pizzeria.calculateDailyRevenue());
        System.out.printf("Стоимость инвентаря: %.2f руб.\n",
            inventory.calculateTotalInventoryValue());
        System.out.println("========================================");
    }

    /**
     * Выход из системы
     */
    private static void exitSystem() {
        pizzeria.close();
        System.out.println("\nСпасибо за использование системы!");
        System.out.println("До свидания!");
        scanner.close();
    }

    /**
     * Получение целого числа от пользователя
     */
    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    /**
     * Получение дробного числа от пользователя
     */
    private static double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }
}
