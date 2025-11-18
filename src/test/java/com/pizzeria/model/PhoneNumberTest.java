package com.pizzeria.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PhoneNumber Value Object Tests")
class PhoneNumberTest {

    @Test
    @DisplayName("Should create phone number with valid number")
    void shouldCreatePhoneNumberWithValidNumber() {
        PhoneNumber phone = new PhoneNumber("+79991234567");
        assertNotNull(phone);
        assertEquals("+79991234567", phone.getNumber());
    }

    @Test
    @DisplayName("Should validate correct phone format")
    void shouldValidateCorrectPhoneFormat() {
        PhoneNumber phone = new PhoneNumber("+79991234567");
        assertTrue(phone.isValid());
    }

    @Test
    @DisplayName("Should get phone string representation")
    void shouldGetPhoneStringRepresentation() {
        PhoneNumber phone = new PhoneNumber("+79991234567");
        assertEquals("+79991234567", phone.toString());
    }

    @Test
    @DisplayName("Should compare phone numbers for equality")
    void shouldComparePhoneNumbersForEquality() {
        PhoneNumber phone1 = new PhoneNumber("+79991234567");
        PhoneNumber phone2 = new PhoneNumber("+79991234567");
        PhoneNumber phone3 = new PhoneNumber("+79997654321");

        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
    }

    @Test
    @DisplayName("Should handle different phone formats")
    void shouldHandleDifferentPhoneFormats() {
        PhoneNumber phone1 = new PhoneNumber("+79991234567");
        PhoneNumber phone2 = new PhoneNumber("+7 999 123 45 67");

        assertNotNull(phone1);
        assertNotNull(phone2);
    }
}
