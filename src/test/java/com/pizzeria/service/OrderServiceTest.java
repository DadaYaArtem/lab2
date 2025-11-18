package com.pizzeria.service;

import com.pizzeria.enums.OrderStatus;
import com.pizzeria.exceptions.DuplicateOrderException;
import com.pizzeria.exceptions.OrderNotFoundException;
import com.pizzeria.model.Order;
import com.pizzeria.model.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("OrderService Tests")
class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        when(mockCustomer.getFullName()).thenReturn("John Doe");
        when(mockCustomer.getId()).thenReturn("CUST-001");
    }

    @Test
    @DisplayName("Should create order successfully")
    void testCreateOrder_Success() throws DuplicateOrderException {
        // When
        Order order = orderService.createOrder(mockCustomer);

        // Then
        assertNotNull(order);
        assertEquals("ORD-1", order.getId());
        assertEquals(mockCustomer, order.getCustomer());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        verify(mockCustomer).addToOrderHistory("ORD-1");
    }

    @Test
    @DisplayName("Should increment order counter for multiple orders")
    void testCreateOrder_MultipleOrders() throws DuplicateOrderException {
        // When
        Order order1 = orderService.createOrder(mockCustomer);
        Order order2 = orderService.createOrder(mockCustomer);
        Order order3 = orderService.createOrder(mockCustomer);

        // Then
        assertEquals("ORD-1", order1.getId());
        assertEquals("ORD-2", order2.getId());
        assertEquals("ORD-3", order3.getId());
    }

    @Test
    @DisplayName("Should get order by ID successfully")
    void testGetOrder_Success() throws DuplicateOrderException, OrderNotFoundException {
        // Given
        Order createdOrder = orderService.createOrder(mockCustomer);

        // When
        Order retrievedOrder = orderService.getOrder("ORD-1");

        // Then
        assertNotNull(retrievedOrder);
        assertEquals(createdOrder.getId(), retrievedOrder.getId());
        assertEquals(createdOrder.getCustomer(), retrievedOrder.getCustomer());
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when order doesn't exist")
    void testGetOrder_NotFound() {
        // When & Then
        OrderNotFoundException exception = assertThrows(
            OrderNotFoundException.class,
            () -> orderService.getOrder("ORD-999")
        );
        assertTrue(exception.getMessage().contains("ORD-999"));
    }

    @Test
    @DisplayName("Should cancel order successfully")
    void testCancelOrder_Success() throws DuplicateOrderException, OrderNotFoundException {
        // Given
        Order order = orderService.createOrder(mockCustomer);
        assertEquals(OrderStatus.PENDING, order.getStatus());

        // When
        orderService.cancelOrder("ORD-1");

        // Then
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when canceling non-existent order")
    void testCancelOrder_NotFound() {
        // When & Then
        assertThrows(
            OrderNotFoundException.class,
            () -> orderService.cancelOrder("ORD-999")
        );
    }

    @Test
    @DisplayName("Should update order status successfully")
    void testUpdateOrderStatus_Success() throws DuplicateOrderException, OrderNotFoundException {
        // Given
        Order order = orderService.createOrder(mockCustomer);

        // When
        orderService.updateOrderStatus("ORD-1", OrderStatus.CONFIRMED);

        // Then
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    @DisplayName("Should update order status through various states")
    void testUpdateOrderStatus_MultipleTransitions() throws DuplicateOrderException, OrderNotFoundException {
        // Given
        Order order = orderService.createOrder(mockCustomer);

        // When & Then
        orderService.updateOrderStatus("ORD-1", OrderStatus.CONFIRMED);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());

        orderService.updateOrderStatus("ORD-1", OrderStatus.PREPARING);
        assertEquals(OrderStatus.PREPARING, order.getStatus());

        orderService.updateOrderStatus("ORD-1", OrderStatus.READY);
        assertEquals(OrderStatus.READY, order.getStatus());

        orderService.updateOrderStatus("ORD-1", OrderStatus.DELIVERING);
        assertEquals(OrderStatus.DELIVERING, order.getStatus());

        orderService.updateOrderStatus("ORD-1", OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException when updating non-existent order")
    void testUpdateOrderStatus_NotFound() {
        // When & Then
        assertThrows(
            OrderNotFoundException.class,
            () -> orderService.updateOrderStatus("ORD-999", OrderStatus.CONFIRMED)
        );
    }

    @Test
    @DisplayName("Should calculate total revenue correctly")
    void testCalculateTotalRevenue_WithPaidOrders() throws DuplicateOrderException {
        // Given
        Order order1 = orderService.createOrder(mockCustomer);
        Order order2 = orderService.createOrder(mockCustomer);
        Order order3 = orderService.createOrder(mockCustomer);

        order1.processPayment(100.0);
        order2.processPayment(200.0);
        // order3 is not paid

        // When
        double revenue = orderService.calculateTotalRevenue();

        // Then
        assertEquals(300.0, revenue, 0.01);
    }

    @Test
    @DisplayName("Should return zero revenue when no orders are paid")
    void testCalculateTotalRevenue_NoPaidOrders() throws DuplicateOrderException {
        // Given
        orderService.createOrder(mockCustomer);
        orderService.createOrder(mockCustomer);

        // When
        double revenue = orderService.calculateTotalRevenue();

        // Then
        assertEquals(0.0, revenue, 0.01);
    }

    @Test
    @DisplayName("Should return zero revenue when no orders exist")
    void testCalculateTotalRevenue_NoOrders() {
        // When
        double revenue = orderService.calculateTotalRevenue();

        // Then
        assertEquals(0.0, revenue, 0.01);
    }

    @Test
    @DisplayName("Should get order count correctly")
    void testGetOrderCount() throws DuplicateOrderException {
        // Given
        assertEquals(0, orderService.getOrderCount());

        // When
        orderService.createOrder(mockCustomer);
        orderService.createOrder(mockCustomer);
        orderService.createOrder(mockCustomer);

        // Then
        assertEquals(3, orderService.getOrderCount());
    }

    @Test
    @DisplayName("Should get all orders")
    void testGetAllOrders() throws DuplicateOrderException {
        // Given
        orderService.createOrder(mockCustomer);
        orderService.createOrder(mockCustomer);

        // When
        Map<String, Order> allOrders = orderService.getAllOrders();

        // Then
        assertNotNull(allOrders);
        assertEquals(2, allOrders.size());
        assertTrue(allOrders.containsKey("ORD-1"));
        assertTrue(allOrders.containsKey("ORD-2"));
    }

    @Test
    @DisplayName("Should return immutable copy of orders map")
    void testGetAllOrders_ReturnsDefensiveCopy() throws DuplicateOrderException {
        // Given
        orderService.createOrder(mockCustomer);

        // When
        Map<String, Order> orders1 = orderService.getAllOrders();
        Map<String, Order> orders2 = orderService.getAllOrders();

        // Then
        assertNotSame(orders1, orders2);
        assertEquals(orders1.size(), orders2.size());
    }

    @Test
    @DisplayName("Should handle empty order list")
    void testGetAllOrders_Empty() {
        // When
        Map<String, Order> allOrders = orderService.getAllOrders();

        // Then
        assertNotNull(allOrders);
        assertTrue(allOrders.isEmpty());
    }
}
