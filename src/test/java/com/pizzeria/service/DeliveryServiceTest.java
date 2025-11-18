package com.pizzeria.service;

import com.pizzeria.exceptions.InvalidDeliveryAddressException;
import com.pizzeria.model.Address;
import com.pizzeria.model.DeliveryInfo;
import com.pizzeria.model.Order;
import com.pizzeria.model.users.DeliveryDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DeliveryService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("DeliveryService Tests")
class DeliveryServiceTest {

    private DeliveryService deliveryService;

    @Mock
    private DeliveryDriver mockDriver1;

    @Mock
    private DeliveryDriver mockDriver2;

    @Mock
    private Order mockOrder;

    @Mock
    private Address mockAddress;

    @BeforeEach
    void setUp() {
        deliveryService = new DeliveryService();
        when(mockDriver1.getFullName()).thenReturn("Driver One");
        when(mockDriver2.getFullName()).thenReturn("Driver Two");
        when(mockOrder.getId()).thenReturn("ORD-1");
    }

    @Test
    @DisplayName("Should add driver successfully")
    void testAddDriver() {
        // When
        deliveryService.addDriver(mockDriver1);

        // Then
        DeliveryDriver found = deliveryService.findAvailableDriver();
        assertNotNull(found);
    }

    @Test
    @DisplayName("Should add multiple drivers")
    void testAddDriver_Multiple() {
        // When
        deliveryService.addDriver(mockDriver1);
        deliveryService.addDriver(mockDriver2);

        // Then
        assertEquals(0, deliveryService.getActiveDeliveriesCount());
    }

    @Test
    @DisplayName("Should find available driver")
    void testFindAvailableDriver_Success() {
        // Given
        when(mockDriver1.isAvailable()).thenReturn(true);
        deliveryService.addDriver(mockDriver1);

        // When
        DeliveryDriver driver = deliveryService.findAvailableDriver();

        // Then
        assertNotNull(driver);
        assertEquals(mockDriver1, driver);
    }

    @Test
    @DisplayName("Should return null when no drivers available")
    void testFindAvailableDriver_NoneAvailable() {
        // Given
        when(mockDriver1.isAvailable()).thenReturn(false);
        when(mockDriver2.isAvailable()).thenReturn(false);
        deliveryService.addDriver(mockDriver1);
        deliveryService.addDriver(mockDriver2);

        // When
        DeliveryDriver driver = deliveryService.findAvailableDriver();

        // Then
        assertNull(driver);
    }

    @Test
    @DisplayName("Should return null when no drivers exist")
    void testFindAvailableDriver_NoDrivers() {
        // When
        DeliveryDriver driver = deliveryService.findAvailableDriver();

        // Then
        assertNull(driver);
    }

    @Test
    @DisplayName("Should return first available driver")
    void testFindAvailableDriver_FirstAvailable() {
        // Given
        when(mockDriver1.isAvailable()).thenReturn(false);
        when(mockDriver2.isAvailable()).thenReturn(true);
        deliveryService.addDriver(mockDriver1);
        deliveryService.addDriver(mockDriver2);

        // When
        DeliveryDriver driver = deliveryService.findAvailableDriver();

        // Then
        assertNotNull(driver);
        assertEquals(mockDriver2, driver);
    }

    @Test
    @DisplayName("Should schedule delivery successfully")
    void testScheduleDelivery_Success() throws InvalidDeliveryAddressException {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);
        deliveryService.addDriver(mockDriver1);

        // When
        DeliveryInfo delivery = deliveryService.scheduleDelivery(mockOrder, mockDriver1);

        // Then
        assertNotNull(delivery);
        assertEquals(1, deliveryService.getActiveDeliveriesCount());
        verify(mockDriver1).startDelivery();
    }

    @Test
    @DisplayName("Should throw InvalidDeliveryAddressException when address is null")
    void testScheduleDelivery_NullAddress() {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(null);

        // When & Then
        InvalidDeliveryAddressException exception = assertThrows(
            InvalidDeliveryAddressException.class,
            () -> deliveryService.scheduleDelivery(mockOrder, mockDriver1)
        );
        assertTrue(exception.getMessage().contains("Адрес доставки не указан"));
        verify(mockDriver1, never()).startDelivery();
    }

    @Test
    @DisplayName("Should handle multiple scheduled deliveries")
    void testScheduleDelivery_Multiple() throws InvalidDeliveryAddressException {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);
        Order mockOrder2 = mock(Order.class);
        when(mockOrder2.getDeliveryAddress()).thenReturn(mockAddress);
        when(mockOrder2.getId()).thenReturn("ORD-2");

        // When
        deliveryService.scheduleDelivery(mockOrder, mockDriver1);
        deliveryService.scheduleDelivery(mockOrder2, mockDriver2);

        // Then
        assertEquals(2, deliveryService.getActiveDeliveriesCount());
    }

    @Test
    @DisplayName("Should complete delivery successfully")
    void testCompleteDelivery() throws InvalidDeliveryAddressException {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);
        DeliveryInfo delivery = deliveryService.scheduleDelivery(mockOrder, mockDriver1);
        assertEquals(1, deliveryService.getActiveDeliveriesCount());

        // When
        deliveryService.completeDelivery(delivery);

        // Then
        assertEquals(0, deliveryService.getActiveDeliveriesCount());
        verify(mockDriver1).completeDelivery();
    }

    @Test
    @DisplayName("Should validate address correctly - valid address")
    void testValidateAddress_Valid() {
        // Given
        when(mockAddress.getStreet()).thenReturn("Main Street");
        when(mockAddress.getHouseNumber()).thenReturn("123");
        when(mockAddress.getCity()).thenReturn("New York");

        // When
        boolean isValid = deliveryService.validateAddress(mockAddress);

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Should validate address correctly - null address")
    void testValidateAddress_Null() {
        // When
        boolean isValid = deliveryService.validateAddress(null);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should validate address correctly - missing street")
    void testValidateAddress_MissingStreet() {
        // Given
        when(mockAddress.getStreet()).thenReturn(null);
        when(mockAddress.getHouseNumber()).thenReturn("123");
        when(mockAddress.getCity()).thenReturn("New York");

        // When
        boolean isValid = deliveryService.validateAddress(mockAddress);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should validate address correctly - missing house number")
    void testValidateAddress_MissingHouseNumber() {
        // Given
        when(mockAddress.getStreet()).thenReturn("Main Street");
        when(mockAddress.getHouseNumber()).thenReturn(null);
        when(mockAddress.getCity()).thenReturn("New York");

        // When
        boolean isValid = deliveryService.validateAddress(mockAddress);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should validate address correctly - missing city")
    void testValidateAddress_MissingCity() {
        // Given
        when(mockAddress.getStreet()).thenReturn("Main Street");
        when(mockAddress.getHouseNumber()).thenReturn("123");
        when(mockAddress.getCity()).thenReturn(null);

        // When
        boolean isValid = deliveryService.validateAddress(mockAddress);

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Should get active deliveries count correctly")
    void testGetActiveDeliveriesCount() throws InvalidDeliveryAddressException {
        // Given
        assertEquals(0, deliveryService.getActiveDeliveriesCount());
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);

        // When
        deliveryService.scheduleDelivery(mockOrder, mockDriver1);

        // Then
        assertEquals(1, deliveryService.getActiveDeliveriesCount());
    }

    @Test
    @DisplayName("Should track active deliveries count correctly")
    void testGetActiveDeliveriesCount_AfterCompletions() throws InvalidDeliveryAddressException {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);
        Order mockOrder2 = mock(Order.class);
        when(mockOrder2.getDeliveryAddress()).thenReturn(mockAddress);

        // When
        DeliveryInfo delivery1 = deliveryService.scheduleDelivery(mockOrder, mockDriver1);
        DeliveryInfo delivery2 = deliveryService.scheduleDelivery(mockOrder2, mockDriver2);
        assertEquals(2, deliveryService.getActiveDeliveriesCount());

        deliveryService.completeDelivery(delivery1);

        // Then
        assertEquals(1, deliveryService.getActiveDeliveriesCount());
    }

    @Test
    @DisplayName("Should handle delivery lifecycle correctly")
    void testDeliveryLifecycle() throws InvalidDeliveryAddressException {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);
        when(mockDriver1.isAvailable()).thenReturn(true);
        deliveryService.addDriver(mockDriver1);

        // When - Schedule
        DeliveryInfo delivery = deliveryService.scheduleDelivery(mockOrder, mockDriver1);
        assertEquals(1, deliveryService.getActiveDeliveriesCount());

        // When - Complete
        deliveryService.completeDelivery(delivery);

        // Then
        assertEquals(0, deliveryService.getActiveDeliveriesCount());
        verify(mockDriver1).startDelivery();
        verify(mockDriver1).completeDelivery();
    }
}
