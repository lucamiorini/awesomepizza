package com.awesome.pizza.service;

import com.awesome.pizza.AnotherOrderInProcesssException;
import com.awesome.pizza.OrderRepository;
import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.dto.admin.AdminOrderResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PizzaChefOrderServiceIT {

    @Autowired
    private PizzaChefOrderService service;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DirtiesContext
    @DisplayName("When there are many orders than retrieve the old one in SUBMITTED status")
    public void shouldReturnTheOldestOrder(){

        OrderEntity order1 = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.DIAVOLA)
                .status(OrderStatus.SUBMITTED)
                .timestamp(ZonedDateTime.now())
                .build();

        orderRepository.save(order1);

        OrderEntity order2 = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.MARGHERITA)
                .status(OrderStatus.SUBMITTED)
                .timestamp(ZonedDateTime.now())
                .build();
        orderRepository.save(order2);

        AdminOrderResponseDto nextOrder = service.getNextOrder();

        assertEquals(1, nextOrder.getOrderNumber());
        assertEquals(PizzaType.DIAVOLA, nextOrder.getPizzaType());
    }

    @Test
    @DirtiesContext
    @DisplayName("When there one order is in process cannot take another one")
    public void shouldReturnExceptionWhenAnotherOrderInProcess(){

        OrderEntity order1 = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.DIAVOLA)
                .status(OrderStatus.PROCESSING)
                .timestamp(ZonedDateTime.now())
                .build();

        orderRepository.save(order1);

        OrderEntity order2 = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.MARGHERITA)
                .status(OrderStatus.SUBMITTED)
                .timestamp(ZonedDateTime.now())
                .build();
        orderRepository.save(order2);

       assertThrows(AnotherOrderInProcesssException.class, () -> service.getNextOrder());
    }

    @Test
    @DirtiesContext
    @DisplayName("When order does not exist than update status fail")
    public void shouldFailOnUpdateStatus(){
        OrderEntity order = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.DIAVOLA)
                .status(OrderStatus.SUBMITTED)
                .timestamp(ZonedDateTime.now())
                .build();

        orderRepository.save(order);

        assertThrows(EntityNotFoundException.class, () -> service.updateOrderStatus(2, OrderStatus.PROCESSING));
    }

    @Test
    @DirtiesContext
    @DisplayName("When order does exist than update status ok")
    public void shouldUpdateOrderStatus(){
        OrderEntity order = OrderEntity.builder()
                .username("user")
                .pizzaType(PizzaType.DIAVOLA)
                .status(OrderStatus.SUBMITTED)
                .timestamp(ZonedDateTime.now())
                .build();

        orderRepository.save(order);

        service.updateOrderStatus(1, OrderStatus.PROCESSING);

        OrderEntity updateOrder = orderRepository.findById(1).get();
        assertEquals(OrderStatus.PROCESSING, updateOrder.getStatus());
    }

}