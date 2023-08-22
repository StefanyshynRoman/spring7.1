package com.shpp.rstefanyshyn.spring.exeption;

public class InvalidPersonException extends RuntimeException {
    public InvalidPersonException(String message) {
        super(message);
    }
}