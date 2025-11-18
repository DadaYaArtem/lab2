package com.pizzeria.model.products;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PepperoniPizza class
 */
@DisplayName("PepperoniPizza Tests")
class PepperoniPizzaTest {

    private PepperoniPizza smallPizza;
    private PepperoniPizza mediumPizza;
    private PepperoniPizza largePizza;
    private PepperoniPizza extraLargePizza;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        smallPizza = new PepperoniPizza(PizzaSize.SMALL);
        mediumPizza = new PepperoniPizza(PizzaSize.MEDIUM);
        largePizza = new PepperoniPizza(PizzaSize.LARGE);
        extraLargePizza = new PepperoniPizza(PizzaSize.EXTRA_LARGE);
    }

    @Test
    @DisplayName("Should create PepperoniPizza with SMALL size")
    void testCreateSmallPizza() {
        assertNotNull(smallPizza);
        assertEquals(PizzaSize.SMALL, smallPizza.getSize());
        assertEquals("Пепперони", smallPizza.getName());
    }

    @Test
    @DisplayName("Should create PepperoniPizza with MEDIUM size")
    void testCreateMediumPizza() {
        assertNotNull(mediumPizza);
        assertEquals(PizzaSize.MEDIUM, mediumPizza.getSize());
        assertEquals("Пепперони", mediumPizza.getName());
    }

    @Test
    @DisplayName("Should create PepperoniPizza with LARGE size")
    void testCreateLargePizza() {
        assertNotNull(largePizza);
        assertEquals(PizzaSize.LARGE, largePizza.getSize());
        assertEquals("Пепперони", largePizza.getName());
    }

    @Test
    @DisplayName("Should create PepperoniPizza with EXTRA_LARGE size")
    void testCreateExtraLargePizza() {
        assertNotNull(extraLargePizza);
        assertEquals(PizzaSize.EXTRA_LARGE, extraLargePizza.getSize());
        assertEquals("Пепперони", extraLargePizza.getName());
    }

    @Test
    @DisplayName("Should calculate correct price for SMALL size")
    void testSmallPizzaPrice() {
        // base price 400.0, SMALL multiplier 1.0
        assertEquals(400.0, smallPizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for MEDIUM size")
    void testMediumPizzaPrice() {
        // base price 400.0, MEDIUM multiplier 1.5
        assertEquals(600.0, mediumPizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for LARGE size")
    void testLargePizzaPrice() {
        // base price 400.0, LARGE multiplier 2.0
        assertEquals(800.0, largePizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for EXTRA_LARGE size")
    void testExtraLargePizzaPrice() {
        // base price 400.0, EXTRA_LARGE multiplier 2.5
        assertEquals(1000.0, extraLargePizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should return correct name")
    void testGetName() {
        assertEquals("Пепперони", smallPizza.getName());
        assertEquals("Пепперони", mediumPizza.getName());
        assertEquals("Пепперони", largePizza.getName());
    }

    @Test
    @DisplayName("Should return description with ingredients")
    void testGetDescription() {
        String description = smallPizza.getDescription();
        assertNotNull(description);
        assertTrue(description.contains("пепперони") || description.contains("Ингредиенты"));
    }

    @Test
    @DisplayName("Should have non-null ingredient list")
    void testIngredientListNotNull() {
        assertNotNull(smallPizza.getIngredients());
        assertNotNull(mediumPizza.getIngredients());
        assertNotNull(largePizza.getIngredients());
    }

    @Test
    @DisplayName("Should return true for isSpicy")
    void testIsSpicy() {
        assertTrue(smallPizza.isSpicy());
        assertTrue(mediumPizza.isSpicy());
    }

    @Test
    @DisplayName("Should return correct spicy level")
    void testGetSpicyLevel() {
        assertEquals(2, smallPizza.getSpicyLevel());
        assertEquals(2, mediumPizza.getSpicyLevel());
    }

    @Test
    @DisplayName("Should return correct calories for SMALL size")
    void testSmallPizzaCalories() {
        assertEquals(1000, smallPizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for MEDIUM size")
    void testMediumPizzaCalories() {
        assertEquals(1500, mediumPizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for LARGE size")
    void testLargePizzaCalories() {
        assertEquals(2000, largePizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for EXTRA_LARGE size")
    void testExtraLargePizzaCalories() {
        assertEquals(2500, extraLargePizza.getCalories());
    }

    @Test
    @DisplayName("Should have cooking time set")
    void testCookingTime() {
        assertEquals(20, smallPizza.getCookingTime());
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
        smallPizza.applyDiscount(20.0);
        assertEquals(320.0, smallPizza.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate final price without discount")
    void testFinalPriceWithoutDiscount() {
        assertEquals(400.0, smallPizza.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should process payment with sufficient amount")
    void testProcessPaymentWithSufficientAmount() {
        assertTrue(smallPizza.processPayment(400.0));
        assertTrue(smallPizza.processPayment(500.0));
    }

    @Test
    @DisplayName("Should not process payment with insufficient amount")
    void testProcessPaymentWithInsufficientAmount() {
        assertFalse(smallPizza.processPayment(350.0));
    }
}
