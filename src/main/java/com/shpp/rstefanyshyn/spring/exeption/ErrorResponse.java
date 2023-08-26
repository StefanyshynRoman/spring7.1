package com.shpp.rstefanyshyn.spring.exeption;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
    private LocalDateTime localDateTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {

        this.timestamp = timestamp;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    // constructors, getters, setters
}