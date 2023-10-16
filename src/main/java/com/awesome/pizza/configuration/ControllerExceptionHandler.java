package com.awesome.pizza.configuration;

import com.awesome.pizza.AnotherOrderInProcesssException;
import com.awesome.pizza.dto.ErrorMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage messageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return new ErrorMessage(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage messageNotReadableException(EntityNotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                ZonedDateTime.now());
    }

    @ExceptionHandler(value = {AnotherOrderInProcesssException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage genericException(AnotherOrderInProcesssException ex, WebRequest request) {
        return new ErrorMessage(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ZonedDateTime.now());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage genericException(Exception ex, WebRequest request) {
        return new ErrorMessage(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ZonedDateTime.now());
    }
}
