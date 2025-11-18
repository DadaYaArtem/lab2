package com.pizzeria.factory;

import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.model.ingredients.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for IngredientFactory
 */
@DisplayName("IngredientFactory Tests")
class IngredientFactoryTest {

    private IngredientFactory ingredientFactory;

    @BeforeEach
    void setUp() {
        ingredientFactory = new IngredientFactory();
    }

    @Test
    @DisplayName("Should create cheese successfully")
    void testCreateCheese_Success() throws InvalidPriceException {
        // When
        Cheese cheese = ingredientFactory.createCheese("Mozzarella");

        // Then
        assertNotNull(cheese);
        assertEquals("Mozzarella", cheese.getName());
        assertEquals(50.0, cheese.getPrice(), 0.01);
        assertEquals(100, cheese.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mozzarella", "Parmesan", "Cheddar", "Gouda", "Blue Cheese"})
    @DisplayName("Should create different cheese types")
    void testCreateCheese_DifferentTypes(String type) throws InvalidPriceException {
        // When
        Cheese cheese = ingredientFactory.createCheese(type);

        // Then
        assertNotNull(cheese);
        assertEquals(type, cheese.getName());
        assertEquals(50.0, cheese.getPrice(), 0.01);
        assertEquals(100, cheese.getQuantity());
    }

    @Test
    @DisplayName("Should create meat successfully")
    void testCreateMeat_Success() throws InvalidPriceException {
        // When
        Meat meat = ingredientFactory.createMeat("Pepperoni");

        // Then
        assertNotNull(meat);
        assertEquals("Pepperoni", meat.getName());
        assertEquals(120.0, meat.getPrice(), 0.01);
        assertEquals(80, meat.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pepperoni", "Salami", "Ham", "Bacon", "Chicken"})
    @DisplayName("Should create different meat types")
    void testCreateMeat_DifferentTypes(String type) throws InvalidPriceException {
        // When
        Meat meat = ingredientFactory.createMeat(type);

        // Then
        assertNotNull(meat);
        assertEquals(type, meat.getName());
        assertEquals(120.0, meat.getPrice(), 0.01);
        assertEquals(80, meat.getQuantity());
    }

    @Test
    @DisplayName("Should create vegetable successfully")
    void testCreateVegetable_Success() throws InvalidPriceException {
        // When
        Vegetable vegetable = ingredientFactory.createVegetable("Tomato");

        // Then
        assertNotNull(vegetable);
        assertEquals("Tomato", vegetable.getName());
        assertEquals(30.0, vegetable.getPrice(), 0.01);
        assertEquals(150, vegetable.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tomato", "Onion", "Mushroom", "Bell Pepper", "Olives"})
    @DisplayName("Should create different vegetable types")
    void testCreateVegetable_DifferentTypes(String type) throws InvalidPriceException {
        // When
        Vegetable vegetable = ingredientFactory.createVegetable(type);

        // Then
        assertNotNull(vegetable);
        assertEquals(type, vegetable.getName());
        assertEquals(30.0, vegetable.getPrice(), 0.01);
        assertEquals(150, vegetable.getQuantity());
    }

    @Test
    @DisplayName("Should create sauce successfully")
    void testCreateSauce_Success() throws InvalidPriceException {
        // When
        Sauce sauce = ingredientFactory.createSauce("Tomato Sauce");

        // Then
        assertNotNull(sauce);
        assertEquals("Tomato Sauce", sauce.getName());
        assertEquals(40.0, sauce.getPrice(), 0.01);
        assertEquals(200, sauce.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Tomato Sauce", "BBQ Sauce", "White Sauce", "Pesto", "Ranch"})
    @DisplayName("Should create different sauce types")
    void testCreateSauce_DifferentTypes(String type) throws InvalidPriceException {
        // When
        Sauce sauce = ingredientFactory.createSauce(type);

        // Then
        assertNotNull(sauce);
        assertEquals(type, sauce.getName());
        assertEquals(40.0, sauce.getPrice(), 0.01);
        assertEquals(200, sauce.getQuantity());
    }

    @Test
    @DisplayName("Should create dough successfully")
    void testCreateDough_Success() throws InvalidPriceException {
        // When
        Dough dough = ingredientFactory.createDough("Thin Crust");

        // Then
        assertNotNull(dough);
        assertEquals("Thin Crust", dough.getName());
        assertEquals(25.0, dough.getPrice(), 0.01);
        assertEquals(120, dough.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Thin Crust", "Thick Crust", "Stuffed Crust", "Gluten Free", "Whole Wheat"})
    @DisplayName("Should create different dough types")
    void testCreateDough_DifferentTypes(String type) throws InvalidPriceException {
        // When
        Dough dough = ingredientFactory.createDough(type);

        // Then
        assertNotNull(dough);
        assertEquals(type, dough.getName());
        assertEquals(25.0, dough.getPrice(), 0.01);
        assertEquals(120, dough.getQuantity());
    }

    @Test
    @DisplayName("Should create ingredient using generic method - cheese")
    void testCreateIngredient_Cheese() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("cheese", "Mozzarella");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Cheese.class, ingredient);
        assertEquals("Mozzarella", ingredient.getName());
    }

    @Test
    @DisplayName("Should create ingredient using generic method - meat")
    void testCreateIngredient_Meat() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("meat", "Pepperoni");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Meat.class, ingredient);
        assertEquals("Pepperoni", ingredient.getName());
    }

    @Test
    @DisplayName("Should create ingredient using generic method - vegetable")
    void testCreateIngredient_Vegetable() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("vegetable", "Tomato");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Vegetable.class, ingredient);
        assertEquals("Tomato", ingredient.getName());
    }

    @Test
    @DisplayName("Should create ingredient using generic method - sauce")
    void testCreateIngredient_Sauce() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("sauce", "BBQ");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Sauce.class, ingredient);
        assertEquals("BBQ", ingredient.getName());
    }

    @Test
    @DisplayName("Should create ingredient using generic method - dough")
    void testCreateIngredient_Dough() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("dough", "Thin Crust");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Dough.class, ingredient);
        assertEquals("Thin Crust", ingredient.getName());
    }

    @Test
    @DisplayName("Should create ingredient with Russian category name - cheese")
    void testCreateIngredient_Russian_Cheese() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("сыр", "Mozzarella");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Cheese.class, ingredient);
    }

    @Test
    @DisplayName("Should create ingredient with Russian category name - meat")
    void testCreateIngredient_Russian_Meat() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("мясо", "Pepperoni");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Meat.class, ingredient);
    }

    @Test
    @DisplayName("Should create ingredient with Russian category name - vegetable")
    void testCreateIngredient_Russian_Vegetable() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("овощи", "Tomato");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Vegetable.class, ingredient);
    }

    @Test
    @DisplayName("Should create ingredient with Russian category name - sauce")
    void testCreateIngredient_Russian_Sauce() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("соус", "BBQ");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Sauce.class, ingredient);
    }

    @Test
    @DisplayName("Should create ingredient with Russian category name - dough")
    void testCreateIngredient_Russian_Dough() throws InvalidPriceException {
        // When
        Ingredient ingredient = ingredientFactory.createIngredient("тесто", "Thin");

        // Then
        assertNotNull(ingredient);
        assertInstanceOf(Dough.class, ingredient);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unknown category")
    void testCreateIngredient_UnknownCategory() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> ingredientFactory.createIngredient("unknown", "Something")
        );
        assertTrue(exception.getMessage().contains("Неизвестная категория"));
        assertTrue(exception.getMessage().contains("unknown"));
    }

    @Test
    @DisplayName("Should handle case-insensitive category names")
    void testCreateIngredient_CaseInsensitive() throws InvalidPriceException {
        // When
        Ingredient ingredient1 = ingredientFactory.createIngredient("CHEESE", "Mozzarella");
        Ingredient ingredient2 = ingredientFactory.createIngredient("ChEeSe", "Cheddar");
        Ingredient ingredient3 = ingredientFactory.createIngredient("cheese", "Parmesan");

        // Then
        assertInstanceOf(Cheese.class, ingredient1);
        assertInstanceOf(Cheese.class, ingredient2);
        assertInstanceOf(Cheese.class, ingredient3);
    }

    @Test
    @DisplayName("Should verify correct pricing for all ingredient types")
    void testCreateIngredient_Pricing() throws InvalidPriceException {
        // When
        Cheese cheese = ingredientFactory.createCheese("Mozzarella");
        Meat meat = ingredientFactory.createMeat("Pepperoni");
        Vegetable vegetable = ingredientFactory.createVegetable("Tomato");
        Sauce sauce = ingredientFactory.createSauce("BBQ");
        Dough dough = ingredientFactory.createDough("Thin");

        // Then
        assertEquals(50.0, cheese.getPrice(), 0.01);
        assertEquals(120.0, meat.getPrice(), 0.01);
        assertEquals(30.0, vegetable.getPrice(), 0.01);
        assertEquals(40.0, sauce.getPrice(), 0.01);
        assertEquals(25.0, dough.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should verify correct quantity for all ingredient types")
    void testCreateIngredient_Quantity() throws InvalidPriceException {
        // When
        Cheese cheese = ingredientFactory.createCheese("Mozzarella");
        Meat meat = ingredientFactory.createMeat("Pepperoni");
        Vegetable vegetable = ingredientFactory.createVegetable("Tomato");
        Sauce sauce = ingredientFactory.createSauce("BBQ");
        Dough dough = ingredientFactory.createDough("Thin");

        // Then
        assertEquals(100, cheese.getQuantity());
        assertEquals(80, meat.getQuantity());
        assertEquals(150, vegetable.getQuantity());
        assertEquals(200, sauce.getQuantity());
        assertEquals(120, dough.getQuantity());
    }

    @Test
    @DisplayName("Should create multiple ingredients of same type")
    void testCreateMultipleIngredients() throws InvalidPriceException {
        // When
        Cheese cheese1 = ingredientFactory.createCheese("Mozzarella");
        Cheese cheese2 = ingredientFactory.createCheese("Cheddar");
        Cheese cheese3 = ingredientFactory.createCheese("Parmesan");

        // Then
        assertNotNull(cheese1);
        assertNotNull(cheese2);
        assertNotNull(cheese3);
        assertNotSame(cheese1, cheese2);
        assertNotSame(cheese2, cheese3);
    }
}
