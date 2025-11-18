package com.pizzeria.model.products;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MargheritaPizza class
 */
@DisplayName("MargheritaPizza Tests")
class MargheritaPizzaTest {

    private MargheritaPizza smallPizza;
    private MargheritaPizza mediumPizza;
    private MargheritaPizza largePizza;
    private MargheritaPizza extraLargePizza;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        smallPizza = new MargheritaPizza(PizzaSize.SMALL);
        mediumPizza = new MargheritaPizza(PizzaSize.MEDIUM);
        largePizza = new MargheritaPizza(PizzaSize.LARGE);
        extraLargePizza = new MargheritaPizza(PizzaSize.EXTRA_LARGE);
    }

    @Test
    @DisplayName("Should create MargheritaPizza with SMALL size")
    void testCreateSmallPizza() {
        assertNotNull(smallPizza);
        assertEquals(PizzaSize.SMALL, smallPizza.getSize());
        assertEquals("Маргарита", smallPizza.getName());
    }

    @Test
    @DisplayName("Should create MargheritaPizza with MEDIUM size")
    void testCreateMediumPizza() {
        assertNotNull(mediumPizza);
        assertEquals(PizzaSize.MEDIUM, mediumPizza.getSize());
        assertEquals("Маргарита", mediumPizza.getName());
    }

    @Test
    @DisplayName("Should create MargheritaPizza with LARGE size")
    void testCreateLargePizza() {
        assertNotNull(largePizza);
        assertEquals(PizzaSize.LARGE, largePizza.getSize());
        assertEquals("Маргарита", largePizza.getName());
    }

    @Test
    @DisplayName("Should create MargheritaPizza with EXTRA_LARGE size")
    void testCreateExtraLargePizza() {
        assertNotNull(extraLargePizza);
        assertEquals(PizzaSize.EXTRA_LARGE, extraLargePizza.getSize());
        assertEquals("Маргарита", extraLargePizza.getName());
    }

    @Test
    @DisplayName("Should calculate correct price for SMALL size")
    void testSmallPizzaPrice() {
        // base price 300.0, SMALL multiplier 1.0
        assertEquals(300.0, smallPizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for MEDIUM size")
    void testMediumPizzaPrice() {
        // base price 300.0, MEDIUM multiplier 1.5
        assertEquals(450.0, mediumPizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for LARGE size")
    void testLargePizzaPrice() {
        // base price 300.0, LARGE multiplier 2.0
        assertEquals(600.0, largePizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for EXTRA_LARGE size")
    void testExtraLargePizzaPrice() {
        // base price 300.0, EXTRA_LARGE multiplier 2.5
        assertEquals(750.0, extraLargePizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should return correct name")
    void testGetName() {
        assertEquals("Маргарита", smallPizza.getName());
        assertEquals("Маргарита", mediumPizza.getName());
        assertEquals("Маргарита", largePizza.getName());
    }

    @Test
    @DisplayName("Should return description with ingredients")
    void testGetDescription() {
        String description = smallPizza.getDescription();
        assertNotNull(description);
        assertTrue(description.contains("Классическая пицца") || description.contains("Ингредиенты"));
    }

    @Test
    @DisplayName("Should have non-null ingredient list")
    void testIngredientListNotNull() {
        assertNotNull(smallPizza.getIngredients());
        assertNotNull(mediumPizza.getIngredients());
        assertNotNull(largePizza.getIngredients());
    }

    @Test
    @DisplayName("Should return true for isVegetarian")
    void testIsVegetarian() {
        assertTrue(smallPizza.isVegetarian());
        assertTrue(mediumPizza.isVegetarian());
    }

    @Test
    @DisplayName("Should return correct origin")
    void testGetOrigin() {
        assertEquals("Италия, Неаполь", smallPizza.getOrigin());
    }

    @Test
    @DisplayName("Should return correct calories for SMALL size")
    void testSmallPizzaCalories() {
        assertEquals(800, smallPizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for MEDIUM size")
    void testMediumPizzaCalories() {
        assertEquals(1200, mediumPizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for LARGE size")
    void testLargePizzaCalories() {
        assertEquals(1600, largePizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for EXTRA_LARGE size")
    void testExtraLargePizzaCalories() {
        assertEquals(2000, extraLargePizza.getCalories());
    }

    @Test
    @DisplayName("Should have cooking time set")
    void testCookingTime() {
        assertEquals(18, smallPizza.getCookingTime());
    }

    @Test
    @DisplayName("Should not be ready initially")
    void testNotReadyInitially() {
        assertFalse(smallPizza.isReady());
    }

    @Test
    @DisplayName("Should be available by default")
    void testAvailableByDefault() {
        assertTrue(smallPizza.isAvailable());
    }

    @Test
    @DisplayName("Should apply discount correctly")
    void testApplyDiscount() {
        smallPizza.applyDiscount(10.0);
        assertEquals(270.0, smallPizza.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate final price without discount")
    void testFinalPriceWithoutDiscount() {
        assertEquals(300.0, smallPizza.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should process payment with sufficient amount")
    void testProcessPaymentWithSufficientAmount() {
        assertTrue(smallPizza.processPayment(300.0));
        assertTrue(smallPizza.processPayment(350.0));
    }

    @Test
    @DisplayName("Should not process payment with insufficient amount")
    void testProcessPaymentWithInsufficientAmount() {
        assertFalse(smallPizza.processPayment(250.0));
    }
}
