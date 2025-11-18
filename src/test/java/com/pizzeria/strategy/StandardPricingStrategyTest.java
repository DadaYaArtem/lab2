package com.pizzeria.strategy;

import com.pizzeria.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StandardPricingStrategy
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StandardPricingStrategy Tests")
class StandardPricingStrategyTest {

    private StandardPricingStrategy strategy;

    @Mock
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        strategy = new StandardPricingStrategy();
    }

    @Test
    @DisplayName("Should calculate price as base price plus delivery cost")
    void testCalculatePrice_WithDelivery() {
        // Given
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(600.0, price, 0.01);
        verify(mockOrder).getPrice();
        verify(mockOrder).calculateDeliveryCost();
    }

    @Test
    @DisplayName("Should calculate price without delivery cost when zero")
    void testCalculatePrice_NoDelivery() {
        // Given
        when(mockOrder.getPrice()).thenReturn(750.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(0.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(750.0, price, 0.01);
    }

    @Test
    @DisplayName("Should apply no discount to base price")
    void testCalculatePrice_NoDiscount() {
        // Given
        when(mockOrder.getPrice()).thenReturn(1000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(150.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(1150.0, price, 0.01);
        // Standard strategy doesn't apply any discount
    }

    @Test
    @DisplayName("Should handle zero base price")
    void testCalculatePrice_ZeroBasePrice() {
        // Given
        when(mockOrder.getPrice()).thenReturn(0.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(100.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle large order amounts")
    void testCalculatePrice_LargeAmount() {
        // Given
        when(mockOrder.getPrice()).thenReturn(9999.99);
        when(mockOrder.calculateDeliveryCost()).thenReturn(200.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(10199.99, price, 0.01);
    }

    @Test
    @DisplayName("Should handle small order amounts")
    void testCalculatePrice_SmallAmount() {
        // Given
        when(mockOrder.getPrice()).thenReturn(50.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(150.0, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with decimal values")
    void testCalculatePrice_DecimalValues() {
        // Given
        when(mockOrder.getPrice()).thenReturn(123.45);
        when(mockOrder.calculateDeliveryCost()).thenReturn(67.89);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(191.34, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate price consistently for same order")
    void testCalculatePrice_Consistency() {
        // Given
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price1 = strategy.calculatePrice(mockOrder);
        double price2 = strategy.calculatePrice(mockOrder);
        double price3 = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(price1, price2, 0.01);
        assertEquals(price2, price3, 0.01);
    }

    @Test
    @DisplayName("Should implement PriceCalculationStrategy interface")
    void testImplementsInterface() {
        // Then
        assertInstanceOf(PriceCalculationStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should handle various delivery costs")
    void testCalculatePrice_VariousDeliveryCosts() {
        // Given
        when(mockOrder.getPrice()).thenReturn(500.0);

        // Test with delivery cost 1
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);
        double price1 = strategy.calculatePrice(mockOrder);
        assertEquals(600.0, price1, 0.01);

        // Test with delivery cost 2
        when(mockOrder.calculateDeliveryCost()).thenReturn(150.0);
        double price2 = strategy.calculatePrice(mockOrder);
        assertEquals(650.0, price2, 0.01);

        // Test with delivery cost 3
        when(mockOrder.calculateDeliveryCost()).thenReturn(200.0);
        double price3 = strategy.calculatePrice(mockOrder);
        assertEquals(700.0, price3, 0.01);
    }

    @Test
    @DisplayName("Should handle various base prices")
    void testCalculatePrice_VariousBasePrices() {
        // Given
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // Test with base price 1
        when(mockOrder.getPrice()).thenReturn(200.0);
        double price1 = strategy.calculatePrice(mockOrder);
        assertEquals(300.0, price1, 0.01);

        // Test with base price 2
        when(mockOrder.getPrice()).thenReturn(500.0);
        double price2 = strategy.calculatePrice(mockOrder);
        assertEquals(600.0, price2, 0.01);

        // Test with base price 3
        when(mockOrder.getPrice()).thenReturn(1000.0);
        double price3 = strategy.calculatePrice(mockOrder);
        assertEquals(1100.0, price3, 0.01);
    }

    @Test
    @DisplayName("Should return exact sum of base price and delivery")
    void testCalculatePrice_ExactSum() {
        // Given
        when(mockOrder.getPrice()).thenReturn(456.78);
        when(mockOrder.calculateDeliveryCost()).thenReturn(123.45);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(580.23, price, 0.01);
    }

    @Test
    @DisplayName("Should handle minimum positive values")
    void testCalculatePrice_MinimumValues() {
        // Given
        when(mockOrder.getPrice()).thenReturn(0.01);
        when(mockOrder.calculateDeliveryCost()).thenReturn(0.01);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(0.02, price, 0.001);
    }
}
