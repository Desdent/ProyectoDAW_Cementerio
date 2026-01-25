package com.imo.cemetery.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// En esta clase va el formato JSON de los errores
@Data
@Builder
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
