package com.awesome.pizza.configuration;

import com.awesome.pizza.OrderRepository;
import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.dto.customer.OrderInsertRequestDto;
import com.awesome.pizza.dto.customer.OrderInsertResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.service.CustomerOrderService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseContractTest {
    @LocalServerPort
    private int port;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeAll
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + this.port;
        orderRepository.save(OrderEntity.builder()
                .orderNumber(1)
                .username("testUser")
                .status(OrderStatus.SUBMITTED)
                .shippingAddress("testAddress")
                .pizzaType(PizzaType.MARGHERITA)
                .build());

    }

    @BeforeEach
    public void before() {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(1);
        if(orderEntityOptional.isPresent()){
            OrderEntity orderEntity = orderEntityOptional.get();
            orderEntity.setStatus(OrderStatus.SUBMITTED);
            orderRepository.save(orderEntity);
        }
    }
}
