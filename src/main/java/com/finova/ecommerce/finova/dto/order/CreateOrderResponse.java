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
public class CreateOrderResponse {
    private Long id;
    private String hppUrl;
    private String password;
    private String status;
    private String cvv2AuthStatus;
    private String secret;

    public static CreateOrderResponse from(Order order) {
        return CreateOrderResponse.builder()
                .id(order.getId())
                .hppUrl("https://your-gateway.com/flex")
                .password(order.getPassword())
                .status(order.getStatus().name())
                .cvv2AuthStatus("Required")
                .secret(order.getSecret())
                .build();
    }
}
