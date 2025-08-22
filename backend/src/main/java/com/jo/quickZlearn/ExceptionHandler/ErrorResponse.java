package com.jo.quickZlearn.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;


}
