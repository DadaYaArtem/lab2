package com.pizzeria.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for IdGenerator
 */
@DisplayName("IdGenerator Tests")
class IdGeneratorTest {

    @BeforeEach
    void setUp() {
        // Reset counters before each test to ensure consistent behavior
        IdGenerator.resetCounters();
    }

    @AfterEach
    void tearDown() {
        // Reset counters after each test for clean state
        IdGenerator.resetCounters();
    }

    @Test
    @DisplayName("Should generate order ID with correct format")
    void testGenerateOrderId_Format() {
        // When
        String orderId = IdGenerator.generateOrderId();

        // Then
        assertNotNull(orderId);
        assertTrue(orderId.startsWith("ORD-"));
        assertEquals(10, orderId.length()); // ORD- + 6 digits
    }

    @Test
    @DisplayName("Should generate sequential order IDs")
    void testGenerateOrderId_Sequential() {
        // When
        String id1 = IdGenerator.generateOrderId();
        String id2 = IdGenerator.generateOrderId();
        String id3 = IdGenerator.generateOrderId();

        // Then
        assertEquals("ORD-000001", id1);
        assertEquals("ORD-000002", id2);
        assertEquals("ORD-000003", id3);
    }

    @Test
    @DisplayName("Should generate unique order IDs")
    void testGenerateOrderId_Uniqueness() {
        // Given
        Set<String> ids = new HashSet<>();
        int count = 100;

        // When
        for (int i = 0; i < count; i++) {
            ids.add(IdGenerator.generateOrderId());
        }

        // Then
        assertEquals(count, ids.size());
    }

    @Test
    @DisplayName("Should generate customer ID with correct format")
    void testGenerateCustomerId_Format() {
        // When
        String customerId = IdGenerator.generateCustomerId();

        // Then
        assertNotNull(customerId);
        assertTrue(customerId.startsWith("CUST-"));
        assertEquals(11, customerId.length()); // CUST- + 6 digits
    }

    @Test
    @DisplayName("Should generate sequential customer IDs")
    void testGenerateCustomerId_Sequential() {
        // When
        String id1 = IdGenerator.generateCustomerId();
        String id2 = IdGenerator.generateCustomerId();
        String id3 = IdGenerator.generateCustomerId();

        // Then
        assertEquals("CUST-000001", id1);
        assertEquals("CUST-000002", id2);
        assertEquals("CUST-000003", id3);
    }

    @Test
    @DisplayName("Should generate unique customer IDs")
    void testGenerateCustomerId_Uniqueness() {
        // Given
        Set<String> ids = new HashSet<>();
        int count = 100;

        // When
        for (int i = 0; i < count; i++) {
            ids.add(IdGenerator.generateCustomerId());
        }

        // Then
        assertEquals(count, ids.size());
    }

    @Test
    @DisplayName("Should generate employee ID with correct format")
    void testGenerateEmployeeId_Format() {
        // When
        String employeeId = IdGenerator.generateEmployeeId();

        // Then
        assertNotNull(employeeId);
        assertTrue(employeeId.startsWith("EMP-"));
        assertEquals(10, employeeId.length()); // EMP- + 6 digits
    }

    @Test
    @DisplayName("Should generate sequential employee IDs")
    void testGenerateEmployeeId_Sequential() {
        // When
        String id1 = IdGenerator.generateEmployeeId();
        String id2 = IdGenerator.generateEmployeeId();
        String id3 = IdGenerator.generateEmployeeId();

        // Then
        assertEquals("EMP-000001", id1);
        assertEquals("EMP-000002", id2);
        assertEquals("EMP-000003", id3);
    }

    @Test
    @DisplayName("Should generate unique employee IDs")
    void testGenerateEmployeeId_Uniqueness() {
        // Given
        Set<String> ids = new HashSet<>();
        int count = 100;

        // When
        for (int i = 0; i < count; i++) {
            ids.add(IdGenerator.generateEmployeeId());
        }

        // Then
        assertEquals(count, ids.size());
    }

    @Test
    @DisplayName("Should generate UUID with correct format")
    void testGenerateUUID_Format() {
        // When
        String uuid = IdGenerator.generateUUID();

        // Then
        assertNotNull(uuid);
        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    @Test
    @DisplayName("Should generate unique UUIDs")
    void testGenerateUUID_Uniqueness() {
        // Given
        Set<String> uuids = new HashSet<>();
        int count = 1000;

        // When
        for (int i = 0; i < count; i++) {
            uuids.add(IdGenerator.generateUUID());
        }

        // Then
        assertEquals(count, uuids.size());
    }

    @Test
    @DisplayName("Should generate transaction ID with prefix")
    void testGenerateTransactionId_WithPrefix() {
        // When
        String txnId = IdGenerator.generateTransactionId("PAYMENT");

        // Then
        assertNotNull(txnId);
        assertTrue(txnId.startsWith("PAYMENT-"));
    }

    @Test
    @DisplayName("Should generate transaction ID with timestamp")
    void testGenerateTransactionId_ContainsTimestamp() throws InterruptedException {
        // When
        String txnId1 = IdGenerator.generateTransactionId("TXN");
        Thread.sleep(1); // Ensure different timestamp
        String txnId2 = IdGenerator.generateTransactionId("TXN");

        // Then
        assertNotEquals(txnId1, txnId2);
    }

    @Test
    @DisplayName("Should generate different transaction IDs with different prefixes")
    void testGenerateTransactionId_DifferentPrefixes() {
        // When
        String paymentId = IdGenerator.generateTransactionId("PAYMENT");
        String refundId = IdGenerator.generateTransactionId("REFUND");

        // Then
        assertTrue(paymentId.startsWith("PAYMENT-"));
        assertTrue(refundId.startsWith("REFUND-"));
        assertNotEquals(paymentId, refundId);
    }

    @Test
    @DisplayName("Should reset counters successfully")
    void testResetCounters() {
        // Given
        IdGenerator.generateOrderId();
        IdGenerator.generateCustomerId();
        IdGenerator.generateEmployeeId();

        // When
        IdGenerator.resetCounters();

        // Then
        assertEquals("ORD-000001", IdGenerator.generateOrderId());
        assertEquals("CUST-000001", IdGenerator.generateCustomerId());
        assertEquals("EMP-000001", IdGenerator.generateEmployeeId());
    }

    @Test
    @DisplayName("Should maintain separate counters for different ID types")
    void testSeparateCounters() {
        // When
        String orderId1 = IdGenerator.generateOrderId();
        String customerId1 = IdGenerator.generateCustomerId();
        String employeeId1 = IdGenerator.generateEmployeeId();
        String orderId2 = IdGenerator.generateOrderId();
        String customerId2 = IdGenerator.generateCustomerId();
        String employeeId2 = IdGenerator.generateEmployeeId();

        // Then
        assertEquals("ORD-000001", orderId1);
        assertEquals("ORD-000002", orderId2);
        assertEquals("CUST-000001", customerId1);
        assertEquals("CUST-000002", customerId2);
        assertEquals("EMP-000001", employeeId1);
        assertEquals("EMP-000002", employeeId2);
    }

    @Test
    @DisplayName("Should handle large number of order IDs")
    void testGenerateOrderId_LargeNumbers() {
        // When
        for (int i = 0; i < 999; i++) {
            IdGenerator.generateOrderId();
        }
        String orderId = IdGenerator.generateOrderId();

        // Then
        assertEquals("ORD-001000", orderId);
    }

    @Test
    @DisplayName("Should handle large number of customer IDs")
    void testGenerateCustomerId_LargeNumbers() {
        // When
        for (int i = 0; i < 9999; i++) {
            IdGenerator.generateCustomerId();
        }
        String customerId = IdGenerator.generateCustomerId();

        // Then
        assertEquals("CUST-010000", customerId);
    }

    @Test
    @DisplayName("Should pad order ID with leading zeros")
    void testGenerateOrderId_Padding() {
        // When
        String id1 = IdGenerator.generateOrderId();
        String id10 = IdGenerator.generateOrderId();

        // Then
        assertTrue(id1.matches("ORD-\\d{6}"));
        assertTrue(id10.matches("ORD-\\d{6}"));
    }

    @Test
    @DisplayName("Should pad customer ID with leading zeros")
    void testGenerateCustomerId_Padding() {
        // When
        String id1 = IdGenerator.generateCustomerId();

        // Then
        assertTrue(id1.matches("CUST-\\d{6}"));
    }

    @Test
    @DisplayName("Should pad employee ID with leading zeros")
    void testGenerateEmployeeId_Padding() {
        // When
        String id1 = IdGenerator.generateEmployeeId();

        // Then
        assertTrue(id1.matches("EMP-\\d{6}"));
    }

    @Test
    @DisplayName("Should generate thread-safe IDs")
    void testGenerateOrderId_ThreadSafety() {
        // Given
        Set<String> ids = new HashSet<>();
        int threadCount = 10;
        int idsPerThread = 100;

        // When
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < idsPerThread; j++) {
                    synchronized (ids) {
                        ids.add(IdGenerator.generateOrderId());
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                fail("Thread interrupted");
            }
        }

        // Then
        assertEquals(threadCount * idsPerThread, ids.size());
    }

    @Test
    @DisplayName("Should generate valid UUID format")
    void testGenerateUUID_ValidFormat() {
        // When
        String uuid = IdGenerator.generateUUID();

        // Then
        assertTrue(uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
    }

    @Test
    @DisplayName("Should generate transaction ID with empty prefix")
    void testGenerateTransactionId_EmptyPrefix() {
        // When
        String txnId = IdGenerator.generateTransactionId("");

        // Then
        assertTrue(txnId.startsWith("-"));
    }

    @Test
    @DisplayName("Should verify counter increment after generation")
    void testCounterIncrement() {
        // When
        String id1 = IdGenerator.generateOrderId();
        String id2 = IdGenerator.generateOrderId();

        // Then - extract numbers and verify increment
        int num1 = Integer.parseInt(id1.substring(4));
        int num2 = Integer.parseInt(id2.substring(4));
        assertEquals(num1 + 1, num2);
    }

    @Test
    @DisplayName("Should generate IDs with correct length after reset")
    void testIdLength_AfterReset() {
        // Given
        for (int i = 0; i < 100; i++) {
            IdGenerator.generateOrderId();
        }

        // When
        IdGenerator.resetCounters();
        String orderId = IdGenerator.generateOrderId();

        // Then
        assertEquals(10, orderId.length());
        assertEquals("ORD-000001", orderId);
    }
}
