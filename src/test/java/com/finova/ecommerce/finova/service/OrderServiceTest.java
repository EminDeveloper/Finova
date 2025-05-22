package com.finova.ecommerce.finova.service;
import com.finova.ecommerce.finova.dto.order.*;
import com.finova.ecommerce.finova.entity.Order;

import com.finova.ecommerce.finova.enums.OrderStatus;

import com.finova.ecommerce.finova.models.Transaction;
import com.finova.ecommerce.finova.repositories.OrderRepository;
import com.finova.ecommerce.finova.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_shouldSaveOrderAndReturnResponse() {
        // Given
        OrderDTO dto = new OrderDTO();
        dto.setTypeRid("Order_SMS");
        dto.setAmount("100.00");
        dto.setCurrency("AZN");
        dto.setLanguage("az");
        dto.setDescription("Test payment");

        CreateOrderRequest request = new CreateOrderRequest();
        request.setOrder(dto);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setTypeRid("Order_SMS");
        savedOrder.setAmount("100.00");
        savedOrder.setCurrency("AZN");
        savedOrder.setLanguage("az");
        savedOrder.setDescription("Test payment");
        savedOrder.setPassword("secret");
        savedOrder.setSecret("abcdef");
        savedOrder.setStatus(OrderStatus.PREPARING);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // When
        CreateOrderResponse response = orderService.createOrder(request);

        // Then
        assertEquals(1L, response.getId());
        assertEquals("https://your-gateway.com/flex", response.getHppUrl());
        assertEquals("PREPARING", response.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void executeTransaction_shouldUpdateOrderAndSaveTransaction() {
        // Given
        Long orderId = 1L;

        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.PREPARING);

        ExecuteTransactionRequest req = new ExecuteTransactionRequest();
        TranDTO tranDTO = new TranDTO();
        tranDTO.setAmount("100.00");
        tranDTO.setPhase("Single");
        req.setTran(tranDTO);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction t = invocation.getArgument(0);
            t.setId(99L);
            return t;
        });

        // When
        ExecutionResponse response = orderService.executeTransaction(orderId, req);

        // Then
        assertNotNull(response.getApprovalCode());
        verify(orderRepository, times(1)).save(order);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getOrderStatus_shouldReturnCorrectStatus() {
        // Given
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.SUCCESS);
        order.setAmount("100.00");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // When
        OrderStatusResponse response = orderService.getOrderStatus(orderId);

        // Then
        assertEquals("SUCCESS", response.getStatus());
        assertEquals("100.00", response.getAmount());
    }

    @Test
    void getOrderStatus_shouldThrowIfNotFound() {
        Long orderId = 42L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrderStatus(orderId));
    }
}
