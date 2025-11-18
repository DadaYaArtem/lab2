package com.pizzeria.model.users;

import com.pizzeria.enums.EmployeeRole;
import com.pizzeria.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Manager class
 */
@DisplayName("Manager Tests")
class ManagerTest {

    private Manager manager;
    private Chef chef;
    private Waiter waiter;
    private DeliveryDriver driver;

    @BeforeEach
    void setUp() {
        manager = new Manager("MGR-001", "Alice", "Williams", 60000.0);
        chef = new Chef("CHEF-001", "Mario", "Rossi", 50000.0);
        waiter = new Waiter("WAITER-001", "Anna", "Smith", 35000.0);
        driver = new DeliveryDriver("DRIVER-001", "Bob", "Johnson", 40000.0);
    }

    @Test
    @DisplayName("Should create manager with ID, name, and salary")
    void testCreateManager() {
        assertNotNull(manager);
        assertEquals("MGR-001", manager.getId());
        assertEquals("Alice", manager.getFirstName());
        assertEquals("Williams", manager.getLastName());
        assertEquals(60000.0, manager.getSalary(), 0.01);
    }

    @Test
    @DisplayName("Should return correct role")
    void testGetRole() {
        assertEquals("Менеджер", manager.getRole());
    }

    @Test
    @DisplayName("Should return MANAGER as role enum")
    void testGetRoleEnum() {
        assertEquals(EmployeeRole.MANAGER, manager.getRoleEnum());
    }

    @Test
    @DisplayName("Should return correct full name")
    void testGetFullName() {
        assertEquals("Alice Williams", manager.getFullName());
    }

    @Test
    @DisplayName("Should perform duty")
    void testPerformDuty() {
        assertDoesNotThrow(() -> manager.performDuty());
    }

    @Test
    @DisplayName("Should have empty managed employees initially")
    void testInitialManagedEmployees() {
        assertNotNull(manager.getManagedEmployees());
        assertEquals(0, manager.getManagedEmployees().size());
    }

    @Test
    @DisplayName("Should add employee")
    void testAddEmployee() {
        manager.addEmployee(chef);
        assertEquals(1, manager.getManagedEmployees().size());
        assertTrue(manager.getManagedEmployees().contains(chef));
    }

    @Test
    @DisplayName("Should add multiple employees")
    void testAddMultipleEmployees() {
        manager.addEmployee(chef);
        manager.addEmployee(waiter);
        manager.addEmployee(driver);

        assertEquals(3, manager.getManagedEmployees().size());
        assertTrue(manager.getManagedEmployees().contains(chef));
        assertTrue(manager.getManagedEmployees().contains(waiter));
        assertTrue(manager.getManagedEmployees().contains(driver));
    }

    @Test
    @DisplayName("Should remove employee")
    void testRemoveEmployee() throws EmployeeNotFoundException {
        manager.addEmployee(chef);
        assertEquals(1, manager.getManagedEmployees().size());

        manager.removeEmployee(chef);
        assertEquals(0, manager.getManagedEmployees().size());
        assertFalse(manager.getManagedEmployees().contains(chef));
    }

    @Test
    @DisplayName("Should throw exception when removing non-existent employee")
    void testRemoveNonExistentEmployee() {
        assertThrows(EmployeeNotFoundException.class, () -> {
            manager.removeEmployee(chef);
        });
    }

    @Test
    @DisplayName("Should calculate total payroll")
    void testCalculateTotalPayroll() {
        manager.addEmployee(chef);   // 50000
        manager.addEmployee(waiter); // 35000
        manager.addEmployee(driver); // 40000

        double totalPayroll = manager.calculateTotalPayroll();
        assertEquals(125000.0, totalPayroll, 0.01);
    }

    @Test
    @DisplayName("Should return zero payroll with no employees")
    void testZeroPayrollWithNoEmployees() {
        assertEquals(0.0, manager.calculateTotalPayroll(), 0.01);
    }

    @Test
    @DisplayName("Should get employees by role - CHEF")
    void testGetEmployeesByRoleChef() {
        manager.addEmployee(chef);
        manager.addEmployee(waiter);

        List<Employee> chefs = manager.getEmployeesByRole(EmployeeRole.CHEF);
        assertEquals(1, chefs.size());
        assertTrue(chefs.contains(chef));
    }

    @Test
    @DisplayName("Should get employees by role - WAITER")
    void testGetEmployeesByRoleWaiter() {
        manager.addEmployee(chef);
        manager.addEmployee(waiter);

        List<Employee> waiters = manager.getEmployeesByRole(EmployeeRole.WAITER);
        assertEquals(1, waiters.size());
        assertTrue(waiters.contains(waiter));
    }

    @Test
    @DisplayName("Should get employees by role - DELIVERY_DRIVER")
    void testGetEmployeesByRoleDriver() {
        manager.addEmployee(driver);
        manager.addEmployee(waiter);

        List<Employee> drivers = manager.getEmployeesByRole(EmployeeRole.DELIVERY_DRIVER);
        assertEquals(1, drivers.size());
        assertTrue(drivers.contains(driver));
    }

    @Test
    @DisplayName("Should return empty list for role with no employees")
    void testGetEmployeesByRoleEmpty() {
        manager.addEmployee(chef);

        List<Employee> waiters = manager.getEmployeesByRole(EmployeeRole.WAITER);
        assertEquals(0, waiters.size());
    }

    @Test
    @DisplayName("Should get multiple employees of same role")
    void testGetMultipleEmployeesSameRole() {
        Chef chef2 = new Chef("CHEF-002", "Luigi", "Verdi", 48000.0);
        manager.addEmployee(chef);
        manager.addEmployee(chef2);
        manager.addEmployee(waiter);

        List<Employee> chefs = manager.getEmployeesByRole(EmployeeRole.CHEF);
        assertEquals(2, chefs.size());
        assertTrue(chefs.contains(chef));
        assertTrue(chefs.contains(chef2));
    }

    @Test
    @DisplayName("Should conduct performance review")
    void testConductPerformanceReview() {
        manager.addEmployee(chef);
        assertDoesNotThrow(() -> manager.conductPerformanceReview(chef));
    }

    @Test
    @DisplayName("Should set and get department")
    void testSetDepartment() {
        manager.setDepartment("Operations");
        assertEquals("Operations", manager.getDepartment());
    }

    @Test
    @DisplayName("Should be active by default")
    void testActiveByDefault() {
        assertTrue(manager.isActive());
    }

    @Test
    @DisplayName("Should set active status")
    void testSetActive() {
        manager.setActive(false);
        assertFalse(manager.isActive());
    }

    @Test
    @DisplayName("Should have hire date set")
    void testHireDateSet() {
        assertNotNull(manager.getHireDate());
    }

    @Test
    @DisplayName("Should calculate years of service")
    void testYearsOfService() {
        int years = manager.getYearsOfService();
        assertTrue(years >= 0);
    }

    @Test
    @DisplayName("Should calculate bonus based on salary and years")
    void testCalculateBonus() {
        double bonus = manager.calculateBonus();
        assertTrue(bonus >= 0);
    }

    @Test
    @DisplayName("Should verify password")
    void testVerifyPassword() {
        assertTrue(manager.verifyPassword("default123"));
        assertFalse(manager.verifyPassword("wrong"));
    }

    @Test
    @DisplayName("Should change password successfully")
    void testChangePassword() {
        assertDoesNotThrow(() -> {
            manager.changePassword("default123", "newpass123");
        });
        assertTrue(manager.verifyPassword("newpass123"));
    }
}
