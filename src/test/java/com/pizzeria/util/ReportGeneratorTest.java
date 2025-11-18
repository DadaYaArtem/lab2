package com.pizzeria.util;

import com.pizzeria.enums.EmployeeRole;
import com.pizzeria.model.Pizzeria;
import com.pizzeria.model.users.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReportGenerator
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ReportGenerator Tests")
class ReportGeneratorTest {

    @Mock
    private Pizzeria mockPizzeria;

    @Mock
    private Employee mockEmployee;

    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2024, 1, 15);
    }

    @Test
    @DisplayName("Should generate daily sales report successfully")
    void testGenerateDailySalesReport_Success() {
        // Given
        when(mockPizzeria.getName()).thenReturn("Test Pizzeria");
        when(mockPizzeria.getTotalOrders()).thenReturn(25);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(15000.0);

        // When
        String report = ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("ОТЧЕТ О ПРОДАЖАХ"));
        assertTrue(report.contains("Дата: 2024-01-15"));
        assertTrue(report.contains("Test Pizzeria"));
        assertTrue(report.contains("25"));
        assertTrue(report.contains("15000.00"));
    }

    @Test
    @DisplayName("Should generate sales report with zero orders")
    void testGenerateDailySalesReport_ZeroOrders() {
        // Given
        when(mockPizzeria.getName()).thenReturn("Empty Pizzeria");
        when(mockPizzeria.getTotalOrders()).thenReturn(0);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(0.0);

        // When
        String report = ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("0"));
        assertTrue(report.contains("0.00"));
    }

    @Test
    @DisplayName("Should generate sales report with large revenue")
    void testGenerateDailySalesReport_LargeRevenue() {
        // Given
        when(mockPizzeria.getName()).thenReturn("Busy Pizzeria");
        when(mockPizzeria.getTotalOrders()).thenReturn(500);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(999999.99);

        // When
        String report = ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("500"));
        assertTrue(report.contains("999999.99"));
    }

    @Test
    @DisplayName("Should format sales report correctly")
    void testGenerateDailySalesReport_Formatting() {
        // Given
        when(mockPizzeria.getName()).thenReturn("My Pizzeria");
        when(mockPizzeria.getTotalOrders()).thenReturn(10);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(5000.0);

        // When
        String report = ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        assertTrue(report.contains("=========="));
        assertTrue(report.contains("--------------------------------------"));
        assertTrue(report.contains("Общее количество заказов:"));
        assertTrue(report.contains("Общая выручка:"));
        assertTrue(report.contains("руб."));
    }

    @Test
    @DisplayName("Should generate employee report successfully")
    void testGenerateEmployeeReport_Success() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("John Doe");
        when(mockEmployee.getId()).thenReturn("EMP-001");
        when(mockEmployee.getRole()).thenReturn("Chef");
        when(mockEmployee.getSalary()).thenReturn(50000.0);
        when(mockEmployee.getYearsOfService()).thenReturn(5);
        when(mockEmployee.isActive()).thenReturn(true);

        // When
        String report = ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("ОТЧЕТ О СОТРУДНИКЕ"));
        assertTrue(report.contains("John Doe"));
        assertTrue(report.contains("EMP-001"));
        assertTrue(report.contains("Chef"));
        assertTrue(report.contains("50000.00"));
        assertTrue(report.contains("5"));
        assertTrue(report.contains("Активен"));
    }

    @Test
    @DisplayName("Should generate employee report for inactive employee")
    void testGenerateEmployeeReport_Inactive() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("Jane Smith");
        when(mockEmployee.getId()).thenReturn("EMP-002");
        when(mockEmployee.getRole()).thenReturn("Waiter");
        when(mockEmployee.getSalary()).thenReturn(30000.0);
        when(mockEmployee.getYearsOfService()).thenReturn(2);
        when(mockEmployee.isActive()).thenReturn(false);

        // When
        String report = ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("Неактивен"));
    }

    @Test
    @DisplayName("Should generate employee report with zero years of service")
    void testGenerateEmployeeReport_NewEmployee() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("New Employee");
        when(mockEmployee.getId()).thenReturn("EMP-003");
        when(mockEmployee.getRole()).thenReturn("Delivery Driver");
        when(mockEmployee.getSalary()).thenReturn(25000.0);
        when(mockEmployee.getYearsOfService()).thenReturn(0);
        when(mockEmployee.isActive()).thenReturn(true);

        // When
        String report = ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("0 лет"));
    }

    @Test
    @DisplayName("Should format employee report correctly")
    void testGenerateEmployeeReport_Formatting() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("Test Employee");
        when(mockEmployee.getId()).thenReturn("EMP-999");
        when(mockEmployee.getRole()).thenReturn("Manager");
        when(mockEmployee.getSalary()).thenReturn(60000.0);
        when(mockEmployee.getYearsOfService()).thenReturn(10);
        when(mockEmployee.isActive()).thenReturn(true);

        // When
        String report = ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        assertTrue(report.contains("=========="));
        assertTrue(report.contains("========================================"));
        assertTrue(report.contains("ФИО:"));
        assertTrue(report.contains("ID:"));
        assertTrue(report.contains("Должность:"));
        assertTrue(report.contains("Зарплата:"));
        assertTrue(report.contains("Стаж:"));
        assertTrue(report.contains("Статус:"));
        assertTrue(report.contains("руб."));
    }

    @Test
    @DisplayName("Should generate inventory report with low stock items")
    void testGenerateInventoryReport_WithLowStock() {
        // Given
        List<String> lowStockItems = Arrays.asList(
            "Mozzarella Cheese",
            "Tomato Sauce",
            "Pepperoni"
        );

        // When
        String report = ReportGenerator.generateInventoryReport(lowStockItems);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("ОТЧЕТ О СКЛАДЕ"));
        assertTrue(report.contains("Товары с низким запасом:"));
        assertTrue(report.contains("Mozzarella Cheese"));
        assertTrue(report.contains("Tomato Sauce"));
        assertTrue(report.contains("Pepperoni"));
    }

    @Test
    @DisplayName("Should generate inventory report with no low stock items")
    void testGenerateInventoryReport_NoLowStock() {
        // Given
        List<String> lowStockItems = new ArrayList<>();

        // When
        String report = ReportGenerator.generateInventoryReport(lowStockItems);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("ОТЧЕТ О СКЛАДЕ"));
        assertTrue(report.contains("Нет товаров с низким запасом"));
    }

    @Test
    @DisplayName("Should generate inventory report with single low stock item")
    void testGenerateInventoryReport_SingleItem() {
        // Given
        List<String> lowStockItems = Arrays.asList("Basil");

        // When
        String report = ReportGenerator.generateInventoryReport(lowStockItems);

        // Then
        assertNotNull(report);
        assertTrue(report.contains("Basil"));
        assertTrue(report.contains("  - "));
    }

    @Test
    @DisplayName("Should format inventory report correctly")
    void testGenerateInventoryReport_Formatting() {
        // Given
        List<String> lowStockItems = Arrays.asList("Item1", "Item2");

        // When
        String report = ReportGenerator.generateInventoryReport(lowStockItems);

        // Then
        assertTrue(report.contains("=========="));
        assertTrue(report.contains("===================================="));
        assertTrue(report.contains("  - Item1"));
        assertTrue(report.contains("  - Item2"));
    }

    @Test
    @DisplayName("Should generate inventory report with many items")
    void testGenerateInventoryReport_ManyItems() {
        // Given
        List<String> lowStockItems = Arrays.asList(
            "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
            "Item 6", "Item 7", "Item 8", "Item 9", "Item 10"
        );

        // When
        String report = ReportGenerator.generateInventoryReport(lowStockItems);

        // Then
        assertNotNull(report);
        for (String item : lowStockItems) {
            assertTrue(report.contains(item));
        }
    }

    @Test
    @DisplayName("Should verify sales report contains all required sections")
    void testGenerateDailySalesReport_AllSections() {
        // Given
        when(mockPizzeria.getName()).thenReturn("Complete Pizzeria");
        when(mockPizzeria.getTotalOrders()).thenReturn(42);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(12345.67);

        // When
        String report = ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        String[] requiredSections = {
            "ОТЧЕТ О ПРОДАЖАХ",
            "Дата:",
            "Пиццерия:",
            "Общее количество заказов:",
            "Общая выручка:"
        };
        for (String section : requiredSections) {
            assertTrue(report.contains(section), "Report should contain: " + section);
        }
    }

    @Test
    @DisplayName("Should verify employee report contains all required sections")
    void testGenerateEmployeeReport_AllSections() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("Complete Employee");
        when(mockEmployee.getId()).thenReturn("EMP-999");
        when(mockEmployee.getRole()).thenReturn("Full Role");
        when(mockEmployee.getSalary()).thenReturn(45000.0);
        when(mockEmployee.getYearsOfService()).thenReturn(3);
        when(mockEmployee.isActive()).thenReturn(true);

        // When
        String report = ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        String[] requiredSections = {
            "ОТЧЕТ О СОТРУДНИКЕ",
            "ФИО:",
            "ID:",
            "Должность:",
            "Зарплата:",
            "Стаж:",
            "Статус:"
        };
        for (String section : requiredSections) {
            assertTrue(report.contains(section), "Report should contain: " + section);
        }
    }

    @Test
    @DisplayName("Should handle decimal values in sales report")
    void testGenerateDailySalesReport_DecimalValues() {
        // Given
        when(mockPizzeria.getName()).thenReturn("Decimal Pizzeria");
        when(mockPizzeria.getTotalOrders()).thenReturn(15);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(1234.56);

        // When
        String report = ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        assertTrue(report.contains("1234.56"));
    }

    @Test
    @DisplayName("Should handle decimal values in employee report")
    void testGenerateEmployeeReport_DecimalSalary() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("Decimal Employee");
        when(mockEmployee.getId()).thenReturn("EMP-001");
        when(mockEmployee.getRole()).thenReturn("Worker");
        when(mockEmployee.getSalary()).thenReturn(35678.90);
        when(mockEmployee.getYearsOfService()).thenReturn(1);
        when(mockEmployee.isActive()).thenReturn(true);

        // When
        String report = ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        assertTrue(report.contains("35678.90"));
    }

    @Test
    @DisplayName("Should verify method calls in sales report generation")
    void testGenerateDailySalesReport_VerifyMethodCalls() {
        // Given
        when(mockPizzeria.getName()).thenReturn("Test");
        when(mockPizzeria.getTotalOrders()).thenReturn(10);
        when(mockPizzeria.calculateDailyRevenue()).thenReturn(5000.0);

        // When
        ReportGenerator.generateDailySalesReport(mockPizzeria, testDate);

        // Then
        verify(mockPizzeria).getName();
        verify(mockPizzeria).getTotalOrders();
        verify(mockPizzeria).calculateDailyRevenue();
    }

    @Test
    @DisplayName("Should verify method calls in employee report generation")
    void testGenerateEmployeeReport_VerifyMethodCalls() {
        // Given
        when(mockEmployee.getFullName()).thenReturn("Test");
        when(mockEmployee.getId()).thenReturn("ID");
        when(mockEmployee.getRole()).thenReturn("Role");
        when(mockEmployee.getSalary()).thenReturn(1000.0);
        when(mockEmployee.getYearsOfService()).thenReturn(1);
        when(mockEmployee.isActive()).thenReturn(true);

        // When
        ReportGenerator.generateEmployeeReport(mockEmployee);

        // Then
        verify(mockEmployee).getFullName();
        verify(mockEmployee).getId();
        verify(mockEmployee).getRole();
        verify(mockEmployee).getSalary();
        verify(mockEmployee).getYearsOfService();
        verify(mockEmployee).isActive();
    }
}
