package com.pizzeria.model.payment;

import com.pizzeria.enums.PaymentMethod;
import com.pizzeria.exceptions.InvalidPaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CardPayment class
 */
@DisplayName("CardPayment Tests")
class CardPaymentTest {

    private CardPayment payment;

    @BeforeEach
    void setUp() throws InvalidPaymentException {
        payment = new CardPayment("CARD-001", 500.0, "1234567890123456");
    }

    @Test
    @DisplayName("Should create card payment with transaction ID, amount and card number")
    void testCreateCardPayment() {
        assertNotNull(payment);
        assertEquals("CARD-001", payment.getTransactionId());
        assertEquals(500.0, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should return CARD as payment method")
    void testGetPaymentMethod() {
        assertEquals(PaymentMethod.CARD, payment.getMethod());
    }

    @Test
    @DisplayName("Should mask card number")
    void testMaskCardNumber() {
        String maskedNumber = payment.getCardNumber();
        assertNotNull(maskedNumber);
        assertTrue(maskedNumber.contains("****"));
        assertTrue(maskedNumber.contains("3456")); // last 4 digits
    }

    @Test
    @DisplayName("Should process payment with valid card")
    void testProcessPaymentWithValidCard() throws InvalidPaymentException {
        assertTrue(payment.process());
        assertTrue(payment.isSuccessful());
    }

    @Test
    @DisplayName("Should validate card number length")
    void testValidateCardNumberLength() throws InvalidPaymentException {
        CardPayment validPayment = new CardPayment("CARD-002", 300.0, "1234567890123456");
        assertDoesNotThrow(() -> validPayment.process());
    }

    @Test
    @DisplayName("Should throw exception for invalid card")
    void testInvalidCardValidation() throws InvalidPaymentException {
        CardPayment invalidPayment = new CardPayment("CARD-003", 300.0, "123");
        assertThrows(InvalidPaymentException.class, () -> {
            invalidPayment.process();
        });
    }

    @Test
    @DisplayName("Should verify valid PIN")
    void testVerifyValidPin() {
        assertTrue(payment.verifyPin("1234"));
    }

    @Test
    @DisplayName("Should reject invalid PIN length")
    void testRejectInvalidPinLength() {
        assertFalse(payment.verifyPin("123"));
        assertFalse(payment.verifyPin("12345"));
    }

    @Test
    @DisplayName("Should reject null PIN")
    void testRejectNullPin() {
        assertFalse(payment.verifyPin(null));
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
            new CardPayment("CARD-004", 0.0, "1234567890123456");
        });
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for negative amount")
    void testNegativeAmountThrowsException() {
        assertThrows(InvalidPaymentException.class, () -> {
            new CardPayment("CARD-005", -100.0, "1234567890123456");
        });
    }

    @Test
    @DisplayName("Should set and get card holder name")
    void testCardHolderName() {
        payment.setCardHolderName("John Doe");
        assertEquals("John Doe", payment.getCardHolderName());
    }

    @Test
    @DisplayName("Should set and get expiry date")
    void testExpiryDate() {
        payment.setExpiryDate("12/25");
        assertEquals("12/25", payment.getExpiryDate());
    }

    @Test
    @DisplayName("Should generate receipt")
    void testGetReceipt() {
        String receipt = payment.getReceipt();
        assertNotNull(receipt);
        assertTrue(receipt.contains("CARD-001"));
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
    @DisplayName("Should mask short card numbers")
    void testMaskShortCardNumber() throws InvalidPaymentException {
        CardPayment shortCardPayment = new CardPayment("CARD-006", 100.0, "123");
        assertEquals("****", shortCardPayment.getCardNumber());
    }

    @Test
    @DisplayName("Should handle null card number")
    void testNullCardNumber() throws InvalidPaymentException {
        CardPayment nullCardPayment = new CardPayment("CARD-007", 100.0, null);
        assertEquals("****", nullCardPayment.getCardNumber());
    }
}
