package com.shpp.rstefanyshyn.spring.exeption;

import java.util.ResourceBundle;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id, String mesagges) {
        super(mesagges + id);

    }
}
