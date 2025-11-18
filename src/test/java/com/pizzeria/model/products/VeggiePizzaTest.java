package com.pizzeria.model.products;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for VeggiePizza class
 */
@DisplayName("VeggiePizza Tests")
class VeggiePizzaTest {

    private VeggiePizza smallPizza;
    private VeggiePizza mediumPizza;
    private VeggiePizza largePizza;
    private VeggiePizza extraLargePizza;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        smallPizza = new VeggiePizza(PizzaSize.SMALL);
        mediumPizza = new VeggiePizza(PizzaSize.MEDIUM);
        largePizza = new VeggiePizza(PizzaSize.LARGE);
        extraLargePizza = new VeggiePizza(PizzaSize.EXTRA_LARGE);
    }

    @Test
    @DisplayName("Should create VeggiePizza with SMALL size")
    void testCreateSmallPizza() {
        assertNotNull(smallPizza);
        assertEquals(PizzaSize.SMALL, smallPizza.getSize());
        assertEquals("Вегетарианская", smallPizza.getName());
    }

    @Test
    @DisplayName("Should create VeggiePizza with MEDIUM size")
    void testCreateMediumPizza() {
        assertNotNull(mediumPizza);
        assertEquals(PizzaSize.MEDIUM, mediumPizza.getSize());
        assertEquals("Вегетарианская", mediumPizza.getName());
    }

    @Test
    @DisplayName("Should create VeggiePizza with LARGE size")
    void testCreateLargePizza() {
        assertNotNull(largePizza);
        assertEquals(PizzaSize.LARGE, largePizza.getSize());
        assertEquals("Вегетарианская", largePizza.getName());
    }

    @Test
    @DisplayName("Should create VeggiePizza with EXTRA_LARGE size")
    void testCreateExtraLargePizza() {
        assertNotNull(extraLargePizza);
        assertEquals(PizzaSize.EXTRA_LARGE, extraLargePizza.getSize());
        assertEquals("Вегетарианская", extraLargePizza.getName());
    }

    @Test
    @DisplayName("Should calculate correct price for SMALL size")
    void testSmallPizzaPrice() {
        // base price 350.0, SMALL multiplier 1.0
        assertEquals(350.0, smallPizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for MEDIUM size")
    void testMediumPizzaPrice() {
        // base price 350.0, MEDIUM multiplier 1.5
        assertEquals(525.0, mediumPizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for LARGE size")
    void testLargePizzaPrice() {
        // base price 350.0, LARGE multiplier 2.0
        assertEquals(700.0, largePizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate correct price for EXTRA_LARGE size")
    void testExtraLargePizzaPrice() {
        // base price 350.0, EXTRA_LARGE multiplier 2.5
        assertEquals(875.0, extraLargePizza.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should return correct name")
    void testGetName() {
        assertEquals("Вегетарианская", smallPizza.getName());
        assertEquals("Вегетарианская", mediumPizza.getName());
        assertEquals("Вегетарианская", largePizza.getName());
    }

    @Test
    @DisplayName("Should return description with ingredients")
    void testGetDescription() {
        String description = smallPizza.getDescription();
        assertNotNull(description);
        assertTrue(description.contains("овощами") || description.contains("Ингредиенты"));
    }

    @Test
    @DisplayName("Should have non-null ingredient list")
    void testIngredientListNotNull() {
        assertNotNull(smallPizza.getIngredients());
        assertNotNull(mediumPizza.getIngredients());
        assertNotNull(largePizza.getIngredients());
    }

    @Test
    @DisplayName("Should return false for isVegan (contains cheese)")
    void testIsVegan() {
        assertFalse(smallPizza.isVegan());
        assertFalse(mediumPizza.isVegan());
    }

    @Test
    @DisplayName("Should return true for isHealthy")
    void testIsHealthy() {
        assertTrue(smallPizza.isHealthy());
        assertTrue(mediumPizza.isHealthy());
    }

    @Test
    @DisplayName("Should return correct calories for SMALL size")
    void testSmallPizzaCalories() {
        assertEquals(700, smallPizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for MEDIUM size")
    void testMediumPizzaCalories() {
        assertEquals(1000, mediumPizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for LARGE size")
    void testLargePizzaCalories() {
        assertEquals(1400, largePizza.getCalories());
    }

    @Test
    @DisplayName("Should return correct calories for EXTRA_LARGE size")
    void testExtraLargePizzaCalories() {
        assertEquals(1800, extraLargePizza.getCalories());
    }

    @Test
    @DisplayName("Should have cooking time set")
    void testCookingTime() {
        assertEquals(19, smallPizza.getCookingTime());
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
        smallPizza.applyDiscount(15.0);
        assertEquals(297.5, smallPizza.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should calculate final price without discount")
    void testFinalPriceWithoutDiscount() {
        assertEquals(350.0, smallPizza.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should process payment with sufficient amount")
    void testProcessPaymentWithSufficientAmount() {
        assertTrue(smallPizza.processPayment(350.0));
        assertTrue(smallPizza.processPayment(400.0));
    }

    @Test
    @DisplayName("Should not process payment with insufficient amount")
    void testProcessPaymentWithInsufficientAmount() {
        assertFalse(smallPizza.processPayment(300.0));
    }
}
