package com.pizzeria.model.users;

import com.pizzeria.enums.EmployeeRole;
import com.pizzeria.exceptions.InvalidDeliveryAddressException;
import com.pizzeria.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DeliveryDriver class
 */
@DisplayName("DeliveryDriver Tests")
class DeliveryDriverTest {

    private DeliveryDriver driver;
    private Address addressFrom;
    private Address addressTo;

    @BeforeEach
    void setUp() throws InvalidDeliveryAddressException {
        driver = new DeliveryDriver("DRIVER-001", "Bob", "Johnson", 40000.0);
        addressFrom = new Address("Main Street", "1", "New York", "10001");
        addressTo = new Address("Second Street", "5", "New York", "10002");

        addressFrom.setLatitude(40.7128);
        addressFrom.setLongitude(-74.0060);
        addressTo.setLatitude(40.7580);
        addressTo.setLongitude(-73.9855);
    }

    @Test
    @DisplayName("Should create delivery driver with ID, name, and salary")
    void testCreateDeliveryDriver() {
        assertNotNull(driver);
        assertEquals("DRIVER-001", driver.getId());
        assertEquals("Bob", driver.getFirstName());
        assertEquals("Johnson", driver.getLastName());
        assertEquals(40000.0, driver.getSalary(), 0.01);
    }

    @Test
    @DisplayName("Should return correct role")
    void testGetRole() {
        assertEquals("Водитель доставки", driver.getRole());
    }

    @Test
    @DisplayName("Should return DELIVERY_DRIVER as role enum")
    void testGetRoleEnum() {
        assertEquals(EmployeeRole.DELIVERY_DRIVER, driver.getRoleEnum());
    }

    @Test
    @DisplayName("Should return correct full name")
    void testGetFullName() {
        assertEquals("Bob Johnson", driver.getFullName());
    }

    @Test
    @DisplayName("Should perform duty")
    void testPerformDuty() {
        assertDoesNotThrow(() -> driver.performDuty());
    }

    @Test
    @DisplayName("Should be available initially")
    void testAvailableInitially() {
        assertTrue(driver.isAvailable());
    }

    @Test
    @DisplayName("Should have zero deliveries completed initially")
    void testInitialDeliveriesCompleted() {
        assertEquals(0, driver.getDeliveriesCompleted());
    }

    @Test
    @DisplayName("Should set and get vehicle type")
    void testSetVehicleType() {
        driver.setVehicleType("Motorcycle");
        assertEquals("Motorcycle", driver.getVehicleType());
    }

    @Test
    @DisplayName("Should set and get vehicle number")
    void testSetVehicleNumber() {
        driver.setVehicleNumber("ABC-123");
        assertEquals("ABC-123", driver.getVehicleNumber());
    }

    @Test
    @DisplayName("Should start delivery")
    void testStartDelivery() {
        driver.startDelivery();
        assertFalse(driver.isAvailable());
    }

    @Test
    @DisplayName("Should complete delivery")
    void testCompleteDelivery() {
        driver.startDelivery();
        assertFalse(driver.isAvailable());

        driver.completeDelivery();
        assertTrue(driver.isAvailable());
        assertEquals(1, driver.getDeliveriesCompleted());
    }

    @Test
    @DisplayName("Should increment deliveries on each completion")
    void testMultipleDeliveries() {
        driver.completeDelivery();
        assertEquals(1, driver.getDeliveriesCompleted());

        driver.completeDelivery();
        assertEquals(2, driver.getDeliveriesCompleted());

        driver.completeDelivery();
        assertEquals(3, driver.getDeliveriesCompleted());
    }

    @Test
    @DisplayName("Should calculate delivery time based on distance")
    void testCalculateDeliveryTime() {
        int deliveryTime = driver.calculateDeliveryTime(addressFrom, addressTo);
        assertTrue(deliveryTime > 0);
    }

    @Test
    @DisplayName("Should calculate delivery bonus")
    void testCalculateDeliveryBonus() {
        assertEquals(0.0, driver.calculateDeliveryBonus(), 0.01);

        driver.completeDelivery();
        assertEquals(50.0, driver.calculateDeliveryBonus(), 0.01);

        driver.completeDelivery();
        assertEquals(100.0, driver.calculateDeliveryBonus(), 0.01);
    }

    @Test
    @DisplayName("Should calculate bonus for multiple deliveries")
    void testBonusForMultipleDeliveries() {
        for (int i = 0; i < 10; i++) {
            driver.completeDelivery();
        }
        assertEquals(500.0, driver.calculateDeliveryBonus(), 0.01); // 10 * 50
    }

    @Test
    @DisplayName("Should set availability")
    void testSetAvailability() {
        driver.setAvailable(false);
        assertFalse(driver.isAvailable());

        driver.setAvailable(true);
        assertTrue(driver.isAvailable());
    }

    @Test
    @DisplayName("Should be active by default")
    void testActiveByDefault() {
        assertTrue(driver.isActive());
    }

    @Test
    @DisplayName("Should set active status")
    void testSetActive() {
        driver.setActive(false);
        assertFalse(driver.isActive());
    }

    @Test
    @DisplayName("Should have hire date set")
    void testHireDateSet() {
        assertNotNull(driver.getHireDate());
    }

    @Test
    @DisplayName("Should calculate years of service")
    void testYearsOfService() {
        int years = driver.getYearsOfService();
        assertTrue(years >= 0);
    }

    @Test
    @DisplayName("Should calculate bonus based on salary and years")
    void testCalculateBonus() {
        double bonus = driver.calculateBonus();
        assertTrue(bonus >= 0);
    }

    @Test
    @DisplayName("Should verify password")
    void testVerifyPassword() {
        assertTrue(driver.verifyPassword("default123"));
        assertFalse(driver.verifyPassword("wrong"));
    }

    @Test
    @DisplayName("Should change password successfully")
    void testChangePassword() {
        assertDoesNotThrow(() -> {
            driver.changePassword("default123", "newpass123");
        });
        assertTrue(driver.verifyPassword("newpass123"));
    }

    @Test
    @DisplayName("Should handle delivery lifecycle")
    void testDeliveryLifecycle() {
        // Initially available with 0 deliveries
        assertTrue(driver.isAvailable());
        assertEquals(0, driver.getDeliveriesCompleted());

        // Start delivery
        driver.startDelivery();
        assertFalse(driver.isAvailable());

        // Complete delivery
        driver.completeDelivery();
        assertTrue(driver.isAvailable());
        assertEquals(1, driver.getDeliveriesCompleted());
    }

    @Test
    @DisplayName("Should set vehicle information")
    void testSetVehicleInformation() {
        driver.setVehicleType("Car");
        driver.setVehicleNumber("XYZ-789");

        assertEquals("Car", driver.getVehicleType());
        assertEquals("XYZ-789", driver.getVehicleNumber());
    }
}
