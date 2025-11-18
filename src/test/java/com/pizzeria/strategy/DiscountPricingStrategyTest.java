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
 * Unit tests for DiscountPricingStrategy
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("DiscountPricingStrategy Tests")
class DiscountPricingStrategyTest {

    private DiscountPricingStrategy strategy;

    @Mock
    private Order mockOrder;

    @BeforeEach
    void setUp() {
        when(mockOrder.getPrice()).thenReturn(1000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);
    }

    @Test
    @DisplayName("Should apply 10% discount correctly")
    void testCalculatePrice_10PercentDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(10.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Total: 1100, Discount 10%: 990
        assertEquals(990.0, price, 0.01);
    }

    @Test
    @DisplayName("Should apply 20% discount correctly")
    void testCalculatePrice_20PercentDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(20.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Total: 1100, Discount 20%: 880
        assertEquals(880.0, price, 0.01);
    }

    @Test
    @DisplayName("Should apply 50% discount correctly")
    void testCalculatePrice_50PercentDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(50.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Total: 1100, Discount 50%: 550
        assertEquals(550.0, price, 0.01);
    }

    @Test
    @DisplayName("Should apply 0% discount (no discount)")
    void testCalculatePrice_0PercentDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(0.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Total: 1100, No discount: 1100
        assertEquals(1100.0, price, 0.01);
    }

    @ParameterizedTest
    @ValueSource(doubles = {5.0, 10.0, 15.0, 20.0, 25.0, 30.0})
    @DisplayName("Should apply various discount percentages correctly")
    void testCalculatePrice_VariousDiscounts(double discountPercentage) {
        // Given
        strategy = new DiscountPricingStrategy(discountPercentage);
        double basePrice = 1000.0;
        double deliveryCost = 100.0;
        double total = basePrice + deliveryCost;
        double expectedPrice = total * (1 - discountPercentage / 100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(expectedPrice, price, 0.01);
    }

    @Test
    @DisplayName("Should get discount percentage")
    void testGetDiscountPercentage() {
        // Given
        strategy = new DiscountPricingStrategy(15.0);

        // When
        double discount = strategy.getDiscountPercentage();

        // Then
        assertEquals(15.0, discount, 0.01);
    }

    @Test
    @DisplayName("Should set discount percentage")
    void testSetDiscountPercentage() {
        // Given
        strategy = new DiscountPricingStrategy(10.0);
        assertEquals(10.0, strategy.getDiscountPercentage(), 0.01);

        // When
        strategy.setDiscountPercentage(25.0);

        // Then
        assertEquals(25.0, strategy.getDiscountPercentage(), 0.01);
    }

    @Test
    @DisplayName("Should calculate price with updated discount percentage")
    void testCalculatePrice_AfterUpdatingDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(10.0);
        double price1 = strategy.calculatePrice(mockOrder);
        assertEquals(990.0, price1, 0.01);

        // When
        strategy.setDiscountPercentage(20.0);
        double price2 = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(880.0, price2, 0.01);
    }

    @Test
    @DisplayName("Should handle discount with no delivery cost")
    void testCalculatePrice_NoDelivery() {
        // Given
        strategy = new DiscountPricingStrategy(10.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(0.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 0, Total: 1000, Discount 10%: 900
        assertEquals(900.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle discount with high delivery cost")
    void testCalculatePrice_HighDeliveryCost() {
        // Given
        strategy = new DiscountPricingStrategy(15.0);
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(300.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 500, Delivery: 300, Total: 800, Discount 15%: 680
        assertEquals(680.0, price, 0.01);
    }

    @Test
    @DisplayName("Should apply discount to total including delivery")
    void testCalculatePrice_DiscountIncludesDelivery() {
        // Given
        strategy = new DiscountPricingStrategy(10.0);
        when(mockOrder.getPrice()).thenReturn(1000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(200.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Discount applies to (base + delivery)
        // Base: 1000, Delivery: 200, Total: 1200, Discount 10%: 1080
        assertEquals(1080.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle small order with discount")
    void testCalculatePrice_SmallOrder() {
        // Given
        strategy = new DiscountPricingStrategy(20.0);
        when(mockOrder.getPrice()).thenReturn(100.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(50.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 100, Delivery: 50, Total: 150, Discount 20%: 120
        assertEquals(120.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle large order with discount")
    void testCalculatePrice_LargeOrder() {
        // Given
        strategy = new DiscountPricingStrategy(25.0);
        when(mockOrder.getPrice()).thenReturn(5000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(200.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 5000, Delivery: 200, Total: 5200, Discount 25%: 3900
        assertEquals(3900.0, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate correctly with decimal discount percentage")
    void testCalculatePrice_DecimalDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(12.5);
        when(mockOrder.getPrice()).thenReturn(800.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(200.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 800, Delivery: 200, Total: 1000, Discount 12.5%: 875
        assertEquals(875.0, price, 0.01);
    }

    @Test
    @DisplayName("Should implement PriceCalculationStrategy interface")
    void testImplementsInterface() {
        // Given
        strategy = new DiscountPricingStrategy(10.0);

        // Then
        assertInstanceOf(PriceCalculationStrategy.class, strategy);
    }

    @Test
    @DisplayName("Should handle 100% discount")
    void testCalculatePrice_100PercentDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 100, Total: 1100, Discount 100%: 0
        assertEquals(0.0, price, 0.01);
    }

    @Test
    @DisplayName("Should handle fractional discount percentages")
    void testCalculatePrice_FractionalDiscount() {
        // Given
        strategy = new DiscountPricingStrategy(7.5);
        when(mockOrder.getPrice()).thenReturn(1000.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(0.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Base: 1000, Delivery: 0, Total: 1000, Discount 7.5%: 925
        assertEquals(925.0, price, 0.01);
    }

    @Test
    @DisplayName("Should calculate price consistently")
    void testCalculatePrice_Consistency() {
        // Given
        strategy = new DiscountPricingStrategy(15.0);

        // When
        double price1 = strategy.calculatePrice(mockOrder);
        double price2 = strategy.calculatePrice(mockOrder);
        double price3 = strategy.calculatePrice(mockOrder);

        // Then
        assertEquals(price1, price2, 0.01);
        assertEquals(price2, price3, 0.01);
    }

    @Test
    @DisplayName("Should verify discount calculation formula")
    void testCalculatePrice_VerifyFormula() {
        // Given
        strategy = new DiscountPricingStrategy(30.0);
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.calculateDeliveryCost()).thenReturn(100.0);

        // When
        double price = strategy.calculatePrice(mockOrder);

        // Then
        // Formula: (basePrice + deliveryCost) * (1 - discount/100)
        // (500 + 100) * (1 - 30/100) = 600 * 0.7 = 420
        assertEquals(420.0, price, 0.01);
        verify(mockOrder).getPrice();
        verify(mockOrder).calculateDeliveryCost();
    }
}
