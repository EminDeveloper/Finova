package com.finova.ecommerce.finova.controllers;

import com.finova.ecommerce.finova.dto.order.CreateOrderRequest;
import com.finova.ecommerce.finova.dto.order.CreateOrderResponse;
import com.finova.ecommerce.finova.dto.order.ExecuteTransactionRequest;
import com.finova.ecommerce.finova.dto.order.ExecutionResponse;
import com.finova.ecommerce.finova.dto.order.OrderStatusResponse;
import com.finova.ecommerce.finova.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor // ✅ Constructor injection for final field
public class OrderController {

    private final OrderService orderService; // ✅ Correct

    @PostMapping("/")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PostMapping("/{id}/exec-tran")
    public ResponseEntity<ExecutionResponse> executeTran(@PathVariable Long id, @RequestBody ExecuteTransactionRequest request) {
        return ResponseEntity.ok(orderService.executeTransaction(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatusResponse> getOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderStatus(id));
    }
}
