package com.pizzeria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Address Model Tests")
class AddressTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address("Москва", "Ленина", "10", "5");
    }

    @Test
    @DisplayName("Should create address with all fields")
    void shouldCreateAddressWithAllFields() {
        assertNotNull(address);
        assertEquals("Москва", address.getCity());
        assertEquals("Ленина", address.getStreet());
        assertEquals("10", address.getBuilding());
        assertEquals("5", address.getApartment());
    }

    @Test
    @DisplayName("Should get full address string")
    void shouldGetFullAddressString() {
        String fullAddress = address.getFullAddress();
        assertTrue(fullAddress.contains("Москва"));
        assertTrue(fullAddress.contains("Ленина"));
        assertTrue(fullAddress.contains("10"));
        assertTrue(fullAddress.contains("5"));
    }

    @Test
    @DisplayName("Should create address without apartment")
    void shouldCreateAddressWithoutApartment() {
        Address addressNoApt = new Address("Санкт-Петербург", "Невский", "20", null);
        assertNotNull(addressNoApt);
        assertEquals("Санкт-Петербург", addressNoApt.getCity());
        assertNull(addressNoApt.getApartment());
    }

    @Test
    @DisplayName("Should update address fields")
    void shouldUpdateAddressFields() {
        address.setCity("Казань");
        address.setStreet("Баумана");

        assertEquals("Казань", address.getCity());
        assertEquals("Баумана", address.getStreet());
    }

    @Test
    @DisplayName("Should validate address is not empty")
    void shouldValidateAddressIsNotEmpty() {
        assertTrue(address.isValid());
    }
}
