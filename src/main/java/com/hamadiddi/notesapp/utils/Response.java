package com.hamadiddi.notesapp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response <T> {

    private String message;
    private String status;
    private T data;
    private Integer code;

}
