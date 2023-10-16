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
public class OrderInsertResponseDto {

    private int orderNumber;
    private OrderStatus status;
}
