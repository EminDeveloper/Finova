package com.finova.ecommerce.finova.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finova.ecommerce.finova.dto.order.*;
import com.finova.ecommerce.finova.enums.OrderStatus;
import com.finova.ecommerce.finova.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OrderService orderService() {
            return Mockito.mock(OrderService.class);
        }
    }

    @Autowired
    private OrderService orderService;

    @Test
    void createOrder_shouldReturnCreateOrderResponse() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderDTO dto = new OrderDTO();
        dto.setAmount("100.00");
        dto.setCurrency("AZN");
        dto.setTypeRid("Order_SMS");
        dto.setLanguage("az");
        dto.setDescription("Demo order");
        request.setOrder(dto);

        CreateOrderResponse mockResponse = CreateOrderResponse.builder()
                .id(1L)
                .hppUrl("https://your-gateway.com/flex")
                .password("abc123")
                .status("PREPARING")
                .cvv2AuthStatus("Required")
                .secret("xyz987")
                .build();

        Mockito.when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("PREPARING"))
                .andExpect(jsonPath("$.cvv2AuthStatus").value("Required"));
    }

    @Test
    void executeTransaction_shouldReturnApprovalCode() throws Exception {
        ExecuteTransactionRequest req = new ExecuteTransactionRequest();
        TranDTO tran = new TranDTO();
        tran.setAmount("100.00");
        tran.setPhase("Single");
        req.setTran(tran);

        ExecutionResponse mockRes = ExecutionResponse.builder()
                .approvalCode("123456")
                .build();

        Mockito.when(orderService.executeTransaction(Mockito.eq(1L), any(ExecuteTransactionRequest.class)))
                .thenReturn(mockRes);

        mockMvc.perform(post("/order/1/exec-tran")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approvalCode").value("123456"));
    }

    @Test
    void getOrderStatus_shouldReturnStatus() throws Exception {
        OrderStatusResponse res = OrderStatusResponse.builder()
                .id(1L)
                .status(OrderStatus.SUCCESS.name())
                .amount("100.00")
                .build();

        Mockito.when(orderService.getOrderStatus(1L)).thenReturn(res);

        mockMvc.perform(get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.amount").value("100.00"));
    }
}
