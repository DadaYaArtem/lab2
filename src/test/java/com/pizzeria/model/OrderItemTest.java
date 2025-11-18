package com.pizzeria.model;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InvalidPizzaSizeException;
import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.model.products.MargheritaPizza;
import com.pizzeria.model.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OrderItem Model Tests")
class OrderItemTest {

    private Product pizza;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() throws InvalidPriceException, InvalidPizzaSizeException {
        pizza = new MargheritaPizza(PizzaSize.MEDIUM);
        orderItem = new OrderItem(pizza, 2);
    }

    @Test
    @DisplayName("Should create order item with product and quantity")
    void shouldCreateOrderItemWithProductAndQuantity() {
        assertNotNull(orderItem);
        assertEquals(pizza, orderItem.getProduct());
        assertEquals(2, orderItem.getQuantity());
    }

    @Test
    @DisplayName("Should calculate subtotal correctly")
    void shouldCalculateSubtotalCorrectly() {
        double expectedSubtotal = pizza.getPrice() * 2;
        assertEquals(expectedSubtotal, orderItem.getSubtotal(), 0.01);
    }

    @Test
    @DisplayName("Should update quantity")
    void shouldUpdateQuantity() {
        orderItem.setQuantity(5);
        assertEquals(5, orderItem.getQuantity());

        double expectedSubtotal = pizza.getPrice() * 5;
        assertEquals(expectedSubtotal, orderItem.getSubtotal(), 0.01);
    }

    @Test
    @DisplayName("Should get product details")
    void shouldGetProductDetails() {
        assertEquals(pizza.getName(), orderItem.getProduct().getName());
        assertEquals(pizza.getPrice(), orderItem.getProduct().getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should handle single quantity")
    void shouldHandleSingleQuantity() throws InvalidPriceException {
        OrderItem singleItem = new OrderItem(pizza, 1);
        assertEquals(1, singleItem.getQuantity());
        assertEquals(pizza.getPrice(), singleItem.getSubtotal(), 0.01);
    }

    @Test
    @DisplayName("Should handle large quantity")
    void shouldHandleLargeQuantity() throws InvalidPriceException {
        OrderItem largeItem = new OrderItem(pizza, 100);
        assertEquals(100, largeItem.getQuantity());
        assertEquals(pizza.getPrice() * 100, largeItem.getSubtotal(), 0.01);
    }
}
