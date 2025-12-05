package com.hamadiddi.notesapp.utils;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

    private String status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
