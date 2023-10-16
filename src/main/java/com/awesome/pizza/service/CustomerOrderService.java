package com.awesome.pizza.service;

import com.awesome.pizza.OrderRepository;
import com.awesome.pizza.dto.customer.CustomerOrderResponseDto;
import com.awesome.pizza.dto.customer.OrderInsertRequestDto;
import com.awesome.pizza.dto.customer.OrderInsertResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerOrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public OrderInsertResponseDto submitOrder(OrderInsertRequestDto createOrderDto) {
        OrderEntity entity = orderMapper.convertToEntity(createOrderDto);
        entity.setStatus(OrderStatus.SUBMITTED);
        OrderEntity savedEntity = orderRepository.save(entity);
        return orderMapper.convertFromEntityToOrderInsert(savedEntity);
    }

    public CustomerOrderResponseDto retrieveOrder(int orderNumber) {
        OrderEntity orderEntity = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("Order with number " + orderNumber + " not found"));
        return orderMapper.convertFromEntityToOrderResponse(orderEntity);
    }
}
