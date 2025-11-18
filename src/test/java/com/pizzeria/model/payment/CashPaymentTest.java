package com.pizzeria.model.payment;

import com.pizzeria.enums.PaymentMethod;
import com.pizzeria.exceptions.InvalidPaymentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CashPayment class
 */
@DisplayName("CashPayment Tests")
class CashPaymentTest {

    private CashPayment payment;

    @BeforeEach
    void setUp() throws InvalidPaymentException {
        payment = new CashPayment("CASH-001", 500.0);
    }

    @Test
    @DisplayName("Should create cash payment with transaction ID and amount")
    void testCreateCashPayment() {
        assertNotNull(payment);
        assertEquals("CASH-001", payment.getTransactionId());
        assertEquals(500.0, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should return CASH as payment method")
    void testGetPaymentMethod() {
        assertEquals(PaymentMethod.CASH, payment.getMethod());
    }

    @Test
    @DisplayName("Should process payment with exact amount")
    void testProcessPaymentExactAmount() throws InvalidPaymentException {
        payment.setAmountReceived(500.0);
        assertTrue(payment.process());
        assertTrue(payment.isSuccessful());
        assertEquals(0.0, payment.getChange(), 0.01);
    }

    @Test
    @DisplayName("Should process payment with more than required amount")
    void testProcessPaymentWithChange() throws InvalidPaymentException {
        payment.setAmountReceived(600.0);
        assertTrue(payment.process());
        assertTrue(payment.isSuccessful());
        assertEquals(100.0, payment.getChange(), 0.01);
    }

    @Test
    @DisplayName("Should calculate change correctly")
    void testCalculateChange() {
        assertEquals(100.0, payment.calculateChange(600.0), 0.01);
        assertEquals(50.0, payment.calculateChange(550.0), 0.01);
        assertEquals(0.0, payment.calculateChange(500.0), 0.01);
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for insufficient amount")
    void testProcessPaymentInsufficientAmount() {
        payment.setAmountReceived(400.0);
        assertThrows(InvalidPaymentException.class, () -> {
            payment.process();
        });
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
            new CashPayment("CASH-002", 0.0);
        });
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for negative amount")
    void testNegativeAmountThrowsException() {
        assertThrows(InvalidPaymentException.class, () -> {
            new CashPayment("CASH-003", -100.0);
        });
    }

    @Test
    @DisplayName("Should get and set amount received")
    void testAmountReceived() {
        payment.setAmountReceived(700.0);
        assertEquals(700.0, payment.getAmountReceived(), 0.01);
    }

    @Test
    @DisplayName("Should generate receipt")
    void testGetReceipt() {
        String receipt = payment.getReceipt();
        assertNotNull(receipt);
        assertTrue(receipt.contains("CASH-001"));
        assertTrue(receipt.contains("500"));
    }

    @Test
    @DisplayName("Should refund successful payment")
    void testRefundSuccessfulPayment() throws InvalidPaymentException {
        payment.setAmountReceived(500.0);
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
    @DisplayName("Should process large cash payment")
    void testLargeCashPayment() throws InvalidPaymentException {
        CashPayment largePayment = new CashPayment("CASH-999", 10000.0);
        largePayment.setAmountReceived(12000.0);
        assertTrue(largePayment.process());
        assertEquals(2000.0, largePayment.getChange(), 0.01);
    }
}
