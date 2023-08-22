package com.shpp.rstefanyshyn.spring.exeption;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Could not find task " + id);
    }
}
