// обработка ошибок и валидация 

package com.example.demo1;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;


// специальная аннотация для обработки ошибок 
@ControllerAdvice 
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // @ExceptionHandler(Exception.class) а именно (Exception.class) означает что мы обрабатываем все исключения 
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<ErrorResponseDto> handleGenericException(
        Exception e
    ) {
        var errorDto =  new ErrorResponseDto(
            "Internal server error",
            e.getMessage(),
            LocalDateTime.now()
        );

        log.error("Handle exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class) 
    public ResponseEntity<ErrorResponseDto> EntityNotFoundException(
        EntityNotFoundException e
    ) {
        log.error("Handle Entity exception", e);

        var errorDto =  new ErrorResponseDto(
            "Entity not found",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorDto);
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class}) 
    public ResponseEntity<ErrorResponseDto> HandleBadRequestException(
        Exception e
    ) {
        log.error("Handle IllegalArgumentException", e);

        var errorDto =  new ErrorResponseDto(
            "Illegal Argument exception",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorDto);
    }
}
