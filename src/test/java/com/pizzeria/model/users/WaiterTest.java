package com.pizzeria.model.users;

import com.pizzeria.enums.EmployeeRole;
import com.pizzeria.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Waiter class
 */
@DisplayName("Waiter Tests")
class WaiterTest {

    private Waiter waiter;
    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {
        waiter = new Waiter("WAITER-001", "Anna", "Smith", 35000.0);
        customer = new Customer("CUST-001", "John", "Doe");
        order = new Order("ORD-001", customer);
    }

    @Test
    @DisplayName("Should create waiter with ID, name, and salary")
    void testCreateWaiter() {
        assertNotNull(waiter);
        assertEquals("WAITER-001", waiter.getId());
        assertEquals("Anna", waiter.getFirstName());
        assertEquals("Smith", waiter.getLastName());
        assertEquals(35000.0, waiter.getSalary(), 0.01);
    }

    @Test
    @DisplayName("Should return correct role")
    void testGetRole() {
        assertEquals("Официант", waiter.getRole());
    }

    @Test
    @DisplayName("Should return WAITER as role enum")
    void testGetRoleEnum() {
        assertEquals(EmployeeRole.WAITER, waiter.getRoleEnum());
    }

    @Test
    @DisplayName("Should return correct full name")
    void testGetFullName() {
        assertEquals("Anna Smith", waiter.getFullName());
    }

    @Test
    @DisplayName("Should perform duty")
    void testPerformDuty() {
        assertDoesNotThrow(() -> waiter.performDuty());
    }

    @Test
    @DisplayName("Should have empty current orders initially")
    void testInitialCurrentOrders() {
        assertNotNull(waiter.getCurrentOrders());
        assertEquals(0, waiter.getCurrentOrders().size());
    }

    @Test
    @DisplayName("Should have zero tips initially")
    void testInitialTips() {
        assertEquals(0.0, waiter.getTips(), 0.01);
    }

    @Test
    @DisplayName("Should take order")
    void testTakeOrder() {
        waiter.takeOrder(order);
        assertEquals(1, waiter.getCurrentOrders().size());
        assertTrue(waiter.getCurrentOrders().contains(order));
    }

    @Test
    @DisplayName("Should take multiple orders")
    void testTakeMultipleOrders() {
        Order order2 = new Order("ORD-002", customer);
        Order order3 = new Order("ORD-003", customer);

        waiter.takeOrder(order);
        waiter.takeOrder(order2);
        waiter.takeOrder(order3);

        assertEquals(3, waiter.getCurrentOrders().size());
    }

    @Test
    @DisplayName("Should serve order")
    void testServeOrder() {
        waiter.takeOrder(order);
        assertEquals(1, waiter.getCurrentOrders().size());

        waiter.serveOrder(order);
        assertEquals(0, waiter.getCurrentOrders().size());
        assertFalse(waiter.getCurrentOrders().contains(order));
    }

    @Test
    @DisplayName("Should receive tip")
    void testReceiveTip() {
        waiter.receiveTip(50.0);
        assertEquals(50.0, waiter.getTips(), 0.01);
    }

    @Test
    @DisplayName("Should accumulate multiple tips")
    void testAccumulateMultipleTips() {
        waiter.receiveTip(50.0);
        waiter.receiveTip(30.0);
        waiter.receiveTip(20.0);
        assertEquals(100.0, waiter.getTips(), 0.01);
    }

    @Test
    @DisplayName("Should calculate total earnings with tips")
    void testTotalEarningsWithTips() {
        waiter.receiveTip(100.0);
        double totalEarnings = waiter.getTotalEarnings();
        assertEquals(35100.0, totalEarnings, 0.01); // salary + tips
    }

    @Test
    @DisplayName("Should calculate total earnings without tips")
    void testTotalEarningsWithoutTips() {
        double totalEarnings = waiter.getTotalEarnings();
        assertEquals(35000.0, totalEarnings, 0.01);
    }

    @Test
    @DisplayName("Should not be busy with few orders")
    void testNotBusyWithFewOrders() {
        waiter.takeOrder(order);
        assertFalse(waiter.isBusy());
    }

    @Test
    @DisplayName("Should be busy with many orders")
    void testBusyWithManyOrders() {
        for (int i = 0; i < 6; i++) {
            Order newOrder = new Order("ORD-" + i, customer);
            waiter.takeOrder(newOrder);
        }
        assertTrue(waiter.isBusy());
    }

    @Test
    @DisplayName("Should set tips directly")
    void testSetTips() {
        waiter.setTips(200.0);
        assertEquals(200.0, waiter.getTips(), 0.01);
    }

    @Test
    @DisplayName("Should be active by default")
    void testActiveByDefault() {
        assertTrue(waiter.isActive());
    }

    @Test
    @DisplayName("Should set active status")
    void testSetActive() {
        waiter.setActive(false);
        assertFalse(waiter.isActive());
    }

    @Test
    @DisplayName("Should have hire date set")
    void testHireDateSet() {
        assertNotNull(waiter.getHireDate());
    }

    @Test
    @DisplayName("Should calculate years of service")
    void testYearsOfService() {
        int years = waiter.getYearsOfService();
        assertTrue(years >= 0);
    }

    @Test
    @DisplayName("Should calculate bonus based on salary and years")
    void testCalculateBonus() {
        double bonus = waiter.calculateBonus();
        assertTrue(bonus >= 0);
    }

    @Test
    @DisplayName("Should verify password")
    void testVerifyPassword() {
        assertTrue(waiter.verifyPassword("default123"));
        assertFalse(waiter.verifyPassword("wrong"));
    }

    @Test
    @DisplayName("Should change password successfully")
    void testChangePassword() {
        assertDoesNotThrow(() -> {
            waiter.changePassword("default123", "newpass123");
        });
        assertTrue(waiter.verifyPassword("newpass123"));
    }

    @Test
    @DisplayName("Should handle serving order not in current orders")
    void testServeOrderNotInList() {
        Order otherOrder = new Order("ORD-999", customer);
        int initialSize = waiter.getCurrentOrders().size();
        waiter.serveOrder(otherOrder);
        assertEquals(initialSize, waiter.getCurrentOrders().size());
    }
}
