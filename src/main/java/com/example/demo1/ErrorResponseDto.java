package com.example.demo1;

import java.time.LocalDateTime;

// этот класс будет использоваться исключительно для переноса каких то данных 

public record ErrorResponseDto(
    String message,
    String detailMessage,
    LocalDateTime errorTime
) {
    
}
