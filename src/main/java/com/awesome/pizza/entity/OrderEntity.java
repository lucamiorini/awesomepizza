package com.awesome.pizza.entity;

import com.awesome.pizza.dto.PizzaType;
import com.awesome.pizza.enumeration.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNumber;
    private String username;
    private PizzaType pizzaType;
    private String shippingAddress;
    private OrderStatus status = OrderStatus.SUBMITTED;
    private ZonedDateTime timestamp;
}
