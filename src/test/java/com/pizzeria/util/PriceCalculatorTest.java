package com.pizzeria.util;

import com.pizzeria.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PriceCalculator
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PriceCalculator Tests")
class PriceCalculatorTest {

    @Mock
    private Order mockOrder;

    @Test
    @DisplayName("Should calculate price with tax correctly")
    void testCalculateWithTax() {
        // Given
        double price = 1000.0;
        double taxRate = 13.0;

        // When
        double result = PriceCalculator.calculateWithTax(price, taxRate);

        // Then
        assertEquals(1130.0, result, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 10.0, 110.0",
        "500.0, 13.0, 565.0",
        "1000.0, 20.0, 1200.0",
        "250.0, 5.0, 262.5"
    })
    @DisplayName("Should calculate price with various tax rates")
    void testCalculateWithTax_VariousRates(double price, double taxRate, double expected) {
        // When
        double result = PriceCalculator.calculateWithTax(price, taxRate);

        // Then
        assertEquals(expected, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with zero tax")
    void testCalculateWithTax_ZeroTax() {
        // Given
        double price = 1000.0;
        double taxRate = 0.0;

        // When
        double result = PriceCalculator.calculateWithTax(price, taxRate);

        // Then
        assertEquals(1000.0, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with discount correctly")
    void testCalculateWithDiscount() {
        // Given
        double price = 1000.0;
        double discountPercentage = 10.0;

        // When
        double result = PriceCalculator.calculateWithDiscount(price, discountPercentage);

        // Then
        assertEquals(900.0, result, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 10.0, 90.0",
        "500.0, 20.0, 400.0",
        "1000.0, 50.0, 500.0",
        "750.0, 25.0, 562.5"
    })
    @DisplayName("Should calculate price with various discounts")
    void testCalculateWithDiscount_VariousDiscounts(double price, double discount, double expected) {
        // When
        double result = PriceCalculator.calculateWithDiscount(price, discount);

        // Then
        assertEquals(expected, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with zero discount")
    void testCalculateWithDiscount_ZeroDiscount() {
        // Given
        double price = 1000.0;
        double discountPercentage = 0.0;

        // When
        double result = PriceCalculator.calculateWithDiscount(price, discountPercentage);

        // Then
        assertEquals(1000.0, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate price with 100% discount")
    void testCalculateWithDiscount_FullDiscount() {
        // Given
        double price = 1000.0;
        double discountPercentage = 100.0;

        // When
        double result = PriceCalculator.calculateWithDiscount(price, discountPercentage);

        // Then
        assertEquals(0.0, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate tip correctly")
    void testCalculateTip() {
        // Given
        double amount = 1000.0;
        double tipPercentage = 15.0;

        // When
        double result = PriceCalculator.calculateTip(amount, tipPercentage);

        // Then
        assertEquals(150.0, result, 0.01);
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 10.0, 10.0",
        "500.0, 15.0, 75.0",
        "1000.0, 20.0, 200.0",
        "250.0, 5.0, 12.5"
    })
    @DisplayName("Should calculate tip with various percentages")
    void testCalculateTip_VariousPercentages(double amount, double tipPercentage, double expected) {
        // When
        double result = PriceCalculator.calculateTip(amount, tipPercentage);

        // Then
        assertEquals(expected, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate zero tip")
    void testCalculateTip_ZeroTip() {
        // Given
        double amount = 1000.0;
        double tipPercentage = 0.0;

        // When
        double result = PriceCalculator.calculateTip(amount, tipPercentage);

        // Then
        assertEquals(0.0, result, 0.01);
    }

    @Test
    @DisplayName("Should round to two decimals correctly")
    void testRoundToTwoDecimals() {
        // When & Then
        assertEquals(10.12, PriceCalculator.roundToTwoDecimals(10.123), 0.01);
        assertEquals(10.13, PriceCalculator.roundToTwoDecimals(10.126), 0.01);
        assertEquals(10.0, PriceCalculator.roundToTwoDecimals(10.001), 0.01);
        assertEquals(10.99, PriceCalculator.roundToTwoDecimals(10.994), 0.01);
    }

    @Test
    @DisplayName("Should round to two decimals - edge cases")
    void testRoundToTwoDecimals_EdgeCases() {
        // When & Then
        assertEquals(0.0, PriceCalculator.roundToTwoDecimals(0.0), 0.01);
        assertEquals(0.01, PriceCalculator.roundToTwoDecimals(0.005), 0.001);
        assertEquals(99.99, PriceCalculator.roundToTwoDecimals(99.994), 0.01);
    }

    @Test
    @DisplayName("Should calculate order total with tax and tip")
    void testCalculateOrderTotal() {
        // Given
        when(mockOrder.getPrice()).thenReturn(1000.0);
        double taxRate = 13.0;
        double tipPercentage = 10.0;

        // When
        double result = PriceCalculator.calculateOrderTotal(mockOrder, taxRate, tipPercentage);

        // Then
        // Base: 1000, with 13% tax: 1130, with 10% tip on 1130: 113, Total: 1243
        assertEquals(1243.0, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate order total with zero tax and tip")
    void testCalculateOrderTotal_ZeroTaxAndTip() {
        // Given
        when(mockOrder.getPrice()).thenReturn(500.0);
        double taxRate = 0.0;
        double tipPercentage = 0.0;

        // When
        double result = PriceCalculator.calculateOrderTotal(mockOrder, taxRate, tipPercentage);

        // Then
        assertEquals(500.0, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate order total with only tax")
    void testCalculateOrderTotal_OnlyTax() {
        // Given
        when(mockOrder.getPrice()).thenReturn(1000.0);
        double taxRate = 10.0;
        double tipPercentage = 0.0;

        // When
        double result = PriceCalculator.calculateOrderTotal(mockOrder, taxRate, tipPercentage);

        // Then
        // Base: 1000, with 10% tax: 1100, no tip: 1100
        assertEquals(1100.0, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate order total with only tip")
    void testCalculateOrderTotal_OnlyTip() {
        // Given
        when(mockOrder.getPrice()).thenReturn(1000.0);
        double taxRate = 0.0;
        double tipPercentage = 15.0;

        // When
        double result = PriceCalculator.calculateOrderTotal(mockOrder, taxRate, tipPercentage);

        // Then
        // Base: 1000, no tax: 1000, with 15% tip: 150, Total: 1150
        assertEquals(1150.0, result, 0.01);
    }

    @Test
    @DisplayName("Should format price correctly")
    void testFormatPrice() {
        // When & Then
        assertEquals("100.00 руб.", PriceCalculator.formatPrice(100.0));
        assertEquals("123.45 руб.", PriceCalculator.formatPrice(123.45));
        assertEquals("0.50 руб.", PriceCalculator.formatPrice(0.5));
    }

    @Test
    @DisplayName("Should format price with rounding")
    void testFormatPrice_WithRounding() {
        // When & Then
        assertEquals("100.12 руб.", PriceCalculator.formatPrice(100.123));
        assertEquals("100.13 руб.", PriceCalculator.formatPrice(100.126));
    }

    @Test
    @DisplayName("Should format large prices")
    void testFormatPrice_LargePrices() {
        // When & Then
        assertEquals("9999.99 руб.", PriceCalculator.formatPrice(9999.99));
        assertEquals("12345.67 руб.", PriceCalculator.formatPrice(12345.67));
    }

    @Test
    @DisplayName("Should format zero price")
    void testFormatPrice_Zero() {
        // When & Then
        assertEquals("0.00 руб.", PriceCalculator.formatPrice(0.0));
    }

    @Test
    @DisplayName("Should calculate with tax and round correctly")
    void testCalculateWithTax_Rounding() {
        // Given
        double price = 100.0;
        double taxRate = 13.5;

        // When
        double result = PriceCalculator.calculateWithTax(price, taxRate);

        // Then
        assertEquals(113.5, result, 0.01);
    }

    @Test
    @DisplayName("Should calculate with discount and round correctly")
    void testCalculateWithDiscount_Rounding() {
        // Given
        double price = 123.0;
        double discountPercentage = 12.5;

        // When
        double result = PriceCalculator.calculateWithDiscount(price, discountPercentage);

        // Then
        assertEquals(107.625, result, 0.001);
    }

    @Test
    @DisplayName("Should handle very small amounts")
    void testCalculateWithTax_SmallAmounts() {
        // Given
        double price = 0.01;
        double taxRate = 10.0;

        // When
        double result = PriceCalculator.calculateWithTax(price, taxRate);

        // Then
        assertEquals(0.011, result, 0.001);
    }

    @Test
    @DisplayName("Should handle very large amounts")
    void testCalculateWithTax_LargeAmounts() {
        // Given
        double price = 999999.99;
        double taxRate = 10.0;

        // When
        double result = PriceCalculator.calculateWithTax(price, taxRate);

        // Then
        assertEquals(1099999.989, result, 0.01);
    }

    @Test
    @DisplayName("Should verify order total calculation formula")
    void testCalculateOrderTotal_VerifyFormula() {
        // Given
        when(mockOrder.getPrice()).thenReturn(500.0);
        double taxRate = 10.0;
        double tipPercentage = 15.0;

        // When
        double result = PriceCalculator.calculateOrderTotal(mockOrder, taxRate, tipPercentage);

        // Then
        // Step 1: Calculate with tax: 500 * 1.10 = 550
        // Step 2: Calculate tip: 550 * 0.15 = 82.5
        // Step 3: Total: 550 + 82.5 = 632.5
        assertEquals(632.5, result, 0.01);
        verify(mockOrder).getPrice();
    }

    @Test
    @DisplayName("Should round order total to two decimals")
    void testCalculateOrderTotal_Rounding() {
        // Given
        when(mockOrder.getPrice()).thenReturn(333.33);
        double taxRate = 13.0;
        double tipPercentage = 10.0;

        // When
        double result = PriceCalculator.calculateOrderTotal(mockOrder, taxRate, tipPercentage);

        // Then - should be rounded to 2 decimals
        assertTrue(result > 0);
        String formatted = String.format("%.2f", result);
        assertEquals(result, Double.parseDouble(formatted), 0.01);
    }
}
