package com.pizzeria.model;

import com.pizzeria.model.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Customer Model Tests")
class CustomerTest {

    private Customer customer;
    private Email email;
    private PhoneNumber phone;

    @BeforeEach
    void setUp() {
        email = new Email("test@example.com");
        phone = new PhoneNumber("+79991234567");
        customer = new Customer("Иван", "Иванов", email, phone);
    }

    @Test
    @DisplayName("Should create customer with valid data")
    void shouldCreateCustomerWithValidData() {
        assertNotNull(customer);
        assertEquals("Иван", customer.getFirstName());
        assertEquals("Иванов", customer.getLastName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
    }

    @Test
    @DisplayName("Should get full name")
    void shouldGetFullName() {
        assertEquals("Иван Иванов", customer.getFullName());
    }

    @Test
    @DisplayName("Should set and get loyalty card")
    void shouldSetAndGetLoyaltyCard() {
        LoyaltyCard loyaltyCard = new LoyaltyCard("LC-001");
        customer.setLoyaltyCard(loyaltyCard);

        assertNotNull(customer.getLoyaltyCard());
        assertEquals(loyaltyCard, customer.getLoyaltyCard());
    }

    @Test
    @DisplayName("Should initially have no loyalty card")
    void shouldInitiallyHaveNoLoyaltyCard() {
        Customer newCustomer = new Customer("Петр", "Петров",
                                            new Email("petr@test.com"),
                                            new PhoneNumber("+79997654321"));
        assertNull(newCustomer.getLoyaltyCard());
    }

    @Test
    @DisplayName("Should update email")
    void shouldUpdateEmail() {
        Email newEmail = new Email("newemail@example.com");
        customer.setEmail(newEmail);
        assertEquals(newEmail, customer.getEmail());
    }

    @Test
    @DisplayName("Should update phone")
    void shouldUpdatePhone() {
        PhoneNumber newPhone = new PhoneNumber("+79997777777");
        customer.setPhone(newPhone);
        assertEquals(newPhone, customer.getPhone());
    }

    @Test
    @DisplayName("Should authenticate with correct password")
    void shouldAuthenticateWithCorrectPassword() {
        customer.setPassword("password123");
        assertTrue(customer.authenticate("password123"));
    }

    @Test
    @DisplayName("Should not authenticate with wrong password")
    void shouldNotAuthenticateWithWrongPassword() {
        customer.setPassword("password123");
        assertFalse(customer.authenticate("wrongpassword"));
    }

    @Test
    @DisplayName("Should get customer ID")
    void shouldGetCustomerId() {
        customer.setCustomerId("CUST-001");
        assertEquals("CUST-001", customer.getCustomerId());
    }
}
