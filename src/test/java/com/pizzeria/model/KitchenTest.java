package com.pizzeria.model;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InsufficientIngredientsException;
import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.model.products.MargheritaPizza;
import com.pizzeria.model.products.Pizza;
import com.pizzeria.model.users.Chef;
import com.pizzeria.model.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Kitchen class
 */
@DisplayName("Kitchen Tests")
class KitchenTest {

    private Kitchen kitchen;
    private Inventory inventory;
    private Chef chef;
    private Order order;
    private Customer customer;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        inventory = new Inventory();
        kitchen = new Kitchen(inventory);
        chef = new Chef("CHEF-001", "Mario", "Rossi", 50000.0);
        customer = new Customer("CUST-001", "John", "Doe");
        order = new Order("ORD-001", customer);

        // Add a pizza to the order
        Pizza pizza = new MargheritaPizza(PizzaSize.MEDIUM);
        order.addItem(pizza, 1);
    }

    @Test
    @DisplayName("Should create kitchen with inventory")
    void testCreateKitchen() {
        assertNotNull(kitchen);
        assertNotNull(kitchen.getInventory());
    }

    @Test
    @DisplayName("Should have empty chef list initially")
    void testInitialChefList() {
        assertNotNull(kitchen.getChefs());
        assertEquals(0, kitchen.getChefs().size());
    }

    @Test
    @DisplayName("Should have empty current orders initially")
    void testInitialCurrentOrders() {
        assertNotNull(kitchen.getCurrentOrders());
        assertEquals(0, kitchen.getCurrentOrders().size());
    }

    @Test
    @DisplayName("Should have default max capacity")
    void testDefaultMaxCapacity() {
        assertEquals(10, kitchen.getMaxCapacity());
    }

    @Test
    @DisplayName("Should add chef to kitchen")
    void testAddChef() {
        kitchen.addChef(chef);
        assertEquals(1, kitchen.getChefs().size());
        assertTrue(kitchen.getChefs().contains(chef));
    }

    @Test
    @DisplayName("Should add multiple chefs")
    void testAddMultipleChefs() {
        Chef chef2 = new Chef("CHEF-002", "Luigi", "Verdi", 48000.0);

        kitchen.addChef(chef);
        kitchen.addChef(chef2);

        assertEquals(2, kitchen.getChefs().size());
        assertTrue(kitchen.getChefs().contains(chef));
        assertTrue(kitchen.getChefs().contains(chef2));
    }

    @Test
    @DisplayName("Should prepare order when chef is available")
    void testPrepareOrderWithAvailableChef() throws InsufficientIngredientsException {
        kitchen.addChef(chef);

        int initialOrderCount = kitchen.getCurrentOrders().size();
        kitchen.prepareOrder(order);

        // Order is added then removed after completion
        assertEquals(initialOrderCount, kitchen.getCurrentOrders().size());
    }

    @Test
    @DisplayName("Should not prepare order when kitchen is full")
    void testPrepareOrderWhenKitchenFull() throws InvalidPriceException {
        kitchen.setMaxCapacity(1);
        kitchen.addChef(chef);

        // Fill kitchen to capacity
        Customer customer1 = new Customer("CUST-002", "Jane", "Smith");
        Order order1 = new Order("ORD-002", customer1);
        Pizza pizza1 = new MargheritaPizza(PizzaSize.SMALL);
        order1.addItem(pizza1, 1);

        // Manually add to current orders to simulate full kitchen
        kitchen.getCurrentOrders().add(order1);

        int initialSize = kitchen.getCurrentOrders().size();
        kitchen.prepareOrder(order);

        // Should not change since kitchen is full
        assertEquals(initialSize, kitchen.getCurrentOrders().size());
    }

    @Test
    @DisplayName("Should complete order")
    void testCompleteOrder() {
        kitchen.getCurrentOrders().add(order);
        assertEquals(1, kitchen.getCurrentOrders().size());

        kitchen.completeOrder(order);
        assertEquals(0, kitchen.getCurrentOrders().size());
        assertFalse(kitchen.getCurrentOrders().contains(order));
    }

    @Test
    @DisplayName("Should report busy when at max capacity")
    void testIsBusyAtMaxCapacity() throws InvalidPriceException {
        kitchen.setMaxCapacity(2);

        Customer customer1 = new Customer("CUST-002", "Jane", "Smith");
        Order order1 = new Order("ORD-002", customer1);
        Order order2 = new Order("ORD-003", customer1);

        kitchen.getCurrentOrders().add(order1);
        kitchen.getCurrentOrders().add(order2);

        assertTrue(kitchen.isBusy());
    }

    @Test
    @DisplayName("Should report not busy when below max capacity")
    void testNotBusyBelowMaxCapacity() {
        kitchen.setMaxCapacity(10);
        kitchen.getCurrentOrders().add(order);

        assertFalse(kitchen.isBusy());
    }

    @Test
    @DisplayName("Should get active orders count")
    void testGetActiveOrdersCount() throws InvalidPriceException {
        assertEquals(0, kitchen.getActiveOrdersCount());

        kitchen.getCurrentOrders().add(order);
        assertEquals(1, kitchen.getActiveOrdersCount());

        Customer customer1 = new Customer("CUST-002", "Jane", "Smith");
        Order order2 = new Order("ORD-002", customer1);
        kitchen.getCurrentOrders().add(order2);
        assertEquals(2, kitchen.getActiveOrdersCount());
    }

    @Test
    @DisplayName("Should set and get inventory")
    void testSetInventory() {
        Inventory newInventory = new Inventory();
        kitchen.setInventory(newInventory);
        assertEquals(newInventory, kitchen.getInventory());
    }

    @Test
    @DisplayName("Should set and get max capacity")
    void testSetMaxCapacity() {
        kitchen.setMaxCapacity(20);
        assertEquals(20, kitchen.getMaxCapacity());
    }

    @Test
    @DisplayName("Should set chefs list")
    void testSetChefs() {
        java.util.List<Chef> chefList = new java.util.ArrayList<>();
        chefList.add(chef);

        kitchen.setChefs(chefList);
        assertEquals(chefList, kitchen.getChefs());
        assertEquals(1, kitchen.getChefs().size());
    }

    @Test
    @DisplayName("Should handle order preparation without chefs")
    void testPrepareOrderWithoutChefs() {
        // No chefs added
        kitchen.prepareOrder(order);

        // Order is added but cannot be processed without chefs
        assertTrue(kitchen.getCurrentOrders().contains(order));
    }

    @Test
    @DisplayName("Should track multiple orders")
    void testTrackMultipleOrders() throws InvalidPriceException {
        Customer customer1 = new Customer("CUST-002", "Jane", "Smith");
        Order order1 = new Order("ORD-002", customer1);
        Order order2 = new Order("ORD-003", customer1);

        kitchen.getCurrentOrders().add(order1);
        kitchen.getCurrentOrders().add(order2);
        kitchen.getCurrentOrders().add(order);

        assertEquals(3, kitchen.getActiveOrdersCount());
        assertTrue(kitchen.getCurrentOrders().contains(order1));
        assertTrue(kitchen.getCurrentOrders().contains(order2));
        assertTrue(kitchen.getCurrentOrders().contains(order));
    }
}
