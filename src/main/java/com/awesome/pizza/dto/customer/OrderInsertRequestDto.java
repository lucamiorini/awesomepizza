package com.awesome.pizza.dto.customer;

import com.awesome.pizza.dto.PizzaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInsertRequestDto {
    private String username;
    private PizzaType pizzaType;
    private String shippingAddress;
}
