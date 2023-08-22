package com.shpp.rstefanyshyn.spring.exeption;

public class InvalidEventException extends RuntimeException {

    public InvalidEventException(String message) {
        super(message);
    }
}