package com.pizzeria.exceptions;

import com.pizzeria.enums.OrderStatus;
import com.pizzeria.enums.PizzaSize;
import com.pizzeria.model.*;
import com.pizzeria.model.payment.CashPayment;
import com.pizzeria.model.products.MargheritaPizza;
import com.pizzeria.model.products.Product;
import com.pizzeria.model.users.Customer;
import com.pizzeria.service.OrderService;
import com.pizzeria.service.PaymentService;
import com.pizzeria.service.DeliveryService;
import com.pizzeria.model.users.DeliveryDriver;
import com.pizzeria.model.ingredients.Cheese;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Custom Exceptions Tests")
class ExceptionsTest {

    private OrderService orderService;
    private PaymentService paymentService;
    private DeliveryService deliveryService;
    private Inventory inventory;
    private Kitchen kitchen;
    private Customer customer;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        paymentService = new PaymentService();
        deliveryService = new DeliveryService();
        inventory = new Inventory();
        kitchen = new Kitchen();
        customer = new Customer("Тест", "Тестов",
                              new Email("test@test.com"),
                              new PhoneNumber("+79991234567"));
    }

    // 1. InsufficientIngredientsException Tests
    @Test
    @DisplayName("Should throw InsufficientIngredientsException when ingredient not available")
    void shouldThrowInsufficientIngredientsException() {
        Cheese cheese = new Cheese("Моцарелла", 5.0);
        inventory.addIngredient(cheese, 10);

        InsufficientIngredientsException exception = assertThrows(
            InsufficientIngredientsException.class,
            () -> inventory.useIngredient(cheese, 20)
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Недостаточно"));
    }

    @Test
    @DisplayName("Should handle InsufficientIngredientsException with custom message")
    void shouldHandleInsufficientIngredientsExceptionWithMessage() {
        InsufficientIngredientsException exception =
            new InsufficientIngredientsException("Недостаточно ингредиентов для пиццы");

        assertEquals("Недостаточно ингредиентов для пиццы", exception.getMessage());
        assertNotNull(exception.toString());
    }

    // 2. InvalidPizzaSizeException Tests
    @Test
    @DisplayName("Should throw InvalidPizzaSizeException for null size")
    void shouldThrowInvalidPizzaSizeException() {
        assertThrows(InvalidPizzaSizeException.class,
            () -> new MargheritaPizza(null));
    }

    @Test
    @DisplayName("Should handle InvalidPizzaSizeException with custom message")
    void shouldHandleInvalidPizzaSizeExceptionWithMessage() {
        InvalidPizzaSizeException exception =
            new InvalidPizzaSizeException("Неверный размер пиццы");

        assertEquals("Неверный размер пиццы", exception.getMessage());
    }

    // 3. InvalidPaymentException Tests
    @Test
    @DisplayName("Should throw InvalidPaymentException for insufficient amount")
    void shouldThrowInvalidPaymentException() {
        CashPayment payment = new CashPayment(50.0, 100.0);

        InvalidPaymentException exception = assertThrows(
            InvalidPaymentException.class,
            () -> payment.processPayment()
        );

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle InvalidPaymentException with custom message")
    void shouldHandleInvalidPaymentExceptionWithMessage() {
        InvalidPaymentException exception =
            new InvalidPaymentException("Недостаточно средств для оплаты");

        assertEquals("Недостаточно средств для оплаты", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for negative amount")
    void shouldThrowInvalidPaymentExceptionForNegative() {
        assertThrows(InvalidPaymentException.class,
            () -> new CashPayment(-100.0, 50.0));
    }

    // 4. OrderNotFoundException Tests
    @Test
    @DisplayName("Should throw OrderNotFoundException when order doesn't exist")
    void shouldThrowOrderNotFoundException() {
        OrderNotFoundException exception = assertThrows(
            OrderNotFoundException.class,
            () -> orderService.getOrder("NON_EXISTENT_ORDER")
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("не найден"));
    }

    @Test
    @DisplayName("Should handle OrderNotFoundException with custom message")
    void shouldHandleOrderNotFoundExceptionWithMessage() {
        OrderNotFoundException exception =
            new OrderNotFoundException("Заказ ORD-999 не найден");

        assertEquals("Заказ ORD-999 не найден", exception.getMessage());
    }

    // 5. EmployeeNotFoundException Tests
    @Test
    @DisplayName("Should throw EmployeeNotFoundException when employee doesn't exist")
    void shouldThrowEmployeeNotFoundException() {
        EmployeeNotFoundException exception =
            new EmployeeNotFoundException("Сотрудник не найден");

        assertNotNull(exception.getMessage());
        assertEquals("Сотрудник не найден", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle EmployeeNotFoundException with ID")
    void shouldHandleEmployeeNotFoundExceptionWithId() {
        EmployeeNotFoundException exception =
            new EmployeeNotFoundException("Сотрудник с ID EMP-001 не найден");

        assertTrue(exception.getMessage().contains("EMP-001"));
    }

    // 6. CustomerNotFoundException Tests
    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer doesn't exist")
    void shouldThrowCustomerNotFoundException() {
        CustomerNotFoundException exception =
            new CustomerNotFoundException("Клиент не найден");

        assertNotNull(exception.getMessage());
        assertEquals("Клиент не найден", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle CustomerNotFoundException with email")
    void shouldHandleCustomerNotFoundExceptionWithEmail() {
        CustomerNotFoundException exception =
            new CustomerNotFoundException("Клиент с email test@test.com не найден");

        assertTrue(exception.getMessage().contains("test@test.com"));
    }

    // 7. InvalidPriceException Tests
    @Test
    @DisplayName("Should throw InvalidPriceException for negative price")
    void shouldThrowInvalidPriceException() {
        assertThrows(InvalidPriceException.class,
            () -> new OrderItem(new MargheritaPizza(PizzaSize.MEDIUM), -1));
    }

    @Test
    @DisplayName("Should handle InvalidPriceException with custom message")
    void shouldHandleInvalidPriceExceptionWithMessage() {
        InvalidPriceException exception =
            new InvalidPriceException("Цена не может быть отрицательной");

        assertEquals("Цена не может быть отрицательной", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidPriceException for zero price")
    void shouldThrowInvalidPriceExceptionForZero() {
        InvalidPriceException exception =
            new InvalidPriceException("Цена должна быть больше нуля");

        assertTrue(exception.getMessage().contains("больше нуля"));
    }

    // 8. InvalidDiscountException Tests
    @Test
    @DisplayName("Should throw InvalidDiscountException for negative discount")
    void shouldThrowInvalidDiscountExceptionForNegative() {
        Order order = new Order("ORD-001", customer);

        assertThrows(InvalidDiscountException.class,
            () -> order.applyDiscount(-10.0));
    }

    @Test
    @DisplayName("Should throw InvalidDiscountException for discount over 100")
    void shouldThrowInvalidDiscountExceptionForOver100() {
        Order order = new Order("ORD-001", customer);

        assertThrows(InvalidDiscountException.class,
            () -> order.applyDiscount(150.0));
    }

    @Test
    @DisplayName("Should handle InvalidDiscountException with custom message")
    void shouldHandleInvalidDiscountExceptionWithMessage() {
        InvalidDiscountException exception =
            new InvalidDiscountException("Скидка должна быть от 0 до 100%");

        assertEquals("Скидка должна быть от 0 до 100%", exception.getMessage());
    }

    // 9. DuplicateOrderException Tests
    @Test
    @DisplayName("Should throw DuplicateOrderException for duplicate order ID")
    void shouldThrowDuplicateOrderException() {
        Order order1 = new Order("ORD-001", customer);
        orderService.createOrder(order1);

        Order order2 = new Order("ORD-001", customer);

        DuplicateOrderException exception = assertThrows(
            DuplicateOrderException.class,
            () -> orderService.createOrder(order2)
        );

        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("уже существует"));
    }

    @Test
    @DisplayName("Should handle DuplicateOrderException with custom message")
    void shouldHandleDuplicateOrderExceptionWithMessage() {
        DuplicateOrderException exception =
            new DuplicateOrderException("Заказ с ID ORD-001 уже существует");

        assertTrue(exception.getMessage().contains("ORD-001"));
    }

    // 10. OutOfStockException Tests
    @Test
    @DisplayName("Should throw OutOfStockException when stock is zero")
    void shouldThrowOutOfStockException() {
        Cheese cheese = new Cheese("Моцарелла", 5.0);
        inventory.addIngredient(cheese, 0);

        OutOfStockException exception = assertThrows(
            OutOfStockException.class,
            () -> inventory.checkStock(cheese)
        );

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle OutOfStockException with custom message")
    void shouldHandleOutOfStockExceptionWithMessage() {
        OutOfStockException exception =
            new OutOfStockException("Товар закончился на складе");

        assertEquals("Товар закончился на складе", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw OutOfStockException with product name")
    void shouldThrowOutOfStockExceptionWithProductName() {
        OutOfStockException exception =
            new OutOfStockException("Продукт 'Моцарелла' закончился");

        assertTrue(exception.getMessage().contains("Моцарелла"));
    }

    // 11. InvalidAuthenticationException Tests
    @Test
    @DisplayName("Should throw InvalidAuthenticationException for wrong password")
    void shouldThrowInvalidAuthenticationException() {
        customer.setPassword("correct_password");

        InvalidAuthenticationException exception =
            new InvalidAuthenticationException("Неверный пароль");

        assertNotNull(exception.getMessage());
        assertEquals("Неверный пароль", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle InvalidAuthenticationException for user not found")
    void shouldHandleInvalidAuthenticationExceptionForUserNotFound() {
        InvalidAuthenticationException exception =
            new InvalidAuthenticationException("Пользователь не найден");

        assertTrue(exception.getMessage().contains("не найден"));
    }

    @Test
    @DisplayName("Should handle InvalidAuthenticationException with email")
    void shouldHandleInvalidAuthenticationExceptionWithEmail() {
        InvalidAuthenticationException exception =
            new InvalidAuthenticationException("Неверные учетные данные для test@test.com");

        assertTrue(exception.getMessage().contains("test@test.com"));
    }

    // 12. InvalidDeliveryAddressException Tests
    @Test
    @DisplayName("Should throw InvalidDeliveryAddressException for null address")
    void shouldThrowInvalidDeliveryAddressExceptionForNull() {
        InvalidDeliveryAddressException exception = assertThrows(
            InvalidDeliveryAddressException.class,
            () -> deliveryService.validateAddress(null)
        );

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should throw InvalidDeliveryAddressException for empty address")
    void shouldThrowInvalidDeliveryAddressExceptionForEmpty() {
        Address emptyAddress = new Address("", "", "", "");

        InvalidDeliveryAddressException exception = assertThrows(
            InvalidDeliveryAddressException.class,
            () -> deliveryService.validateAddress(emptyAddress)
        );

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Should handle InvalidDeliveryAddressException with custom message")
    void shouldHandleInvalidDeliveryAddressExceptionWithMessage() {
        InvalidDeliveryAddressException exception =
            new InvalidDeliveryAddressException("Адрес доставки некорректен");

        assertEquals("Адрес доставки некорректен", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate all exceptions extend Exception")
    void shouldValidateAllExceptionsExtendException() {
        assertTrue(Exception.class.isAssignableFrom(InsufficientIngredientsException.class));
        assertTrue(Exception.class.isAssignableFrom(InvalidPizzaSizeException.class));
        assertTrue(Exception.class.isAssignableFrom(InvalidPaymentException.class));
        assertTrue(Exception.class.isAssignableFrom(OrderNotFoundException.class));
        assertTrue(Exception.class.isAssignableFrom(EmployeeNotFoundException.class));
        assertTrue(Exception.class.isAssignableFrom(CustomerNotFoundException.class));
        assertTrue(Exception.class.isAssignableFrom(InvalidPriceException.class));
        assertTrue(Exception.class.isAssignableFrom(InvalidDiscountException.class));
        assertTrue(Exception.class.isAssignableFrom(DuplicateOrderException.class));
        assertTrue(Exception.class.isAssignableFrom(OutOfStockException.class));
        assertTrue(Exception.class.isAssignableFrom(InvalidAuthenticationException.class));
        assertTrue(Exception.class.isAssignableFrom(InvalidDeliveryAddressException.class));
    }

    @Test
    @DisplayName("Should validate all exceptions can be instantiated")
    void shouldValidateAllExceptionsCanBeInstantiated() {
        assertDoesNotThrow(() -> new InsufficientIngredientsException("test"));
        assertDoesNotThrow(() -> new InvalidPizzaSizeException("test"));
        assertDoesNotThrow(() -> new InvalidPaymentException("test"));
        assertDoesNotThrow(() -> new OrderNotFoundException("test"));
        assertDoesNotThrow(() -> new EmployeeNotFoundException("test"));
        assertDoesNotThrow(() -> new CustomerNotFoundException("test"));
        assertDoesNotThrow(() -> new InvalidPriceException("test"));
        assertDoesNotThrow(() -> new InvalidDiscountException("test"));
        assertDoesNotThrow(() -> new DuplicateOrderException("test"));
        assertDoesNotThrow(() -> new OutOfStockException("test"));
        assertDoesNotThrow(() -> new InvalidAuthenticationException("test"));
        assertDoesNotThrow(() -> new InvalidDeliveryAddressException("test"));
    }
}
