package com.pizzeria.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Email Value Object Tests")
class EmailTest {

    @Test
    @DisplayName("Should create email with valid address")
    void shouldCreateEmailWithValidAddress() {
        Email email = new Email("test@example.com");
        assertNotNull(email);
        assertEquals("test@example.com", email.getAddress());
    }

    @Test
    @DisplayName("Should validate correct email format")
    void shouldValidateCorrectEmailFormat() {
        Email email = new Email("user@domain.com");
        assertTrue(email.isValid());
    }

    @Test
    @DisplayName("Should handle email with subdomain")
    void shouldHandleEmailWithSubdomain() {
        Email email = new Email("user@mail.example.com");
        assertTrue(email.isValid());
        assertEquals("user@mail.example.com", email.getAddress());
    }

    @Test
    @DisplayName("Should get email string representation")
    void shouldGetEmailStringRepresentation() {
        Email email = new Email("contact@pizzeria.ru");
        assertEquals("contact@pizzeria.ru", email.toString());
    }

    @Test
    @DisplayName("Should compare emails for equality")
    void shouldCompareEmailsForEquality() {
        Email email1 = new Email("test@example.com");
        Email email2 = new Email("test@example.com");
        Email email3 = new Email("other@example.com");

        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
    }
}
