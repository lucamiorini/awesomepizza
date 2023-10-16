package com.awesome.pizza.dto.admin;

import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminOrderResponseDto {

    private int orderNumber;
    private String username;
    private PizzaType pizzaType;
    private String shippingAddress;
    private OrderStatus status;
}
