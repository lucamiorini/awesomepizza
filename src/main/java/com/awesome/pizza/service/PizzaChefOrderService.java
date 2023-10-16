package com.awesome.pizza.service;

import com.awesome.pizza.AnotherOrderInProcesssException;
import com.awesome.pizza.OrderRepository;
import com.awesome.pizza.dto.admin.AdminOrderResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import com.awesome.pizza.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PizzaChefOrderService {

    private OrderRepository repository;
    private OrderMapper orderMapper;

    public List<AdminOrderResponseDto> retrieveOrderList() {
        List<OrderEntity> orderEntityList = repository.findAll();
        return orderMapper.convertEntityListToAdminOrderResponseList(orderEntityList);
    }

    public AdminOrderResponseDto getNextOrder() {
        Optional<OrderEntity> orederInProcess = repository.findByState(OrderStatus.PROCESSING);
        if(orederInProcess.isPresent()){
            throw new AnotherOrderInProcesssException("Another order already in process");
        }

        Optional<OrderEntity> nextOrder = repository.findByState(OrderStatus.SUBMITTED);
        if(nextOrder.isEmpty()){
            return new AdminOrderResponseDto();
        }

       return orderMapper.convertEntityToAdminOrderResponse(nextOrder.get());
    }

    public void updateOrderStatus(Integer orderNumber, OrderStatus status) {
        OrderEntity orderEntity = repository.findById(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException("order Not found"));
        orderEntity.setStatus(status);
        repository.save(orderEntity);
    }
}
