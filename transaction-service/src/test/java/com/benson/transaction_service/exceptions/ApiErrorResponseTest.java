package com.benson.transaction_service.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ApiErrorResponse
 * Tests error response creation and builder functionality
 */
class ApiErrorResponseTest {

    @Test
    void testApiErrorResponseBuilder_AllFields() {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .status(404)
                .error("NOT_FOUND")
                .message("Resource not found")
                .path("/api/v1/transactions/999")
                .timestamp(LocalDateTime.now())
                .build();

        assertNotNull(response);
        assertEquals(404, response.getStatus());
        assertEquals("NOT_FOUND", response.getError());
        assertEquals("Resource not found", response.getMessage());
        assertEquals("/api/v1/transactions/999", response.getPath());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void testApiErrorResponseOf_BasicInfo() {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Transaction not found";
        String path = "/api/v1/transactions/1";

        ApiErrorResponse response = ApiErrorResponse.of(status, message, path);

        assertNotNull(response);
        assertEquals(404, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void testApiErrorResponseOf_BasicInfo_InternalServerError() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An unexpected error occurred";
        String path = "/api/v1/transactions";

        ApiErrorResponse response = ApiErrorResponse.of(status, message, path);

        assertNotNull(response);
        assertEquals(500, response.getStatus());
        assertEquals(message, response.getMessage());
    }

    @Test
    void testApiErrorResponseOf_WithValidationErrors() {
        int status = 400;
        String message = "Validation failed";
        String path = "/api/v1/transactions";
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("amount", "Amount must be positive");
        validationErrors.put("userId", "User ID is required");

        ApiErrorResponse response = ApiErrorResponse.of(status, message, path, validationErrors);

        assertNotNull(response);
        assertEquals(status, response.getStatus());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertNotNull(response.getValidationErrors());
        assertEquals(2, response.getValidationErrors().size());
        assertEquals("Amount must be positive", response.getValidationErrors().get("amount"));
        assertEquals("User ID is required", response.getValidationErrors().get("userId"));
    }

    @Test
    void testApiErrorResponseOf_EmptyValidationErrors() {
        int status = 400;
        String message = "Bad request";
        String path = "/api/v1/transactions";
        Map<String, String> validationErrors = new HashMap<>();

        ApiErrorResponse response = ApiErrorResponse.of(status, message, path, validationErrors);

        assertNotNull(response);
        assertNotNull(response.getValidationErrors());
        assertTrue(response.getValidationErrors().isEmpty());
    }

    @Test
    void testApiErrorResponseOf_NullValidationErrors() {
        int status = 500;
        String message = "Internal server error";
        String path = "/api/v1/transactions";

        ApiErrorResponse response = ApiErrorResponse.of(status, message, path, null);

        assertNotNull(response);
        assertNull(response.getValidationErrors());
    }

    @Test
    void testApiErrorResponse_SettersAndGetters() {
        ApiErrorResponse response = new ApiErrorResponse(404, "NOT_FOUND", "Not found", "/api/v1", LocalDateTime.now(), null);
//        response.setStatus(404);
//        response.setError("NOT_FOUND");
//        response.setMessage("Not found");
//        response.setPath("/api/v1");

        assertEquals(404, response.getStatus());
        assertEquals("NOT_FOUND", response.getError());
        assertEquals("Not found", response.getMessage());
        assertEquals("/api/v1", response.getPath());
    }

    @Test
    void testApiErrorResponse_Timestamp_AutoSet() {
        LocalDateTime beforeCreation = LocalDateTime.now();
        ApiErrorResponse response = ApiErrorResponse.of(
                HttpStatus.NOT_FOUND,
                "Not found",
                "/api/v1"
        );
        LocalDateTime afterCreation = LocalDateTime.now();

        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isAfter(beforeCreation) || response.getTimestamp().isEqual(beforeCreation));
        assertTrue(response.getTimestamp().isBefore(afterCreation) || response.getTimestamp().isEqual(afterCreation));
    }

    @Test
    void testApiErrorResponse_MultipleValidationErrors() {
        Map<String, String> errors = new HashMap<>();
        errors.put("field1", "error1");
        errors.put("field2", "error2");
        errors.put("field3", "error3");

        ApiErrorResponse response = ApiErrorResponse.of(400, "Validation failed", "/api/v1", errors);

        assertEquals(3, response.getValidationErrors().size());
        assertTrue(response.getValidationErrors().containsKey("field1"));
        assertTrue(response.getValidationErrors().containsKey("field2"));
        assertTrue(response.getValidationErrors().containsKey("field3"));
    }

    @Test
    void testApiErrorResponse_DifferentHttpStatuses() {
        ApiErrorResponse response404 = ApiErrorResponse.of(
                HttpStatus.NOT_FOUND,
                "Not found",
                "/api/v1"
        );
        ApiErrorResponse response400 = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Bad request",
                "/api/v1"
        );
        ApiErrorResponse response500 = ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal error",
                "/api/v1"
        );

        assertEquals(404, response404.getStatus());
        assertEquals(400, response400.getStatus());
        assertEquals(500, response500.getStatus());
    }
}

