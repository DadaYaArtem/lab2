package com.pizzeria.model;

import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.model.products.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Menu class
 */
@DisplayName("Menu Tests")
class MenuTest {

    private Menu menu;
    private Product margheritaPizza;
    private Product pepperoniPizza;
    private Product cola;
    private Product tiramisu;

    @BeforeEach
    void setUp() throws InvalidPriceException {
        menu = new Menu("Main Menu");
        margheritaPizza = new MargheritaPizza(PizzaSize.MEDIUM);
        pepperoniPizza = new PepperoniPizza(PizzaSize.LARGE);
        cola = new Drink("Кока-кола", 100.0, 500);
        tiramisu = new Dessert("Тирамису", 250.0, 150);
    }

    @Test
    @DisplayName("Should create menu with name")
    void testCreateMenu() {
        assertNotNull(menu);
        assertEquals("Main Menu", menu.getName());
    }

    @Test
    @DisplayName("Should have empty product list initially")
    void testInitialProductList() {
        assertNotNull(menu.getProducts());
        assertEquals(0, menu.getProducts().size());
    }

    @Test
    @DisplayName("Should be active by default")
    void testActiveByDefault() {
        assertTrue(menu.isActive());
    }

    @Test
    @DisplayName("Should add product to menu")
    void testAddProduct() {
        menu.addProduct(margheritaPizza);
        assertEquals(1, menu.getProducts().size());
        assertTrue(menu.getProducts().contains(margheritaPizza));
    }

    @Test
    @DisplayName("Should add multiple products")
    void testAddMultipleProducts() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(pepperoniPizza);
        menu.addProduct(cola);
        menu.addProduct(tiramisu);

        assertEquals(4, menu.getProducts().size());
        assertTrue(menu.getProducts().contains(margheritaPizza));
        assertTrue(menu.getProducts().contains(pepperoniPizza));
        assertTrue(menu.getProducts().contains(cola));
        assertTrue(menu.getProducts().contains(tiramisu));
    }

    @Test
    @DisplayName("Should remove product from menu")
    void testRemoveProduct() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(cola);
        assertEquals(2, menu.getProducts().size());

        menu.removeProduct(margheritaPizza);
        assertEquals(1, menu.getProducts().size());
        assertFalse(menu.getProducts().contains(margheritaPizza));
        assertTrue(menu.getProducts().contains(cola));
    }

    @Test
    @DisplayName("Should get all products")
    void testGetAllProducts() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(pepperoniPizza);
        menu.addProduct(cola);

        List<Product> allProducts = menu.getProducts();
        assertEquals(3, allProducts.size());
    }

    @Test
    @DisplayName("Should get only available products")
    void testGetAvailableProducts() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(pepperoniPizza);
        menu.addProduct(cola);

        // Make one product unavailable
        pepperoniPizza.setAvailable(false);

        List<Product> availableProducts = menu.getAvailableProducts();
        assertEquals(2, availableProducts.size());
        assertTrue(availableProducts.contains(margheritaPizza));
        assertFalse(availableProducts.contains(pepperoniPizza));
        assertTrue(availableProducts.contains(cola));
    }

    @Test
    @DisplayName("Should find product by name")
    void testFindProductByName() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(cola);

        Product found = menu.findProductByName("Маргарита");
        assertNotNull(found);
        assertEquals(margheritaPizza, found);
    }

    @Test
    @DisplayName("Should find product by name case insensitive")
    void testFindProductByNameCaseInsensitive() {
        menu.addProduct(margheritaPizza);

        Product found = menu.findProductByName("маргарита");
        assertNotNull(found);
        assertEquals(margheritaPizza, found);
    }

    @Test
    @DisplayName("Should return null when product not found")
    void testFindProductNotFound() {
        menu.addProduct(margheritaPizza);

        Product found = menu.findProductByName("Гавайская");
        assertNull(found);
    }

    @Test
    @DisplayName("Should get products by price range")
    void testGetProductsByPriceRange() {
        menu.addProduct(margheritaPizza); // 450.0 for MEDIUM
        menu.addProduct(cola);             // 100.0
        menu.addProduct(tiramisu);         // 250.0

        List<Product> productsInRange = menu.getProductsByPriceRange(200.0, 500.0);

        assertEquals(2, productsInRange.size());
        assertTrue(productsInRange.contains(margheritaPizza));
        assertTrue(productsInRange.contains(tiramisu));
        assertFalse(productsInRange.contains(cola));
    }

    @Test
    @DisplayName("Should get products by exact price boundaries")
    void testGetProductsByPriceBoundaries() {
        menu.addProduct(cola); // 100.0

        List<Product> productsInRange = menu.getProductsByPriceRange(100.0, 100.0);

        assertEquals(1, productsInRange.size());
        assertTrue(productsInRange.contains(cola));
    }

    @Test
    @DisplayName("Should return empty list for price range with no products")
    void testGetProductsByPriceRangeEmpty() {
        menu.addProduct(margheritaPizza); // 450.0
        menu.addProduct(tiramisu);         // 250.0

        List<Product> productsInRange = menu.getProductsByPriceRange(50.0, 100.0);

        assertEquals(0, productsInRange.size());
    }

    @Test
    @DisplayName("Should set and get menu name")
    void testSetMenuName() {
        menu.setName("Dinner Menu");
        assertEquals("Dinner Menu", menu.getName());
    }

    @Test
    @DisplayName("Should set and get active status")
    void testSetActive() {
        menu.setActive(false);
        assertFalse(menu.isActive());

        menu.setActive(true);
        assertTrue(menu.isActive());
    }

    @Test
    @DisplayName("Should display menu")
    void testDisplayMenu() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(cola);

        assertDoesNotThrow(() -> menu.displayMenu());
    }

    @Test
    @DisplayName("Should handle removing non-existent product")
    void testRemoveNonExistentProduct() throws InvalidPriceException {
        menu.addProduct(margheritaPizza);
        int initialSize = menu.getProducts().size();

        Product otherPizza = new VeggiePizza(PizzaSize.SMALL);
        menu.removeProduct(otherPizza);

        assertEquals(initialSize, menu.getProducts().size());
    }

    @Test
    @DisplayName("Should set products list")
    void testSetProducts() throws InvalidPriceException {
        java.util.List<Product> productList = new java.util.ArrayList<>();
        productList.add(margheritaPizza);
        productList.add(cola);

        menu.setProducts(productList);

        assertEquals(2, menu.getProducts().size());
        assertTrue(menu.getProducts().contains(margheritaPizza));
        assertTrue(menu.getProducts().contains(cola));
    }

    @Test
    @DisplayName("Should handle multiple products with same type")
    void testMultipleProductsSameType() throws InvalidPriceException {
        Product smallMargherita = new MargheritaPizza(PizzaSize.SMALL);
        Product mediumMargherita = new MargheritaPizza(PizzaSize.MEDIUM);

        menu.addProduct(smallMargherita);
        menu.addProduct(mediumMargherita);

        assertEquals(2, menu.getProducts().size());
    }

    @Test
    @DisplayName("Should get all products including unavailable when using getProducts")
    void testGetAllProductsIncludingUnavailable() {
        menu.addProduct(margheritaPizza);
        menu.addProduct(cola);

        cola.setAvailable(false);

        assertEquals(2, menu.getProducts().size());
        assertEquals(1, menu.getAvailableProducts().size());
    }
}
