package com.pizzeria.model.users;

import com.pizzeria.enums.EmployeeRole;
import com.pizzeria.enums.PizzaSize;
import com.pizzeria.exceptions.InsufficientIngredientsException;
import com.pizzeria.exceptions.InvalidPriceException;
import com.pizzeria.model.products.MargheritaPizza;
import com.pizzeria.model.products.Pizza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Chef class
 */
@DisplayName("Chef Tests")
class ChefTest {

    private Chef chef;

    @BeforeEach
    void setUp() {
        chef = new Chef("CHEF-001", "Mario", "Rossi", 50000.0);
    }

    @Test
    @DisplayName("Should create chef with ID, name, and salary")
    void testCreateChef() {
        assertNotNull(chef);
        assertEquals("CHEF-001", chef.getId());
        assertEquals("Mario", chef.getFirstName());
        assertEquals("Rossi", chef.getLastName());
        assertEquals(50000.0, chef.getSalary(), 0.01);
    }

    @Test
    @DisplayName("Should return correct role")
    void testGetRole() {
        assertEquals("Повар", chef.getRole());
    }

    @Test
    @DisplayName("Should return CHEF as role enum")
    void testGetRoleEnum() {
        assertEquals(EmployeeRole.CHEF, chef.getRoleEnum());
    }

    @Test
    @DisplayName("Should return correct full name")
    void testGetFullName() {
        assertEquals("Mario Rossi", chef.getFullName());
    }

    @Test
    @DisplayName("Should perform duty")
    void testPerformDuty() {
        assertDoesNotThrow(() -> chef.performDuty());
    }

    @Test
    @DisplayName("Should have zero pizzas cooked initially")
    void testInitialPizzasCooked() {
        assertEquals(0, chef.getPizzasCooked());
    }

    @Test
    @DisplayName("Should increment pizzas cooked")
    void testIncrementPizzasCooked() {
        chef.incrementPizzasCooked();
        assertEquals(1, chef.getPizzasCooked());
        chef.incrementPizzasCooked();
        assertEquals(2, chef.getPizzasCooked());
    }

    @Test
    @DisplayName("Should set and get specialty")
    void testSpecialty() {
        chef.setSpecialty("Margherita");
        assertEquals("Margherita", chef.getSpecialty());
    }

    @Test
    @DisplayName("Should set and get experience years")
    void testExperienceYears() {
        chef.setExperienceYears(5);
        assertEquals(5, chef.getExperienceYears());
    }

    @Test
    @DisplayName("Should return Новичок skill level for new chef")
    void testNoviceSkillLevel() {
        chef.setExperienceYears(0);
        assertEquals("Новичок", chef.getSkillLevel());
    }

    @Test
    @DisplayName("Should return Опытный skill level for 2+ years")
    void testExperiencedSkillLevel() {
        chef.setExperienceYears(3);
        assertEquals("Опытный", chef.getSkillLevel());
    }

    @Test
    @DisplayName("Should return Профессионал skill level for 5+ years")
    void testProfessionalSkillLevel() {
        chef.setExperienceYears(7);
        assertEquals("Профессионал", chef.getSkillLevel());
    }

    @Test
    @DisplayName("Should return Мастер skill level for 10+ years")
    void testMasterSkillLevel() {
        chef.setExperienceYears(15);
        assertEquals("Мастер", chef.getSkillLevel());
    }

    @Test
    @DisplayName("Should cook pizza with specialty")
    void testCanCookPizzaWithSpecialty() {
        chef.setSpecialty("Margherita");
        assertTrue(chef.canCookPizza("Margherita"));
    }

    @Test
    @DisplayName("Should cook any pizza with enough experience")
    void testCanCookAnyPizzaWithExperience() {
        chef.setExperienceYears(3);
        assertTrue(chef.canCookPizza("Pepperoni"));
        assertTrue(chef.canCookPizza("Veggie"));
    }

    @Test
    @DisplayName("Should not cook pizza without specialty or experience")
    void testCannotCookPizzaWithoutSpecialtyOrExperience() {
        chef.setExperienceYears(1);
        assertFalse(chef.canCookPizza("Pepperoni"));
    }

    @Test
    @DisplayName("Should be active by default")
    void testActiveByDefault() {
        assertTrue(chef.isActive());
    }

    @Test
    @DisplayName("Should set active status")
    void testSetActive() {
        chef.setActive(false);
        assertFalse(chef.isActive());
    }

    @Test
    @DisplayName("Should have hire date set")
    void testHireDateSet() {
        assertNotNull(chef.getHireDate());
    }

    @Test
    @DisplayName("Should calculate years of service")
    void testYearsOfService() {
        int years = chef.getYearsOfService();
        assertTrue(years >= 0);
    }

    @Test
    @DisplayName("Should calculate bonus based on salary and years")
    void testCalculateBonus() {
        double bonus = chef.calculateBonus();
        assertTrue(bonus >= 0);
    }

    @Test
    @DisplayName("Should verify password")
    void testVerifyPassword() {
        assertTrue(chef.verifyPassword("default123"));
        assertFalse(chef.verifyPassword("wrong"));
    }

    @Test
    @DisplayName("Should change password successfully")
    void testChangePassword() {
        assertDoesNotThrow(() -> {
            chef.changePassword("default123", "newpass123");
        });
        assertTrue(chef.verifyPassword("newpass123"));
        assertFalse(chef.verifyPassword("default123"));
    }

    @Test
    @DisplayName("Should throw exception when changing password with wrong old password")
    void testChangePasswordWithWrongOldPassword() {
        assertThrows(Exception.class, () -> {
            chef.changePassword("wrong", "newpass123");
        });
    }

    @Test
    @DisplayName("Should cook pizza and increment counter")
    void testCookPizza() throws InvalidPriceException, InsufficientIngredientsException {
        Pizza pizza = new MargheritaPizza(PizzaSize.MEDIUM);
        int initialCount = chef.getPizzasCooked();

        int cookingTime = chef.cookPizza(pizza);

        assertEquals(initialCount + 1, chef.getPizzasCooked());
        assertTrue(cookingTime > 0);
    }

    @Test
    @DisplayName("Should return cooking time when cooking pizza")
    void testCookPizzaReturnsCookingTime() throws InvalidPriceException, InsufficientIngredientsException {
        Pizza pizza = new MargheritaPizza(PizzaSize.SMALL);
        int cookingTime = chef.cookPizza(pizza);
        assertEquals(18, cookingTime); // Margherita cooking time
    }
}
