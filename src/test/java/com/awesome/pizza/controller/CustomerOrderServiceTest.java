package com.awesome.pizza.controller;

import com.awesome.pizza.OrderRepository;
import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.dto.customer.CustomerOrderResponseDto;
import com.awesome.pizza.dto.customer.OrderInsertRequestDto;
import com.awesome.pizza.dto.customer.OrderInsertResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.mapper.OrderMapper;
import com.awesome.pizza.service.CustomerOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Spy
    private OrderMapper orderMapper;
    @InjectMocks
    private CustomerOrderService customerOrderService;

    @Test
    @DisplayName("When insert order action is required than save on db")
    public void whenSubmittingNeOrderThenSaveOnDatabase() {
        OrderEntity entity = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.MARGHERITA)
                .shippingAddress("testAddress")
                .build();
        when(orderMapper.convertToEntity(any(OrderInsertRequestDto.class))).thenReturn(entity);

        OrderInsertRequestDto insertRequest = OrderInsertRequestDto.builder()
                .username("user")
                .pizzaType(PizzaType.MARGHERITA)
                .shippingAddress("testAddress")
                .build();
        customerOrderService.submitOrder(insertRequest);

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    @DisplayName("Requesting order status for a inexistent order return not found")
    public void whenRequestingStatusForInvalidOrderNumberThenReturnNotFound() {
        when(orderRepository.findById(anyInt())).thenThrow(new EntityNotFoundException("Order not found"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> customerOrderService.retrieveOrder(1));
    }

    @Test
    @DisplayName("Requesting order status for an order then return order status")
    public void whenRequestingStatusForValidOrderNumberThenReturnStatus() {
        OrderEntity entity = OrderEntity.builder()
                .status(OrderStatus.SUBMITTED)
                .build();
        when(orderRepository.findById(2)).thenReturn(Optional.of(entity));
        CustomerOrderResponseDto response = CustomerOrderResponseDto.builder()
                .status(OrderStatus.SUBMITTED)
                .build();
        when(orderMapper.convertFromEntityToOrderResponse(any(OrderEntity.class))).thenReturn(response);

        CustomerOrderResponseDto customerOrderResponseDto = customerOrderService.retrieveOrder(2);
        Assertions.assertEquals(OrderStatus.SUBMITTED, customerOrderResponseDto.getStatus());
    }
}
