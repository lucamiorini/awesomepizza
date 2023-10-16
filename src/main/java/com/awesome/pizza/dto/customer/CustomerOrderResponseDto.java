package com.awesome.pizza.dto.customer;

import com.awesome.pizza.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerOrderResponseDto {

    private int orderNumber;
    private String pizzaType;
    private String shippingAddress;
    private OrderStatus status;
}
