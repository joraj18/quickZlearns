package com.jo.quickZlearn.ExceptionHandler;

import com.jo.quickZlearn.roadmap.exceptions.RoadmapNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoadmapNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoadmapNotFound(RoadmapNotFoundException e){
        ErrorResponse error= new ErrorResponse(
                LocalDateTime.now(),
                "Roadmap Not Found",
                e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception e){
        ErrorResponse error= new ErrorResponse(
                LocalDateTime.now(),
                "Internal Server Error",
                e.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
