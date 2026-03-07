package com.benson.user_service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }



    /**
     * Handle generic exceptions (500 Internal Server Error)
     * Catches all other unexpected exceptions that haven't been handled specifically.
     * These should be logged for debugging purposes.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {

        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please contact support.",
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
