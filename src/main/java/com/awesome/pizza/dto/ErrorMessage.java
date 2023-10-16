package com.awesome.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

    private String message;
    private Integer statusCode;
    private ZonedDateTime timestamp;

}
