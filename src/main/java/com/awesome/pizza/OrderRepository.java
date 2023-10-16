package com.awesome.pizza;

import com.awesome.pizza.entity.OrderEntity;
import com.awesome.pizza.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer> {

    @Query("select o from #{#entityName} o where o.status = :status order by o.timestamp limit 1") //TODO find first
    Optional<OrderEntity> findByState(@Param("status") OrderStatus status);
}
