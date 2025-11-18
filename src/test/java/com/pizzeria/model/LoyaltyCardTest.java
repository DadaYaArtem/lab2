package com.pizzeria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoyaltyCard Model Tests")
class LoyaltyCardTest {

    private LoyaltyCard loyaltyCard;

    @BeforeEach
    void setUp() {
        loyaltyCard = new LoyaltyCard("LC-001");
    }

    @Test
    @DisplayName("Should create loyalty card with ID")
    void shouldCreateLoyaltyCardWithId() {
        assertNotNull(loyaltyCard);
        assertEquals("LC-001", loyaltyCard.getCardNumber());
        assertEquals(0, loyaltyCard.getPoints());
    }

    @Test
    @DisplayName("Should add points")
    void shouldAddPoints() {
        loyaltyCard.addPoints(100);
        assertEquals(100, loyaltyCard.getPoints());

        loyaltyCard.addPoints(50);
        assertEquals(150, loyaltyCard.getPoints());
    }

    @Test
    @DisplayName("Should redeem points")
    void shouldRedeemPoints() {
        loyaltyCard.addPoints(100);
        boolean result = loyaltyCard.redeemPoints(50);

        assertTrue(result);
        assertEquals(50, loyaltyCard.getPoints());
    }

    @Test
    @DisplayName("Should not redeem more points than available")
    void shouldNotRedeemMorePointsThanAvailable() {
        loyaltyCard.addPoints(50);
        boolean result = loyaltyCard.redeemPoints(100);

        assertFalse(result);
        assertEquals(50, loyaltyCard.getPoints());
    }

    @Test
    @DisplayName("Should calculate discount based on points")
    void shouldCalculateDiscountBasedOnPoints() {
        loyaltyCard.addPoints(500);
        double discount = loyaltyCard.getDiscountPercentage();

        assertTrue(discount >= 0);
        assertTrue(discount <= 100);
    }

    @Test
    @DisplayName("Should handle zero points")
    void shouldHandleZeroPoints() {
        assertEquals(0, loyaltyCard.getPoints());
        assertFalse(loyaltyCard.redeemPoints(10));
    }

    @Test
    @DisplayName("Should get card activation date")
    void shouldGetCardActivationDate() {
        assertNotNull(loyaltyCard.getActivationDate());
    }

    @Test
    @DisplayName("Should check if card is active")
    void shouldCheckIfCardIsActive() {
        assertTrue(loyaltyCard.isActive());
    }
}
