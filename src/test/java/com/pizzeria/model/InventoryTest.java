package com.pizzeria.model;

import com.pizzeria.exceptions.InsufficientIngredientsException;
import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.exceptions.OutOfStockException;
import com.pizzeria.model.ingredients.Cheese;
import com.pizzeria.model.ingredients.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Inventory class
 */
@DisplayName("Inventory Tests")
class InventoryTest {

    private Inventory inventory;
    private Ingredient cheese;
    private Ingredient tomato;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        inventory = new Inventory();
        cheese = new Cheese("Моцарелла", 5.0, "Моцарелла");
        cheese.setQuantity(100);

        tomato = new Cheese("Помидор", 2.0, "Томат"); // Using Cheese as a simple Ingredient
        tomato.setQuantity(50);
    }

    @Test
    @DisplayName("Should create inventory")
    void testCreateInventory() {
        assertNotNull(inventory);
        assertNotNull(inventory.getIngredients());
    }

    @Test
    @DisplayName("Should have empty ingredients initially")
    void testInitialIngredients() {
        assertEquals(0, inventory.getIngredients().size());
    }

    @Test
    @DisplayName("Should have default low stock threshold")
    void testDefaultLowStockThreshold() {
        assertEquals(10, inventory.getLowStockThreshold());
    }

    @Test
    @DisplayName("Should add ingredient")
    void testAddIngredient() {
        inventory.addIngredient(cheese);
        assertEquals(1, inventory.getIngredients().size());
        assertTrue(inventory.getIngredients().containsKey("Моцарелла"));
    }

    @Test
    @DisplayName("Should add multiple ingredients")
    void testAddMultipleIngredients() {
        inventory.addIngredient(cheese);
        inventory.addIngredient(tomato);

        assertEquals(2, inventory.getIngredients().size());
        assertTrue(inventory.getIngredients().containsKey("Моцарелла"));
        assertTrue(inventory.getIngredients().containsKey("Помидор"));
    }

    @Test
    @DisplayName("Should remove ingredient")
    void testRemoveIngredient() {
        inventory.addIngredient(cheese);
        assertEquals(1, inventory.getIngredients().size());

        inventory.removeIngredient("Моцарелла");
        assertEquals(0, inventory.getIngredients().size());
        assertFalse(inventory.getIngredients().containsKey("Моцарелла"));
    }

    @Test
    @DisplayName("Should check availability when ingredient exists and has sufficient quantity")
    void testCheckAvailabilitySuccess() {
        inventory.addIngredient(cheese);
        assertTrue(inventory.checkAvailability("Моцарелла", 50));
    }

    @Test
    @DisplayName("Should check availability when ingredient exists but has insufficient quantity")
    void testCheckAvailabilityInsufficientQuantity() {
        inventory.addIngredient(cheese);
        assertFalse(inventory.checkAvailability("Моцарелла", 150));
    }

    @Test
    @DisplayName("Should check availability returns false when ingredient doesn't exist")
    void testCheckAvailabilityNonExistent() {
        assertFalse(inventory.checkAvailability("Базилик", 10));
    }

    @Test
    @DisplayName("Should use ingredient and reduce quantity")
    void testUseIngredient() throws InsufficientIngredientsException {
        inventory.addIngredient(cheese);
        int initialQuantity = cheese.getQuantity();

        inventory.useIngredient("Моцарелла", 30);

        assertEquals(initialQuantity - 30, cheese.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when using ingredient that doesn't exist")
    void testUseNonExistentIngredient() {
        assertThrows(InsufficientIngredientsException.class, () -> {
            inventory.useIngredient("Базилик", 10);
        });
    }

    @Test
    @DisplayName("Should throw exception when using more than available")
    void testUseInsufficientIngredient() {
        inventory.addIngredient(cheese);
        assertThrows(InsufficientIngredientsException.class, () -> {
            inventory.useIngredient("Моцарелла", 150);
        });
    }

    @Test
    @DisplayName("Should restock ingredient")
    void testRestockIngredient() throws OutOfStockException {
        inventory.addIngredient(cheese);
        int initialQuantity = cheese.getQuantity();

        inventory.restockIngredient("Моцарелла", 50);

        assertEquals(initialQuantity + 50, cheese.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when restocking non-existent ingredient")
    void testRestockNonExistentIngredient() {
        assertThrows(OutOfStockException.class, () -> {
            inventory.restockIngredient("Базилик", 50);
        });
    }

    @Test
    @DisplayName("Should get low stock items")
    void testGetLowStockItems() throws InvalidPriceException {
        Ingredient lowStockIngredient = new Cheese("Базилик", 3.0, "Базилик");
        lowStockIngredient.setQuantity(5);

        inventory.addIngredient(cheese); // quantity 100, above threshold
        inventory.addIngredient(lowStockIngredient); // quantity 5, below threshold

        Map<String, Integer> lowStock = inventory.getLowStockItems();

        assertEquals(1, lowStock.size());
        assertTrue(lowStock.containsKey("Базилик"));
        assertEquals(5, lowStock.get("Базилик"));
    }

    @Test
    @DisplayName("Should return empty map when no low stock items")
    void testNoLowStockItems() {
        inventory.addIngredient(cheese); // quantity 100, above threshold

        Map<String, Integer> lowStock = inventory.getLowStockItems();

        assertEquals(0, lowStock.size());
    }

    @Test
    @DisplayName("Should calculate total inventory value")
    void testCalculateTotalInventoryValue() {
        inventory.addIngredient(cheese); // 100 * 5.0 = 500
        inventory.addIngredient(tomato); // 50 * 2.0 = 100

        double totalValue = inventory.calculateTotalInventoryValue();

        assertEquals(600.0, totalValue, 0.01);
    }

    @Test
    @DisplayName("Should return zero value for empty inventory")
    void testEmptyInventoryValue() {
        assertEquals(0.0, inventory.calculateTotalInventoryValue(), 0.01);
    }

    @Test
    @DisplayName("Should set and get low stock threshold")
    void testSetLowStockThreshold() {
        inventory.setLowStockThreshold(20);
        assertEquals(20, inventory.getLowStockThreshold());
    }

    @Test
    @DisplayName("Should identify low stock based on custom threshold")
    void testCustomLowStockThreshold() throws InvalidPriceException {
        inventory.setLowStockThreshold(60);

        inventory.addIngredient(tomato); // quantity 50, below threshold of 60

        Map<String, Integer> lowStock = inventory.getLowStockItems();

        assertEquals(1, lowStock.size());
        assertTrue(lowStock.containsKey("Помидор"));
    }

    @Test
    @DisplayName("Should handle multiple uses of same ingredient")
    void testMultipleUses() throws InsufficientIngredientsException {
        inventory.addIngredient(cheese);
        int initialQuantity = cheese.getQuantity();

        inventory.useIngredient("Моцарелла", 10);
        inventory.useIngredient("Моцарелла", 20);
        inventory.useIngredient("Моцарелла", 15);

        assertEquals(initialQuantity - 45, cheese.getQuantity());
    }

    @Test
    @DisplayName("Should handle restock after use")
    void testRestockAfterUse() throws InsufficientIngredientsException, OutOfStockException {
        inventory.addIngredient(cheese);
        int initialQuantity = cheese.getQuantity();

        inventory.useIngredient("Моцарелла", 30);
        inventory.restockIngredient("Моцарелла", 50);

        assertEquals(initialQuantity - 30 + 50, cheese.getQuantity());
    }
}
