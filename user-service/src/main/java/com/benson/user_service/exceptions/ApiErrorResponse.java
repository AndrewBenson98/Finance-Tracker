package com.benson.user_service.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> validationErrors;


    /**
     * Create an ApiErrorResponse from basic error information
     */
    public static ApiErrorResponse of(HttpStatus status,  String message, String path) {
        return ApiErrorResponse.builder()
                .status(status.value())
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }


    /**
     * Create an ApiErrorResponse with validation errors
     */
    public static ApiErrorResponse of(int status, String message, String path, Map<String, String> validationErrors) {
        return ApiErrorResponse.builder()
                .status(status)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .validationErrors(validationErrors)
                .build();
    }



}
