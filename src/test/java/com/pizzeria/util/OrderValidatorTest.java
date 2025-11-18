package com.pizzeria.util;

import com.pizzeria.model.Address;
import com.pizzeria.model.Order;
import com.pizzeria.model.OrderItem;
import com.pizzeria.model.products.Product;
import com.pizzeria.model.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderValidator
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("OrderValidator Tests")
class OrderValidatorTest {

    @Mock
    private Order mockOrder;

    @Mock
    private Customer mockCustomer;

    @Mock
    private OrderItem mockOrderItem;

    @Mock
    private Product mockProduct;

    @Mock
    private Address mockAddress;

    private List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        orderItems = new ArrayList<>();
    }

    @Test
    @DisplayName("Should validate order successfully when all conditions are met")
    void testValidateOrder_Success() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(2);
        when(mockProduct.isAvailable()).thenReturn(true);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should fail validation when order is null")
    void testValidateOrder_NullOrder() {
        // When
        boolean result = OrderValidator.validateOrder(null);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail validation when customer is null")
    void testValidateOrder_NullCustomer() {
        // Given
        when(mockOrder.getCustomer()).thenReturn(null);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
        verify(mockOrder).getCustomer();
    }

    @Test
    @DisplayName("Should fail validation when order has no items")
    void testValidateOrder_EmptyItems() {
        // Given
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(new ArrayList<>());

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail validation when order item has null product")
    void testValidateOrder_NullProduct() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(null);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail validation when order item has zero quantity")
    void testValidateOrder_ZeroQuantity() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(0);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail validation when order item has negative quantity")
    void testValidateOrder_NegativeQuantity() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(-1);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should fail validation when product is not available")
    void testValidateOrder_ProductNotAvailable() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(1);
        when(mockProduct.isAvailable()).thenReturn(false);
        when(mockProduct.getName()).thenReturn("Test Product");

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should validate order with multiple items successfully")
    void testValidateOrder_MultipleItems() {
        // Given
        OrderItem mockOrderItem2 = mock(OrderItem.class);
        Product mockProduct2 = mock(Product.class);

        orderItems.add(mockOrderItem);
        orderItems.add(mockOrderItem2);

        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);

        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(2);
        when(mockProduct.isAvailable()).thenReturn(true);

        when(mockOrderItem2.getProduct()).thenReturn(mockProduct2);
        when(mockOrderItem2.getQuantity()).thenReturn(3);
        when(mockProduct2.isAvailable()).thenReturn(true);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should fail validation when one of multiple items is invalid")
    void testValidateOrder_MultipleItems_OneInvalid() {
        // Given
        OrderItem mockOrderItem2 = mock(OrderItem.class);
        Product mockProduct2 = mock(Product.class);

        orderItems.add(mockOrderItem);
        orderItems.add(mockOrderItem2);

        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);

        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(2);
        when(mockProduct.isAvailable()).thenReturn(true);

        when(mockOrderItem2.getProduct()).thenReturn(mockProduct2);
        when(mockOrderItem2.getQuantity()).thenReturn(3);
        when(mockProduct2.isAvailable()).thenReturn(false);
        when(mockProduct2.getName()).thenReturn("Unavailable Product");

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should validate minimum order amount - success")
    void testValidateMinimumOrderAmount_Success() {
        // Given
        when(mockOrder.getPrice()).thenReturn(500.0);
        double minimumAmount = 100.0;

        // When
        boolean result = OrderValidator.validateMinimumOrderAmount(mockOrder, minimumAmount);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should validate minimum order amount - exact match")
    void testValidateMinimumOrderAmount_ExactMatch() {
        // Given
        when(mockOrder.getPrice()).thenReturn(100.0);
        double minimumAmount = 100.0;

        // When
        boolean result = OrderValidator.validateMinimumOrderAmount(mockOrder, minimumAmount);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should fail validation when order amount is below minimum")
    void testValidateMinimumOrderAmount_BelowMinimum() {
        // Given
        when(mockOrder.getPrice()).thenReturn(50.0);
        double minimumAmount = 100.0;

        // When
        boolean result = OrderValidator.validateMinimumOrderAmount(mockOrder, minimumAmount);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should validate minimum order amount with zero minimum")
    void testValidateMinimumOrderAmount_ZeroMinimum() {
        // Given
        when(mockOrder.getPrice()).thenReturn(50.0);
        double minimumAmount = 0.0;

        // When
        boolean result = OrderValidator.validateMinimumOrderAmount(mockOrder, minimumAmount);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should validate delivery address - success")
    void testValidateDeliveryAddress_Success() {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);

        // When
        boolean result = OrderValidator.validateDeliveryAddress(mockOrder);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should fail validation when delivery address is null")
    void testValidateDeliveryAddress_Null() {
        // Given
        when(mockOrder.getDeliveryAddress()).thenReturn(null);

        // When
        boolean result = OrderValidator.validateDeliveryAddress(mockOrder);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should validate complete order with all checks")
    void testValidateOrder_CompleteValidation() {
        // Given - valid order
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrder.getPrice()).thenReturn(500.0);
        when(mockOrder.getDeliveryAddress()).thenReturn(mockAddress);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(2);
        when(mockProduct.isAvailable()).thenReturn(true);

        // When
        boolean orderValid = OrderValidator.validateOrder(mockOrder);
        boolean minimumValid = OrderValidator.validateMinimumOrderAmount(mockOrder, 100.0);
        boolean addressValid = OrderValidator.validateDeliveryAddress(mockOrder);

        // Then
        assertTrue(orderValid);
        assertTrue(minimumValid);
        assertTrue(addressValid);
    }

    @Test
    @DisplayName("Should handle edge case - very large order amount")
    void testValidateMinimumOrderAmount_LargeAmount() {
        // Given
        when(mockOrder.getPrice()).thenReturn(999999.99);
        double minimumAmount = 100.0;

        // When
        boolean result = OrderValidator.validateMinimumOrderAmount(mockOrder, minimumAmount);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should handle edge case - very small order amount")
    void testValidateMinimumOrderAmount_SmallAmount() {
        // Given
        when(mockOrder.getPrice()).thenReturn(0.01);
        double minimumAmount = 1.0;

        // When
        boolean result = OrderValidator.validateMinimumOrderAmount(mockOrder, minimumAmount);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Should validate order with positive quantity")
    void testValidateOrder_PositiveQuantity() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(100);
        when(mockProduct.isAvailable()).thenReturn(true);

        // When
        boolean result = OrderValidator.validateOrder(mockOrder);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Should verify all validation methods are called")
    void testValidateOrder_VerifyMethodCalls() {
        // Given
        orderItems.add(mockOrderItem);
        when(mockOrder.getCustomer()).thenReturn(mockCustomer);
        when(mockOrder.getItems()).thenReturn(orderItems);
        when(mockOrderItem.getProduct()).thenReturn(mockProduct);
        when(mockOrderItem.getQuantity()).thenReturn(1);
        when(mockProduct.isAvailable()).thenReturn(true);

        // When
        OrderValidator.validateOrder(mockOrder);

        // Then
        verify(mockOrder).getCustomer();
        verify(mockOrder, atLeastOnce()).getItems();
        verify(mockOrderItem).getProduct();
        verify(mockOrderItem).getQuantity();
        verify(mockProduct).isAvailable();
    }
}
