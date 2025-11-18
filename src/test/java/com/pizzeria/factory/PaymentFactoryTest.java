package com.pizzeria.factory;

import com.pizzeria.enums.PaymentMethod;
import com.pizzeria.exceptions.InvalidPaymentException;
import com.pizzeria.model.payment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PaymentFactory
 */
@DisplayName("PaymentFactory Tests")
class PaymentFactoryTest {

    private PaymentFactory paymentFactory;

    @BeforeEach
    void setUp() {
        paymentFactory = new PaymentFactory();
    }

    @Test
    @DisplayName("Should create CASH payment successfully")
    void testCreatePayment_Cash() throws InvalidPaymentException {
        // When
        Payment payment = paymentFactory.createPayment(PaymentMethod.CASH, "TXN-001", 500.0);

        // Then
        assertNotNull(payment);
        assertInstanceOf(CashPayment.class, payment);
        assertEquals(PaymentMethod.CASH, payment.getMethod());
        assertEquals("TXN-001", payment.getTransactionId());
        assertEquals(500.0, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should create CARD payment successfully")
    void testCreatePayment_Card() throws InvalidPaymentException {
        // When
        Payment payment = paymentFactory.createPayment(PaymentMethod.CARD, "TXN-002", 750.0);

        // Then
        assertNotNull(payment);
        assertInstanceOf(CardPayment.class, payment);
        assertEquals(PaymentMethod.CARD, payment.getMethod());
        assertEquals("TXN-002", payment.getTransactionId());
        assertEquals(750.0, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should create ONLINE payment successfully")
    void testCreatePayment_Online() throws InvalidPaymentException {
        // When
        Payment payment = paymentFactory.createPayment(PaymentMethod.ONLINE, "TXN-003", 1000.0);

        // Then
        assertNotNull(payment);
        assertInstanceOf(OnlinePayment.class, payment);
        assertEquals(PaymentMethod.ONLINE, payment.getMethod());
        assertEquals("TXN-003", payment.getTransactionId());
        assertEquals(1000.0, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for unsupported payment method")
    void testCreatePayment_UnsupportedMethod() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> paymentFactory.createPayment(PaymentMethod.CRYPTO, "TXN-004", 500.0)
        );
        assertTrue(exception.getMessage().contains("Неподдерживаемый метод оплаты"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 500.0, 1000.0, 2500.5, 9999.99})
    @DisplayName("Should create payment with various amounts")
    void testCreatePayment_VariousAmounts(double amount) throws InvalidPaymentException {
        // When
        Payment payment = paymentFactory.createPayment(PaymentMethod.CASH, "TXN-001", amount);

        // Then
        assertNotNull(payment);
        assertEquals(amount, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should create cash payment with generated transaction ID")
    void testCreateCashPayment() throws InvalidPaymentException {
        // When
        CashPayment payment = paymentFactory.createCashPayment(500.0);

        // Then
        assertNotNull(payment);
        assertEquals(PaymentMethod.CASH, payment.getMethod());
        assertEquals(500.0, payment.getAmount(), 0.01);
        assertTrue(payment.getTransactionId().startsWith("CASH-"));
    }

    @Test
    @DisplayName("Should create card payment with custom card number")
    void testCreateCardPayment() throws InvalidPaymentException {
        // Given
        String cardNumber = "4111111111111111";

        // When
        CardPayment payment = paymentFactory.createCardPayment(750.0, cardNumber);

        // Then
        assertNotNull(payment);
        assertEquals(PaymentMethod.CARD, payment.getMethod());
        assertEquals(750.0, payment.getAmount(), 0.01);
        assertTrue(payment.getTransactionId().startsWith("CARD-"));
    }

    @Test
    @DisplayName("Should create online payment with custom email")
    void testCreateOnlinePayment() throws InvalidPaymentException {
        // Given
        String email = "customer@example.com";

        // When
        OnlinePayment payment = paymentFactory.createOnlinePayment(1000.0, email);

        // Then
        assertNotNull(payment);
        assertEquals(PaymentMethod.ONLINE, payment.getMethod());
        assertEquals(1000.0, payment.getAmount(), 0.01);
        assertTrue(payment.getTransactionId().startsWith("ONLINE-"));
    }

    @Test
    @DisplayName("Should generate unique transaction IDs for cash payments")
    void testCreateCashPayment_UniqueTransactionIds() throws InvalidPaymentException, InterruptedException {
        // When
        CashPayment payment1 = paymentFactory.createCashPayment(100.0);
        Thread.sleep(1); // Ensure different timestamps
        CashPayment payment2 = paymentFactory.createCashPayment(200.0);

        // Then
        assertNotEquals(payment1.getTransactionId(), payment2.getTransactionId());
    }

    @Test
    @DisplayName("Should generate unique transaction IDs for card payments")
    void testCreateCardPayment_UniqueTransactionIds() throws InvalidPaymentException, InterruptedException {
        // When
        CardPayment payment1 = paymentFactory.createCardPayment(100.0, "1234567890123456");
        Thread.sleep(1); // Ensure different timestamps
        CardPayment payment2 = paymentFactory.createCardPayment(200.0, "9876543210987654");

        // Then
        assertNotEquals(payment1.getTransactionId(), payment2.getTransactionId());
    }

    @Test
    @DisplayName("Should generate unique transaction IDs for online payments")
    void testCreateOnlinePayment_UniqueTransactionIds() throws InvalidPaymentException, InterruptedException {
        // When
        OnlinePayment payment1 = paymentFactory.createOnlinePayment(100.0, "user1@example.com");
        Thread.sleep(1); // Ensure different timestamps
        OnlinePayment payment2 = paymentFactory.createOnlinePayment(200.0, "user2@example.com");

        // Then
        assertNotEquals(payment1.getTransactionId(), payment2.getTransactionId());
    }

    @Test
    @DisplayName("Should create payment with minimal amount")
    void testCreatePayment_MinimalAmount() throws InvalidPaymentException {
        // When
        Payment payment = paymentFactory.createPayment(PaymentMethod.CASH, "TXN-001", 0.01);

        // Then
        assertNotNull(payment);
        assertEquals(0.01, payment.getAmount(), 0.001);
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for zero amount")
    void testCreatePayment_ZeroAmount() {
        // When & Then
        assertThrows(
            InvalidPaymentException.class,
            () -> paymentFactory.createPayment(PaymentMethod.CASH, "TXN-001", 0.0)
        );
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException for negative amount")
    void testCreatePayment_NegativeAmount() {
        // When & Then
        assertThrows(
            InvalidPaymentException.class,
            () -> paymentFactory.createPayment(PaymentMethod.CASH, "TXN-001", -100.0)
        );
    }

    @Test
    @DisplayName("Should create multiple payments of different types")
    void testCreatePayment_MultipleDifferentTypes() throws InvalidPaymentException {
        // When
        Payment cashPayment = paymentFactory.createPayment(PaymentMethod.CASH, "TXN-001", 100.0);
        Payment cardPayment = paymentFactory.createPayment(PaymentMethod.CARD, "TXN-002", 200.0);
        Payment onlinePayment = paymentFactory.createPayment(PaymentMethod.ONLINE, "TXN-003", 300.0);

        // Then
        assertInstanceOf(CashPayment.class, cashPayment);
        assertInstanceOf(CardPayment.class, cardPayment);
        assertInstanceOf(OnlinePayment.class, onlinePayment);
    }

    @Test
    @DisplayName("Should create cash payment with large amount")
    void testCreateCashPayment_LargeAmount() throws InvalidPaymentException {
        // When
        CashPayment payment = paymentFactory.createCashPayment(99999.99);

        // Then
        assertNotNull(payment);
        assertEquals(99999.99, payment.getAmount(), 0.01);
    }

    @Test
    @DisplayName("Should create card payment with different card numbers")
    void testCreateCardPayment_DifferentCardNumbers() throws InvalidPaymentException {
        // When
        CardPayment payment1 = paymentFactory.createCardPayment(100.0, "1111222233334444");
        CardPayment payment2 = paymentFactory.createCardPayment(200.0, "5555666677778888");

        // Then
        assertNotNull(payment1);
        assertNotNull(payment2);
        assertNotSame(payment1, payment2);
    }

    @Test
    @DisplayName("Should create online payment with different emails")
    void testCreateOnlinePayment_DifferentEmails() throws InvalidPaymentException {
        // When
        OnlinePayment payment1 = paymentFactory.createOnlinePayment(100.0, "user1@test.com");
        OnlinePayment payment2 = paymentFactory.createOnlinePayment(200.0, "user2@test.com");

        // Then
        assertNotNull(payment1);
        assertNotNull(payment2);
        assertNotSame(payment1, payment2);
    }

    @Test
    @DisplayName("Should create payment with exact decimal amount")
    void testCreatePayment_ExactDecimalAmount() throws InvalidPaymentException {
        // When
        Payment payment = paymentFactory.createPayment(PaymentMethod.CARD, "TXN-001", 123.45);

        // Then
        assertEquals(123.45, payment.getAmount(), 0.001);
    }

    @Test
    @DisplayName("Should verify payment method is set correctly")
    void testCreatePayment_VerifyPaymentMethod() throws InvalidPaymentException {
        // When
        Payment cashPayment = paymentFactory.createCashPayment(100.0);
        Payment cardPayment = paymentFactory.createCardPayment(200.0, "1234567890123456");
        Payment onlinePayment = paymentFactory.createOnlinePayment(300.0, "user@test.com");

        // Then
        assertEquals(PaymentMethod.CASH, cashPayment.getMethod());
        assertEquals(PaymentMethod.CARD, cardPayment.getMethod());
        assertEquals(PaymentMethod.ONLINE, onlinePayment.getMethod());
    }
}
