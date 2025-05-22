package com.finova.ecommerce.finova.service;

import com.finova.ecommerce.finova.dto.order.ExecuteTransactionRequest;
import com.finova.ecommerce.finova.entity.Order;
import com.finova.ecommerce.finova.entity.OrderEntity;
import com.finova.ecommerce.finova.enums.OrderStatus;
import com.finova.ecommerce.finova.dto.order.CreateOrderRequest;
import com.finova.ecommerce.finova.dto.order.CreateOrderResponse;
import com.finova.ecommerce.finova.dto.order.ExecutionResponse;
import com.finova.ecommerce.finova.dto.order.OrderStatusResponse;
import com.finova.ecommerce.finova.models.Transaction;
import com.finova.ecommerce.finova.repositories.OrderRepository;
import com.finova.ecommerce.finova.repositories.TransactionRepository;
import com.finova.ecommerce.finova.utility.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setTypeRid(request.getOrder().getTypeRid());
        order.setAmount(request.getOrder().getAmount());
        order.setCurrency(request.getOrder().getCurrency());
        order.setLanguage(request.getOrder().getLanguage());
        order.setDescription(request.getOrder().getDescription());
        order.setStatus(OrderStatus.PREPARING);
        order.setPassword(RandomUtil.generatePassword());
        order.setSecret(UUID.randomUUID().toString().substring(0, 6));
        Order savedOrder = orderRepository.save(order);

        return CreateOrderResponse.from(savedOrder);
    }

    public ExecutionResponse executeTransaction(Long id, ExecuteTransactionRequest req) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        Transaction tran = new Transaction();
        tran.setOrderId(order.getId());
        tran.setPhase(req.getTran().getPhase());
        tran.setAmount(req.getTran().getAmount());
        tran.setApprovalCode(RandomUtil.approvalCode());

        transactionRepository.save(tran);

        order.setStatus(OrderStatus.SUCCESS);
        orderRepository.save(order);

        return ExecutionResponse.from(tran);
    }


    public OrderStatusResponse getOrderStatus(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        return OrderStatusResponse.from(order);
    }
}
