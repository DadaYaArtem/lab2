package com.pizzeria.model.products;

import com.pizzeria.exceptions.InvalidPaymentException;
import com.pizzeria.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Dessert class
 */
@DisplayName("Dessert Tests")
class DessertTest {

    private Dessert tiramisu;
    private Dessert cheesecake;
    private Dessert brownie;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        tiramisu = new Dessert("Тирамису", 250.0, 150);
        cheesecake = new Dessert("Чизкейк", 200.0, 120);
        brownie = new Dessert("Брауни", 180.0, 100);
    }

    @Test
    @DisplayName("Should create dessert with name, price, and weight")
    void testCreateDessert() {
        assertNotNull(tiramisu);
        assertEquals("Тирамису", tiramisu.getName());
        assertEquals(250.0, tiramisu.getPrice(), 0.01);
        assertEquals(150, tiramisu.getWeight());
    }

    @Test
    @DisplayName("Should return correct name")
    void testGetName() {
        assertEquals("Тирамису", tiramisu.getName());
        assertEquals("Чизкейк", cheesecake.getName());
        assertEquals("Брауни", brownie.getName());
    }

    @Test
    @DisplayName("Should return correct price")
    void testGetPrice() {
        assertEquals(250.0, tiramisu.getPrice(), 0.01);
        assertEquals(200.0, cheesecake.getPrice(), 0.01);
        assertEquals(180.0, brownie.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should return correct weight")
    void testGetWeight() {
        assertEquals(150, tiramisu.getWeight());
        assertEquals(120, cheesecake.getWeight());
        assertEquals(100, brownie.getWeight());
    }

    @Test
    @DisplayName("Should throw InvalidPriceException for negative price")
    void testInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> {
            new Dessert("Test", -100.0, 150);
        });
    }

    @Test
    @DisplayName("Should calculate calories based on weight")
    void testCaloriesCalculation() {
        // weight * 3
        assertEquals(450, tiramisu.getCalories()); // 150 * 3
        assertEquals(360, cheesecake.getCalories()); // 120 * 3
        assertEquals(300, brownie.getCalories()); // 100 * 3
    }

    @Test
    @DisplayName("Should be sweet by default")
    void testIsSweetByDefault() {
        assertTrue(tiramisu.isSweet());
        assertTrue(cheesecake.isSweet());
    }

    @Test
    @DisplayName("Should not contain nuts by default")
    void testNoNutsByDefault() {
        assertFalse(tiramisu.isContainsNuts());
        assertFalse(cheesecake.isContainsNuts());
    }

    @Test
    @DisplayName("Should set and get sweet property")
    void testSetSweet() {
        tiramisu.setSweet(false);
        assertFalse(tiramisu.isSweet());
    }

    @Test
    @DisplayName("Should set and get contains nuts property")
    void testSetContainsNuts() {
        brownie.setContainsNuts(true);
        assertTrue(brownie.isContainsNuts());
    }

    @Test
    @DisplayName("Should not be suitable for diabetics when sweet")
    void testNotSuitableForDiabeticsWhenSweet() {
        assertTrue(tiramisu.isSweet());
        assertFalse(tiramisu.isSuitableForDiabetics());
    }

    @Test
    @DisplayName("Should be suitable for diabetics when not sweet")
    void testSuitableForDiabeticsWhenNotSweet() {
        tiramisu.setSweet(false);
        assertTrue(tiramisu.isSuitableForDiabetics());
    }

    @Test
    @DisplayName("Should return preparation time of 5 minutes")
    void testPreparationTime() {
        assertEquals(5, tiramisu.getPreparationTime());
        assertEquals(5, cheesecake.getPreparationTime());
    }

    @Test
    @DisplayName("Should process payment with sufficient amount")
    void testProcessPaymentSuccess() {
        assertDoesNotThrow(() -> {
            assertTrue(tiramisu.processPayment(250.0));
            assertTrue(tiramisu.processPayment(300.0));
        });
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for insufficient amount")
    void testProcessPaymentInsufficientAmount() {
        assertThrows(InvalidPaymentException.class, () -> {
            tiramisu.processPayment(200.0);
        });
    }

    @Test
    @DisplayName("Should apply discount correctly")
    void testApplyDiscount() {
        tiramisu.applyDiscount(20.0);
        assertEquals(200.0, tiramisu.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should set and get weight")
    void testSetWeight() {
        tiramisu.setWeight(200);
        assertEquals(200, tiramisu.getWeight());
    }

    @Test
    @DisplayName("Should be available by default")
    void testAvailableByDefault() {
        assertTrue(tiramisu.isAvailable());
    }

    @Test
    @DisplayName("Should calculate final price without discount")
    void testFinalPriceWithoutDiscount() {
        assertEquals(250.0, tiramisu.getFinalPrice(), 0.01);
    }
}
