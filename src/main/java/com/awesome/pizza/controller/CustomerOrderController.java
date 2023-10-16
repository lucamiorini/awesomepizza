package com.awesome.pizza.controller;

import com.awesome.pizza.dto.customer.CustomerOrderResponseDto;
import com.awesome.pizza.dto.customer.OrderInsertRequestDto;
import com.awesome.pizza.dto.customer.OrderInsertResponseDto;
import com.awesome.pizza.service.CustomerOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@AllArgsConstructor
public class CustomerOrderController {

    private CustomerOrderService service;

    @PostMapping("/")
    public OrderInsertResponseDto insertOrder(@RequestBody OrderInsertRequestDto createOrderDto) {
        return service.submitOrder(createOrderDto);
    }

    @GetMapping("/{id}")
    public CustomerOrderResponseDto retrieveStatus(@PathVariable("id") int orderNumber) {
        return service.retrieveOrder(orderNumber);
    }
}
