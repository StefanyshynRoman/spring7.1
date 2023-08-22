package com.shpp.rstefanyshyn.spring.exeption;

public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException(String message) {
        super(message);
    }
}


