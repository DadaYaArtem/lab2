package com.pizzeria.factory;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InvalidPizzaSizeException;
import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.model.products.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PizzaFactory
 */
@DisplayName("PizzaFactory Tests")
class PizzaFactoryTest {

    private PizzaFactory pizzaFactory;

    @BeforeEach
    void setUp() {
        pizzaFactory = new PizzaFactory();
    }

    @Test
    @DisplayName("Should create Margherita pizza successfully")
    void testCreatePizza_Margherita() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("margherita", PizzaSize.MEDIUM);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(MargheritaPizza.class, pizza);
        assertEquals(PizzaSize.MEDIUM, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Margherita pizza with Russian name")
    void testCreatePizza_Margherita_Russian() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("маргарита", PizzaSize.LARGE);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(MargheritaPizza.class, pizza);
        assertEquals(PizzaSize.LARGE, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Pepperoni pizza successfully")
    void testCreatePizza_Pepperoni() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("pepperoni", PizzaSize.SMALL);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(PepperoniPizza.class, pizza);
        assertEquals(PizzaSize.SMALL, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Pepperoni pizza with Russian name")
    void testCreatePizza_Pepperoni_Russian() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("пепперони", PizzaSize.EXTRA_LARGE);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(PepperoniPizza.class, pizza);
        assertEquals(PizzaSize.EXTRA_LARGE, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Veggie pizza successfully")
    void testCreatePizza_Veggie() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("veggie", PizzaSize.MEDIUM);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(VeggiePizza.class, pizza);
        assertEquals(PizzaSize.MEDIUM, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Veggie pizza with Russian name")
    void testCreatePizza_Veggie_Russian() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("вегетарианская", PizzaSize.LARGE);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(VeggiePizza.class, pizza);
        assertEquals(PizzaSize.LARGE, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Meat Lovers pizza successfully")
    void testCreatePizza_MeatLovers() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("meat", PizzaSize.LARGE);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(MeatLoversPizza.class, pizza);
        assertEquals(PizzaSize.LARGE, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Meat Lovers pizza with Russian name")
    void testCreatePizza_MeatLovers_Russian() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("мясная", PizzaSize.MEDIUM);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(MeatLoversPizza.class, pizza);
        assertEquals(PizzaSize.MEDIUM, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Custom pizza successfully")
    void testCreatePizza_Custom() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("custom", PizzaSize.SMALL);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(CustomPizza.class, pizza);
        assertEquals(PizzaSize.SMALL, pizza.getSize());
    }

    @Test
    @DisplayName("Should create Custom pizza with Russian name")
    void testCreatePizza_Custom_Russian() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("пользовательская", PizzaSize.LARGE);

        // Then
        assertNotNull(pizza);
        assertInstanceOf(CustomPizza.class, pizza);
        assertEquals(PizzaSize.LARGE, pizza.getSize());
    }

    @ParameterizedTest
    @EnumSource(PizzaSize.class)
    @DisplayName("Should create pizza with all sizes")
    void testCreatePizza_AllSizes(PizzaSize size) throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("margherita", size);

        // Then
        assertNotNull(pizza);
        assertEquals(size, pizza.getSize());
    }

    @Test
    @DisplayName("Should throw InvalidPizzaSizeException when size is null")
    void testCreatePizza_NullSize() {
        // When & Then
        InvalidPizzaSizeException exception = assertThrows(
            InvalidPizzaSizeException.class,
            () -> pizzaFactory.createPizza("margherita", null)
        );
        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unknown pizza type")
    void testCreatePizza_UnknownType() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> pizzaFactory.createPizza("hawaiian", PizzaSize.MEDIUM)
        );
        assertTrue(exception.getMessage().contains("Неизвестный тип пиццы"));
        assertTrue(exception.getMessage().contains("hawaiian"));
    }

    @Test
    @DisplayName("Should handle case-insensitive pizza types")
    void testCreatePizza_CaseInsensitive() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza1 = pizzaFactory.createPizza("MARGHERITA", PizzaSize.MEDIUM);
        Pizza pizza2 = pizzaFactory.createPizza("MaRgHeRiTa", PizzaSize.MEDIUM);
        Pizza pizza3 = pizzaFactory.createPizza("margherita", PizzaSize.MEDIUM);

        // Then
        assertInstanceOf(MargheritaPizza.class, pizza1);
        assertInstanceOf(MargheritaPizza.class, pizza2);
        assertInstanceOf(MargheritaPizza.class, pizza3);
    }

    @Test
    @DisplayName("Should create default pizza as Medium Margherita")
    void testCreateDefaultPizza() throws InvalidPriceException {
        // When
        Pizza pizza = pizzaFactory.createDefaultPizza();

        // Then
        assertNotNull(pizza);
        assertInstanceOf(MargheritaPizza.class, pizza);
        assertEquals(PizzaSize.MEDIUM, pizza.getSize());
    }

    @Test
    @DisplayName("Should create large pizza successfully")
    void testCreateLargePizza() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createLargePizza("pepperoni");

        // Then
        assertNotNull(pizza);
        assertInstanceOf(PepperoniPizza.class, pizza);
        assertEquals(PizzaSize.LARGE, pizza.getSize());
    }

    @ParameterizedTest
    @ValueSource(strings = {"margherita", "pepperoni", "veggie", "meat", "custom"})
    @DisplayName("Should create large pizza for all types")
    void testCreateLargePizza_AllTypes(String type) throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createLargePizza(type);

        // Then
        assertNotNull(pizza);
        assertEquals(PizzaSize.LARGE, pizza.getSize());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when creating large pizza with unknown type")
    void testCreateLargePizza_UnknownType() {
        // When & Then
        assertThrows(
            IllegalArgumentException.class,
            () -> pizzaFactory.createLargePizza("unknown")
        );
    }

    @Test
    @DisplayName("Should create multiple different pizzas")
    void testCreatePizza_MultipleDifferentTypes() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza1 = pizzaFactory.createPizza("margherita", PizzaSize.SMALL);
        Pizza pizza2 = pizzaFactory.createPizza("pepperoni", PizzaSize.MEDIUM);
        Pizza pizza3 = pizzaFactory.createPizza("veggie", PizzaSize.LARGE);
        Pizza pizza4 = pizzaFactory.createPizza("meat", PizzaSize.EXTRA_LARGE);

        // Then
        assertInstanceOf(MargheritaPizza.class, pizza1);
        assertInstanceOf(PepperoniPizza.class, pizza2);
        assertInstanceOf(VeggiePizza.class, pizza3);
        assertInstanceOf(MeatLoversPizza.class, pizza4);
    }

    @Test
    @DisplayName("Should create pizzas with extra large size")
    void testCreatePizza_ExtraLarge() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("pepperoni", PizzaSize.EXTRA_LARGE);

        // Then
        assertNotNull(pizza);
        assertEquals(PizzaSize.EXTRA_LARGE, pizza.getSize());
        assertInstanceOf(PepperoniPizza.class, pizza);
    }

    @Test
    @DisplayName("Should create pizzas with small size")
    void testCreatePizza_Small() throws InvalidPriceException, InvalidPizzaSizeException {
        // When
        Pizza pizza = pizzaFactory.createPizza("veggie", PizzaSize.SMALL);

        // Then
        assertNotNull(pizza);
        assertEquals(PizzaSize.SMALL, pizza.getSize());
        assertInstanceOf(VeggiePizza.class, pizza);
    }
}
