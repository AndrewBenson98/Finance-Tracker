package com.benson.transaction_service.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GlobalExceptionHandler
 * Tests exception handling and HTTP response mapping
 */
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/transactions/999");
    }

    @Test
    void testHandleUserNotFoundException() {
        String errorMessage = "User with id 1 not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleUserNotFoundException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals("uri=/api/v1/transactions/999", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleTransactionNotFoundException() {
        String errorMessage = "Transaction with id 999 not found";
        TransactionNotFoundException exception = new TransactionNotFoundException(errorMessage);

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleTransactionNotFoundException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals("uri=/api/v1/transactions/999", response.getBody().getPath());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleTransactionNotFoundException_EmptyMessage() {
        TransactionNotFoundException exception = new TransactionNotFoundException("");

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleTransactionNotFoundException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("", body.getMessage());
    }

    @Test
    void testHandleGlobalException() {
        Exception exception = new Exception("Unexpected error occurred");

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getStatus());
        assertEquals("An unexpected error occurred. Please contact support.", body.getMessage());
        assertEquals("uri=/api/v1/transactions/999", body.getPath());
        assertNotNull(body.getTimestamp());
    }

    @Test
    void testHandleGlobalException_NullPointerException() {
        NullPointerException exception = new NullPointerException("Null value encountered");

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please contact support.", response.getBody().getMessage());
    }

    @Test
    void testHandleGlobalException_IllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleGlobalException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    void testApiErrorResponse_Status() {
        UserNotFoundException exception = new UserNotFoundException("Test error");

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleUserNotFoundException(exception, webRequest);

        ApiErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.getStatus());
    }

    @Test
    void testApiErrorResponse_Timestamp_NotNull() {
        TransactionNotFoundException exception = new TransactionNotFoundException("Transaction not found");

        ResponseEntity<ApiErrorResponse> response = globalExceptionHandler
                .handleTransactionNotFoundException(exception, webRequest);

        assertNotNull(response.getBody().getTimestamp());
    }
}

