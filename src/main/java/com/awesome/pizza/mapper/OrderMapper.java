package com.awesome.pizza.mapper;

import com.awesome.pizza.dto.admin.AdminOrderResponseDto;
import com.awesome.pizza.dto.customer.CustomerOrderResponseDto;
import com.awesome.pizza.dto.customer.OrderInsertRequestDto;
import com.awesome.pizza.dto.customer.OrderInsertResponseDto;
import com.awesome.pizza.entity.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderEntity convertToEntity(OrderInsertRequestDto dto);

    OrderInsertResponseDto convertFromEntityToOrderInsert(OrderEntity entity);

    CustomerOrderResponseDto convertFromEntityToOrderResponse(OrderEntity entity);

    AdminOrderResponseDto convertEntityToAdminOrderResponse(OrderEntity entity);

    List<AdminOrderResponseDto> convertEntityListToAdminOrderResponseList(List<OrderEntity> entity);
}
