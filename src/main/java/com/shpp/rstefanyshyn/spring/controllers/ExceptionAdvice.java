package com.shpp.rstefanyshyn.spring.controllers;


import com.shpp.rstefanyshyn.spring.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

@ControllerAdvice
class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> employeeNotFoundHandler(PersonNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidPersonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ErrorResponse> handleInvalidPersonException(InvalidPersonException ex) {
        ErrorResponse error = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ErrorResponse> handleInvalidStatusException(InvalidStatusException ex) {
        ErrorResponse error = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        ErrorResponse error = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public ResponseEntity<ErrorResponse> handleInvalidException(InvalidDataException ex) {
        ErrorResponse error = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorResponse getErrorResponse(String ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex);
        error.setTimestamp(System.currentTimeMillis());
        error.setLocalDateTime(
                Instant.ofEpochMilli(System.currentTimeMillis())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
        return error;
    }


}