package com.awesome.pizza.controller;

import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.dto.customer.CustomerOrderResponseDto;
import com.awesome.pizza.dto.customer.OrderInsertRequestDto;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.service.CustomerOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerOrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private CustomerOrderService customerOrderService;

    @Test
    public void whenNoAuthenticatedThenReturnOk() throws Exception {
        CustomerOrderResponseDto order = CustomerOrderResponseDto.builder()
                .status(OrderStatus.SUBMITTED)
                .build();
        doReturn(order).when(customerOrderService).retrieveOrder(1);
        mockMvc.perform(get("/v1/order/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When a new order is submitted a order number is returned")
    public void whenSubmitAnOrderThenReturnOrderNumber() throws Exception {
        OrderInsertRequestDto createOrderDto = OrderInsertRequestDto.builder()
                .username("user")
                .pizzaType(PizzaType.MARGHERITA)
                .shippingAddress("testAddress")
                .build();
        mockMvc.perform(post("/v1/order/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsBytes(createOrderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(1))
                .andExpect(jsonPath("$.status").value("SUBMITTED"));
    }

    @Test
    @DisplayName("Requesting order status for a inexistent order return not found")
    public void whenRequestingStatusForInvalidOrderNumberThenReturnNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(customerOrderService).retrieveOrder(-1);
        mockMvc.perform(get("/v1/order/-1"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.statusCode").value(404));
    }

}