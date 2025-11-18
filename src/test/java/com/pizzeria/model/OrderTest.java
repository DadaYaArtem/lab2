package com.pizzeria.model;

import com.pizzeria.enums.OrderStatus;
import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.*;
import com.pizzeria.model.products.MargheritaPizza;
import com.pizzeria.model.products.Product;
import com.pizzeria.model.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order Model Tests")
class OrderTest {

    private Order order;
    private Customer customer;
    private Product pizza;

    @BeforeEach
    void setUp() throws InvalidPriceException, InvalidPizzaSizeException {
        customer = new Customer("Иван", "Иванов", new Email("ivan@test.com"),
                               new PhoneNumber("+79991234567"));
        order = new Order("ORD-001", customer);
        pizza = new MargheritaPizza(PizzaSize.MEDIUM);
    }

    @Test
    @DisplayName("Should create order with valid customer")
    void shouldCreateOrderWithValidCustomer() {
        assertNotNull(order);
        assertEquals("ORD-001", order.getOrderId());
        assertEquals(customer, order.getCustomer());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(0, order.getItems().size());
    }

    @Test
    @DisplayName("Should add item to order")
    void shouldAddItemToOrder() throws InvalidPriceException {
        OrderItem item = new OrderItem(pizza, 2);
        order.addItem(item);

        assertEquals(1, order.getItems().size());
        assertTrue(order.getItems().contains(item));
    }

    @Test
    @DisplayName("Should calculate total price correctly")
    void shouldCalculateTotalPriceCorrectly() throws InvalidPriceException {
        OrderItem item1 = new OrderItem(pizza, 2);
        order.addItem(item1);

        double expectedTotal = pizza.getPrice() * 2;
        assertEquals(expectedTotal, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should update order status")
    void shouldUpdateOrderStatus() {
        order.setStatus(OrderStatus.PREPARING);
        assertEquals(OrderStatus.PREPARING, order.getStatus());

        order.setStatus(OrderStatus.READY);
        assertEquals(OrderStatus.READY, order.getStatus());
    }

    @Test
    @DisplayName("Should set and get delivery address")
    void shouldSetAndGetDeliveryAddress() {
        Address address = new Address("Москва", "Ленина", "10", "5");
        order.setDeliveryAddress(address);

        assertEquals(address, order.getDeliveryAddress());
    }

    @Test
    @DisplayName("Should handle multiple items")
    void shouldHandleMultipleItems() throws InvalidPriceException, InvalidPizzaSizeException {
        Product pizza2 = new MargheritaPizza(PizzaSize.LARGE);

        OrderItem item1 = new OrderItem(pizza, 2);
        OrderItem item2 = new OrderItem(pizza2, 1);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(2, order.getItems().size());

        double expectedTotal = (pizza.getPrice() * 2) + (pizza2.getPrice() * 1);
        assertEquals(expectedTotal, order.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should apply discount")
    void shouldApplyDiscount() throws InvalidDiscountException {
        order.applyDiscount(10.0);
        assertEquals(10.0, order.getDiscount(), 0.01);
    }

    @Test
    @DisplayName("Should throw exception for invalid discount")
    void shouldThrowExceptionForInvalidDiscount() {
        assertThrows(InvalidDiscountException.class, () -> order.applyDiscount(-5.0));
        assertThrows(InvalidDiscountException.class, () -> order.applyDiscount(101.0));
    }

    @Test
    @DisplayName("Should calculate final price with discount")
    void shouldCalculateFinalPriceWithDiscount() throws InvalidPriceException, InvalidDiscountException {
        OrderItem item = new OrderItem(pizza, 2);
        order.addItem(item);

        double totalBeforeDiscount = order.getTotalPrice();
        order.applyDiscount(10.0); // 10% discount

        double expectedFinalPrice = totalBeforeDiscount * 0.9;
        assertEquals(expectedFinalPrice, order.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should get order creation time")
    void shouldGetOrderCreationTime() {
        assertNotNull(order.getCreatedAt());
    }

    @Test
    @DisplayName("Should handle empty order")
    void shouldHandleEmptyOrder() {
        assertEquals(0.0, order.getTotalPrice(), 0.01);
        assertTrue(order.getItems().isEmpty());
    }
}
