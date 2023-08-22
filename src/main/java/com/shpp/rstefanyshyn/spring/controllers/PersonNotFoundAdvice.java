package com.shpp.rstefanyshyn.spring.controllers;


import com.shpp.rstefanyshyn.spring.exeption.ErrorResponse;
import com.shpp.rstefanyshyn.spring.exeption.InvalidPersonException;
import com.shpp.rstefanyshyn.spring.exeption.InvalidStatusException;
import com.shpp.rstefanyshyn.spring.exeption.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
class PersonNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String,String> employeeNotFoundHandler(PersonNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(InvalidPersonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ErrorResponse> handleInvalidPersonException(InvalidPersonException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ErrorResponse> handleInvalidStatusException(InvalidStatusException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }





}