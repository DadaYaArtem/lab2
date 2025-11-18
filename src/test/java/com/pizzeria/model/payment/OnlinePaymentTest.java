package com.pizzeria.model.payment;

import com.pizzeria.enums.PaymentMethod;
import com.pizzeria.exceptions.InvalidPaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for OnlinePayment class
 */
@DisplayName("OnlinePayment Tests")
class OnlinePaymentTest {

    private OnlinePayment payment;

    @BeforeEach
    void setUp() throws InvalidPaymentException {
        payment = new OnlinePayment("ONLINE-001", 500.0, "test@example.com");
    }

    @Test
    @DisplayName("Should create online payment with transaction ID, amount and email")
    void testCreateOnlinePayment() {
        assertNotNull(payment);
        assertEquals("ONLINE-001", payment.getTransactionId());
        assertEquals(500.0, payment.getAmount(), 0.01);
        assertEquals("test@example.com", payment.getEmail());
    }

    @Test
    @DisplayName("Should return ONLINE as payment method")
    void testGetPaymentMethod() {
        assertEquals(PaymentMethod.ONLINE, payment.getMethod());
    }

    @Test
    @DisplayName("Should have default payment gateway")
    void testDefaultPaymentGateway() {
        assertEquals("PayPal", payment.getPaymentGateway());
    }

    @Test
    @DisplayName("Should set and get payment gateway")
    void testSetPaymentGateway() {
        payment.setPaymentGateway("Stripe");
        assertEquals("Stripe", payment.getPaymentGateway());
    }

    @Test
    @DisplayName("Should process payment with valid email")
    void testProcessPaymentWithValidEmail() throws InvalidPaymentException {
        assertTrue(payment.process());
        assertTrue(payment.isSuccessful());
        assertNotNull(payment.getConfirmationCode());
    }

    @Test
    @DisplayName("Should generate confirmation code after processing")
    void testGenerateConfirmationCode() throws InvalidPaymentException {
        payment.process();
        String confirmationCode = payment.getConfirmationCode();
        assertNotNull(confirmationCode);
        assertTrue(confirmationCode.startsWith("CONF-"));
    }

    @Test
    @DisplayName("Should verify correct confirmation code")
    void testVerifyConfirmationCode() throws InvalidPaymentException {
        payment.process();
        String code = payment.getConfirmationCode();
        assertTrue(payment.verifyConfirmationCode(code));
    }

    @Test
    @DisplayName("Should not verify incorrect confirmation code")
    void testVerifyIncorrectConfirmationCode() throws InvalidPaymentException {
        payment.process();
        assertFalse(payment.verifyConfirmationCode("WRONG-CODE"));
    }

    @Test
    @DisplayName("Should not verify null confirmation code")
    void testVerifyNullConfirmationCode() throws InvalidPaymentException {
        payment.process();
        assertFalse(payment.verifyConfirmationCode(null));
    }

    @Test
    @DisplayName("Should throw exception for null email")
    void testNullEmailValidation() {
        OnlinePayment nullEmailPayment = assertDoesNotThrow(() ->
            new OnlinePayment("ONLINE-002", 300.0, null)
        );
        assertThrows(InvalidPaymentException.class, () -> {
            nullEmailPayment.process();
        });
    }

    @Test
    @DisplayName("Should throw exception for invalid email")
    void testInvalidEmailValidation() {
        OnlinePayment invalidEmailPayment = assertDoesNotThrow(() ->
            new OnlinePayment("ONLINE-003", 300.0, "invalid-email")
        );
        assertThrows(InvalidPaymentException.class, () -> {
            invalidEmailPayment.process();
        });
    }

    @Test
    @DisplayName("Should accept valid email formats")
    void testValidEmailFormats() throws InvalidPaymentException {
        OnlinePayment payment1 = new OnlinePayment("ONLINE-004", 100.0, "user@domain.com");
        OnlinePayment payment2 = new OnlinePayment("ONLINE-005", 100.0, "test.user@example.org");
        OnlinePayment payment3 = new OnlinePayment("ONLINE-006", 100.0, "name+tag@company.co.uk");

        assertDoesNotThrow(() -> payment1.process());
        assertDoesNotThrow(() -> payment2.process());
        assertDoesNotThrow(() -> payment3.process());
    }

    @Test
    @DisplayName("Should not be successful initially")
    void testNotSuccessfulInitially() {
        assertFalse(payment.isSuccessful());
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for zero amount")
    void testZeroAmountThrowsException() {
        assertThrows(InvalidPaymentException.class, () -> {
            new OnlinePayment("ONLINE-007", 0.0, "test@example.com");
        });
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for negative amount")
    void testNegativeAmountThrowsException() {
        assertThrows(InvalidPaymentException.class, () -> {
            new OnlinePayment("ONLINE-008", -100.0, "test@example.com");
        });
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetAndGetEmail() {
        payment.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", payment.getEmail());
    }

    @Test
    @DisplayName("Should generate receipt")
    void testGetReceipt() {
        String receipt = payment.getReceipt();
        assertNotNull(receipt);
        assertTrue(receipt.contains("ONLINE-001"));
        assertTrue(receipt.contains("500"));
    }

    @Test
    @DisplayName("Should refund successful payment")
    void testRefundSuccessfulPayment() throws InvalidPaymentException {
        payment.process();
        assertDoesNotThrow(() -> payment.refund());
        assertFalse(payment.isSuccessful());
    }

    @Test
    @DisplayName("Should throw exception when refunding unsuccessful payment")
    void testRefundUnsuccessfulPayment() {
        assertThrows(InvalidPaymentException.class, () -> {
            payment.refund();
        });
    }

    @Test
    @DisplayName("Should have payment time set")
    void testPaymentTimeSet() {
        assertNotNull(payment.getPaymentTime());
    }

    @Test
    @DisplayName("Should process payment with different gateways")
    void testDifferentPaymentGateways() throws InvalidPaymentException {
        payment.setPaymentGateway("Stripe");
        assertTrue(payment.process());

        OnlinePayment payment2 = new OnlinePayment("ONLINE-009", 200.0, "user@test.com");
        payment2.setPaymentGateway("Square");
        assertTrue(payment2.process());
    }
}
