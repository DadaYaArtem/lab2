package com.pizzeria.service;

import com.pizzeria.exceptions.InvalidPaymentException;
import com.pizzeria.model.Order;
import com.pizzeria.model.Receipt;
import com.pizzeria.model.payment.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PaymentService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentService Tests")
class PaymentServiceTest {

    private PaymentService paymentService;

    @Mock
    private Order mockOrder;

    @Mock
    private Payment mockPayment;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    @DisplayName("Should process payment successfully when amount is sufficient")
    void testProcessPayment_Success() throws InvalidPaymentException {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(500.0);
        when(mockOrder.getId()).thenReturn("ORD-1");
        when(mockPayment.getAmount()).thenReturn(500.0);
        when(mockPayment.process()).thenReturn(true);

        // When
        Receipt receipt = paymentService.processPayment(mockOrder, mockPayment);

        // Then
        assertNotNull(receipt);
        assertEquals("RCP-1", receipt.getReceiptNumber());
        verify(mockPayment).process();
        verify(mockOrder).processPayment(500.0);
    }

    @Test
    @DisplayName("Should process payment successfully when amount exceeds order price")
    void testProcessPayment_ExcessAmount() throws InvalidPaymentException {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(300.0);
        when(mockOrder.getId()).thenReturn("ORD-2");
        when(mockPayment.getAmount()).thenReturn(500.0);
        when(mockPayment.process()).thenReturn(true);

        // When
        Receipt receipt = paymentService.processPayment(mockOrder, mockPayment);

        // Then
        assertNotNull(receipt);
        verify(mockPayment).process();
        verify(mockOrder).processPayment(500.0);
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException when payment amount is insufficient")
    void testProcessPayment_InsufficientAmount() {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(500.0);
        when(mockPayment.getAmount()).thenReturn(400.0);

        // When & Then
        InvalidPaymentException exception = assertThrows(
            InvalidPaymentException.class,
            () -> paymentService.processPayment(mockOrder, mockPayment)
        );
        assertTrue(exception.getMessage().contains("Недостаточная сумма"));
        verify(mockPayment, never()).process();
        verify(mockOrder, never()).processPayment(anyDouble());
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException when payment processing fails")
    void testProcessPayment_ProcessingFailed() throws InvalidPaymentException {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(500.0);
        when(mockPayment.getAmount()).thenReturn(500.0);
        when(mockPayment.process()).thenReturn(false);

        // When & Then
        InvalidPaymentException exception = assertThrows(
            InvalidPaymentException.class,
            () -> paymentService.processPayment(mockOrder, mockPayment)
        );
        assertTrue(exception.getMessage().contains("Ошибка обработки платежа"));
        verify(mockPayment).process();
        verify(mockOrder, never()).processPayment(anyDouble());
    }

    @Test
    @DisplayName("Should generate unique receipt numbers for multiple payments")
    void testProcessPayment_MultipleReceipts() throws InvalidPaymentException {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(100.0);
        when(mockOrder.getId()).thenReturn("ORD-1");
        when(mockPayment.getAmount()).thenReturn(100.0);
        when(mockPayment.process()).thenReturn(true);

        // When
        Receipt receipt1 = paymentService.processPayment(mockOrder, mockPayment);
        Receipt receipt2 = paymentService.processPayment(mockOrder, mockPayment);
        Receipt receipt3 = paymentService.processPayment(mockOrder, mockPayment);

        // Then
        assertEquals("RCP-1", receipt1.getReceiptNumber());
        assertEquals("RCP-2", receipt2.getReceiptNumber());
        assertEquals("RCP-3", receipt3.getReceiptNumber());
    }

    @Test
    @DisplayName("Should refund payment successfully")
    void testRefundPayment_Success() throws InvalidPaymentException {
        // Given
        when(mockPayment.isSuccessful()).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> paymentService.refundPayment(mockPayment));
        verify(mockPayment).refund();
    }

    @Test
    @DisplayName("Should throw InvalidPaymentException when refunding unsuccessful payment")
    void testRefundPayment_UnsuccessfulPayment() {
        // Given
        when(mockPayment.isSuccessful()).thenReturn(false);

        // When & Then
        InvalidPaymentException exception = assertThrows(
            InvalidPaymentException.class,
            () -> paymentService.refundPayment(mockPayment)
        );
        assertTrue(exception.getMessage().contains("Невозможно вернуть неуспешный платеж"));
        verify(mockPayment, never()).refund();
    }

    @Test
    @DisplayName("Should calculate tax correctly at 13%")
    void testCalculateTax() {
        // Given
        double amount1 = 1000.0;
        double amount2 = 500.0;
        double amount3 = 250.0;

        // When
        double tax1 = paymentService.calculateTax(amount1);
        double tax2 = paymentService.calculateTax(amount2);
        double tax3 = paymentService.calculateTax(amount3);

        // Then
        assertEquals(130.0, tax1, 0.01);
        assertEquals(65.0, tax2, 0.01);
        assertEquals(32.5, tax3, 0.01);
    }

    @Test
    @DisplayName("Should calculate tax for zero amount")
    void testCalculateTax_ZeroAmount() {
        // When
        double tax = paymentService.calculateTax(0.0);

        // Then
        assertEquals(0.0, tax, 0.01);
    }

    @Test
    @DisplayName("Should calculate service fee correctly at 5%")
    void testCalculateServiceFee() {
        // Given
        double amount1 = 1000.0;
        double amount2 = 200.0;
        double amount3 = 750.0;

        // When
        double fee1 = paymentService.calculateServiceFee(amount1);
        double fee2 = paymentService.calculateServiceFee(amount2);
        double fee3 = paymentService.calculateServiceFee(amount3);

        // Then
        assertEquals(50.0, fee1, 0.01);
        assertEquals(10.0, fee2, 0.01);
        assertEquals(37.5, fee3, 0.01);
    }

    @Test
    @DisplayName("Should calculate service fee for zero amount")
    void testCalculateServiceFee_ZeroAmount() {
        // When
        double fee = paymentService.calculateServiceFee(0.0);

        // Then
        assertEquals(0.0, fee, 0.01);
    }

    @Test
    @DisplayName("Should handle exact payment amount")
    void testProcessPayment_ExactAmount() throws InvalidPaymentException {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(123.45);
        when(mockOrder.getId()).thenReturn("ORD-1");
        when(mockPayment.getAmount()).thenReturn(123.45);
        when(mockPayment.process()).thenReturn(true);

        // When
        Receipt receipt = paymentService.processPayment(mockOrder, mockPayment);

        // Then
        assertNotNull(receipt);
        verify(mockOrder).processPayment(123.45);
    }

    @Test
    @DisplayName("Should handle payment processing with minimal amount difference")
    void testProcessPayment_MinimalDifference() throws InvalidPaymentException {
        // Given
        when(mockOrder.getFinalPrice()).thenReturn(100.00);
        when(mockOrder.getId()).thenReturn("ORD-1");
        when(mockPayment.getAmount()).thenReturn(100.01);
        when(mockPayment.process()).thenReturn(true);

        // When
        Receipt receipt = paymentService.processPayment(mockOrder, mockPayment);

        // Then
        assertNotNull(receipt);
        verify(mockOrder).processPayment(100.01);
    }
}
