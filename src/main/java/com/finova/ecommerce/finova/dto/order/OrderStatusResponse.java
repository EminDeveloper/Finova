package com.finova.ecommerce.finova.dto.order;

import com.finova.ecommerce.finova.entity.Order;
import com.finova.ecommerce.finova.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusResponse {
    private Long id;
    private String status;
    private String amount;

    public static OrderStatusResponse from(Order order) {
        return OrderStatusResponse.builder()
                .id(order.getId())
                .status(order.getStatus().name())
                .amount(order.getAmount())
                .build();
    }
}