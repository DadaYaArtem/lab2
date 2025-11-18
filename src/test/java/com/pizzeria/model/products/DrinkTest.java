package com.pizzeria.model.products;

import com.pizzeria.exceptions.InvalidPaymentException;
import com.pizzeria.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Drink class
 */
@DisplayName("Drink Tests")
class DrinkTest {

    private Drink cola;
    private Drink juice;
    private Drink water;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        cola = new Drink("Кока-кола", 100.0, 500);
        juice = new Drink("Апельсиновый сок", 120.0, 300);
        water = new Drink("Минеральная вода", 50.0, 500);
    }

    @Test
    @DisplayName("Should create drink with name, price, and volume")
    void testCreateDrink() {
        assertNotNull(cola);
        assertEquals("Кока-кола", cola.getName());
        assertEquals(100.0, cola.getPrice(), 0.01);
        assertEquals(500, cola.getVolume());
    }

    @Test
    @DisplayName("Should return correct name")
    void testGetName() {
        assertEquals("Кока-кола", cola.getName());
        assertEquals("Апельсиновый сок", juice.getName());
        assertEquals("Минеральная вода", water.getName());
    }

    @Test
    @DisplayName("Should return correct price")
    void testGetPrice() {
        assertEquals(100.0, cola.getPrice(), 0.01);
        assertEquals(120.0, juice.getPrice(), 0.01);
        assertEquals(50.0, water.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should return correct volume")
    void testGetVolume() {
        assertEquals(500, cola.getVolume());
        assertEquals(300, juice.getVolume());
        assertEquals(500, water.getVolume());
    }

    @Test
    @DisplayName("Should throw InvalidPriceException for negative price")
    void testInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> {
            new Drink("Test", -50.0, 500);
        });
    }

    @Test
    @DisplayName("Should calculate calories for cola")
    void testColaCalories() {
        // Volume 500ml * 0.42 = 210 calories
        assertEquals(210, cola.getCalories());
    }

    @Test
    @DisplayName("Should calculate calories for juice")
    void testJuiceCalories() {
        // Volume 300ml * 0.45 = 135 calories
        assertEquals(135, juice.getCalories());
    }

    @Test
    @DisplayName("Should return zero calories for water")
    void testWaterCalories() {
        assertEquals(0, water.getCalories());
    }

    @Test
    @DisplayName("Should have default temperature of 20 degrees")
    void testDefaultTemperature() {
        assertEquals(20, cola.getTemperature());
    }

    @Test
    @DisplayName("Should not be carbonated by default")
    void testNotCarbonatedByDefault() {
        Drink testDrink = assertDoesNotThrow(() -> new Drink("Test", 50.0, 500));
        assertFalse(testDrink.isCarbonated());
    }

    @Test
    @DisplayName("Should not be alcoholic by default")
    void testNotAlcoholicByDefault() {
        assertFalse(cola.isAlcoholic());
        assertFalse(juice.isAlcoholic());
    }

    @Test
    @DisplayName("Should chill drink to 4 degrees")
    void testChillDrink() {
        cola.chill();
        assertEquals(4, cola.getTemperature());
    }

    @Test
    @DisplayName("Should be cold after chilling")
    void testIsColdAfterChilling() {
        cola.chill();
        assertTrue(cola.isCold());
    }

    @Test
    @DisplayName("Should not be cold at room temperature")
    void testNotColdAtRoomTemperature() {
        assertFalse(cola.isCold());
    }

    @Test
    @DisplayName("Should set carbonated property")
    void testSetCarbonated() {
        cola.setCarbonated(true);
        assertTrue(cola.isCarbonated());
    }

    @Test
    @DisplayName("Should set alcoholic property")
    void testSetAlcoholic() {
        Drink beer = assertDoesNotThrow(() -> new Drink("Beer", 150.0, 500));
        beer.setAlcoholic(true);
        assertTrue(beer.isAlcoholic());
    }

    @Test
    @DisplayName("Should return preparation time of 2 minutes")
    void testPreparationTime() {
        assertEquals(2, cola.getPreparationTime());
    }

    @Test
    @DisplayName("Should process payment with sufficient amount")
    void testProcessPaymentSuccess() {
        assertDoesNotThrow(() -> {
            assertTrue(cola.processPayment(100.0));
            assertTrue(cola.processPayment(150.0));
        });
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for insufficient amount")
    void testProcessPaymentInsufficientAmount() {
        assertThrows(InvalidPaymentException.class, () -> {
            cola.processPayment(50.0);
        });
    }

    @Test
    @DisplayName("Should apply discount correctly")
    void testApplyDiscount() {
        cola.applyDiscount(10.0);
        assertEquals(90.0, cola.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should set and get volume")
    void testSetVolume() {
        cola.setVolume(750);
        assertEquals(750, cola.getVolume());
    }

    @Test
    @DisplayName("Should set and get temperature")
    void testSetTemperature() {
        cola.setTemperature(5);
        assertEquals(5, cola.getTemperature());
    }

    @Test
    @DisplayName("Should be available by default")
    void testAvailableByDefault() {
        assertTrue(cola.isAvailable());
    }
}
