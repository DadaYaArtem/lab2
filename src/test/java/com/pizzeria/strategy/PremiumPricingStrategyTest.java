package com.pizzeria.strategy;

import com.pizzeria.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PremiumPricingStrategy
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PremiumPricingStrategy Tests")
class PremiumPricingStrategyTest {

    private PremiumPricingStrategy strategy;

    @Mock
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        when(mockOrder.getPrice()).thenReturn(1000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);
    }

    @Test
    @DisplayName("Should add service fee to total price")
    void testCalculatePrice_WithServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Service Fee: 50, Total: 1150
        assertEquals(1150.0, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with zero service fee")
    void testCalculatePrice_ZeroServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(0.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Service Fee: 0, Total: 1100
        assertEquals(1100.0, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with high service fee")
    void testCalculatePrice_HighServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(500.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Service Fee: 500, Total: 1600
        assertEquals(1600.0, price, 0.01);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 25.0, 50.0, 100.0, 200.0})
    @DisplayName("Should add various service fees correctly")
    void testCalculatePrice_VariousServiceFees(double serviceFee) {
        // Given
        strategy = new PremiumPricingStrategy(serviceFee);
        double basePrice = 1000.0;
        double deliveryCost = 100.0;
        double expectedPrice = basePrice + deliveryCost + serviceFee;

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(expectedPrice, price, 0.01);
    }

    @Test
    @DisplayName("Should get service fee")
    void testGetServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(75.0);

        // When
        double serviceFee = strategy.getServiceFee();

        // Then
        assertEquals(75.0, serviceFee, 0.01);
    }

    @Test
    @DisplayName("Should set service fee")
    void testSetServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);
        assertEquals(50.0, strategy.getServiceFee(), 0.01);

        // When
        strategy.setServiceFee(100.0);

        // Then
        assertEquals(100.0, strategy.getServiceFee(), 0.01);
    }

    @Test
    @DisplayName("Should calculate price with updated service fee")
    void testCalculatePrice_AfterUpdatingServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);
        double price1 = strategy.calculatePrice(mockOrder);
        assertEquals(1150.0, price1, 0.01);

        // When
        strategy.setServiceFee(100.0);
        double price2 = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(1200.0, price2, 0.01);
    }

    @Test
    @DisplayName("Should handle premium pricing with no delivery cost")
    void testCalculatePrice_NoDelivery() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(0.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 0, Service Fee: 50, Total: 1050
        assertEquals(1050.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle premium pricing with high delivery cost")
    void testCalculatePrice_HighDeliveryCost() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(300.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 500, Delivery: 300, Service Fee: 50, Total: 850
        assertEquals(850.0, price, 0.01);
    }

    @Test
    @DisplayName("Should be more expensive than standard pricing")
    void testCalculatePrice_MoreThanStandard() {
        // Given
        strategy = new PremiumPricingStrategy(100.0);
        StandardPricingStrategy standardStrategy = new StandardPricingStrategy();

        // When
        double premiumPrice = strategy.calculatePrice(mockOrder);
        double standardPrice = standardStrategy.calculatePrice(mockOrder);

        // Then
        assertTrue(premiumPrice > standardPrice);
        assertEquals(100.0, premiumPrice - standardPrice, 0.01);
    }

    @Test
    @DisplayName("Should handle small order with premium fee")
    void testCalculatePrice_SmallOrder() {
        // Given
        strategy = new PremiumPricingStrategy(25.0);
        when(mockOrder.getPrice()).thenReturn(100.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(50.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 100, Delivery: 50, Service Fee: 25, Total: 175
        assertEquals(175.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle large order with premium fee")
    void testCalculatePrice_LargeOrder() {
        // Given
        strategy = new PremiumPricingStrategy(150.0);
        when(mockOrder.getPrice()).thenReturn(5000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(200.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 5000, Delivery: 200, Service Fee: 150, Total: 5350
        assertEquals(5350.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle decimal service fee")
    void testCalculatePrice_DecimalServiceFee() {
        // Given
        strategy = new PremiumPricingStrategy(37.5);
        when(mockOrder.getPrice()).thenReturn(800.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 800, Delivery: 100, Service Fee: 37.5, Total: 937.5
        assertEquals(937.5, price, 0.01);
    }

    @Test
    @DisplayName("Should implement PriceCalculationStrategy interface")
    void testImplementsInterface() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);

        // Then
        assertInstanceOf(PriceCalculationStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should add flat fee regardless of order size")
    void testCalculatePrice_FlatFee() {
        // Given
        strategy = new PremiumPricingStrategy(100.0);

        // When - small order
        when(mockOrder.getPrice()).thenReturn(200.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(50.0);
        double smallPrice = strategy.calculatePrice(mockOrder);

        // When - large order
        when(mockOrder.getPrice()).thenReturn(2000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(150.0);
        double largePrice = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(350.0, smallPrice, 0.01);  // 200 + 50 + 100
        assertEquals(2250.0, largePrice, 0.01); // 2000 + 150 + 100
    }

    @Test
    @DisplayName("Should calculate price consistently")
    void testCalculatePrice_Consistency() {
        // Given
        strategy = new PremiumPricingStrategy(75.0);

        // When
        double price1 = strategy.calculatePrice(mockOrder);
        double price2 = strategy.calculatePrice(mockOrder);
        double price3 = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(price1, price2, 0.01);
        assertEquals(price2, price3, 0.01);
    }

    @Test
    @DisplayName("Should verify premium pricing formula")
    void testCalculatePrice_VerifyFormula() {
        // Given
        strategy = new PremiumPricingStrategy(60.0);
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Formula: basePrice + deliveryCost + serviceFee
        // 500 + 100 + 60 = 660
        assertEquals(660.0, price, 0.01);
        verify(mockOrder).getPrice();
        verify(mockOrder).calculateDeliveryCost();
    }

    @Test
    @DisplayName("Should handle minimum service fee")
    void testCalculatePrice_MinimumFee() {
        // Given
        strategy = new PremiumPricingStrategy(0.01);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Service Fee: 0.01, Total: 1100.01
        assertEquals(1100.01, price, 0.001);
    }

    @Test
    @DisplayName("Should handle very large service fee")
    void testCalculatePrice_VeryLargeFee() {
        // Given
        strategy = new PremiumPricingStrategy(10000.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Service Fee: 10000, Total: 11100
        assertEquals(11100.0, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate premium price with zero base price")
    void testCalculatePrice_ZeroBasePrice() {
        // Given
        strategy = new PremiumPricingStrategy(50.0);
        when(mockOrder.getPrice()).thenReturn(0.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 0, Delivery: 100, Service Fee: 50, Total: 150
        assertEquals(150.0, price, 0.01);
    }
}
