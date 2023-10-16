package com.awesome.pizza.service;

import com.awesome.pizza.AnotherOrderInProcesssException;
import com.awesome.pizza.OrderRepository;
import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.dto.admin.AdminOrderResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.mapper.OrderMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PizzaChefOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private PizzaChefOrderService orderService;

    @Test
    @DisplayName("When no order already in PROCESSING state then return the next order to process")
    public void shouldGetNextOrderWhenNoOrderInProcess(){
        OrderEntity entity = OrderEntity.builder()
                .username("user")
                .orderNumber(1)
                .pizzaType(PizzaType.MARGHERITA)
                .build();
        when(orderRepository.findByState(OrderStatus.PROCESSING)).thenReturn(Optional.empty());
        when(orderRepository.findByState(OrderStatus.SUBMITTED)).thenReturn(Optional.of(entity));

        AdminOrderResponseDto responseBuilder = AdminOrderResponseDto.builder()
                .username("user")
                .orderNumber(1)
                .pizzaType(PizzaType.MARGHERITA)
                .build();
        when(orderMapper.convertEntityToAdminOrderResponse(entity)).thenReturn(responseBuilder);

        AdminOrderResponseDto nextOrder = orderService.getNextOrder();
        assertEquals("user", nextOrder.getUsername());
        assertEquals(1, nextOrder.getOrderNumber());
        assertEquals(PizzaType.MARGHERITA, nextOrder.getPizzaType());
    }

    @Test
    @DisplayName("When an order is already in PROCESSING state then return exception")
    public void shouldGetNextOrderOnlyIfNoOrderAlreadyInProcessingStatus(){
        OrderEntity entity = OrderEntity.builder()
                .username("user")
                .orderNumber(1)
                .pizzaType(PizzaType.MARGHERITA)
                .build();
        when(orderRepository.findByState(OrderStatus.PROCESSING)).thenReturn(Optional.of(entity));

        assertThrows(AnotherOrderInProcesssException.class, () -> orderService.getNextOrder());

    }

}