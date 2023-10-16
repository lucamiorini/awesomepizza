package com.awesome.pizza.controller;

import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.dto.admin.AdminOrderResponseDto;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.service.PizzaChefOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/admin/order")
@AllArgsConstructor
public class PizzaChefOrderController {

    private PizzaChefOrderService service;

    /**
     * retrieve all order not already delivered
     * @return
     */
    @GetMapping("/all")
    public List<AdminOrderResponseDto> retrieveOrderList() {
        return service.retrieveOrderList();
    }

    /**
     * retrieve the next order to deliver
     * @return
     */
    @GetMapping("/")
    public AdminOrderResponseDto getNextOrder() {
       return service.getNextOrder();
    }

    /**
     * Update the status of the specified order
     * @param orderNumber
     * @param status
     */
    @PutMapping("/{orderNumber}/{status}")
    public void updateOrderStatus(@PathVariable("orderNumber") Integer orderNumber,
            @PathVariable("status") OrderStatus status) {
        service.updateOrderStatus(orderNumber, status);
    }
}
