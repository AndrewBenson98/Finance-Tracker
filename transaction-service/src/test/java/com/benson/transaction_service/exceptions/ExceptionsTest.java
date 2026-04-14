package com.benson.transaction_service.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for custom exceptions
 * Tests exception initialization and message handling
 */
class TransactionNotFoundExceptionTest {

    @Test
    void testTransactionNotFoundExceptionConstructor() {
        String message = "Transaction with id 1 not found";
        TransactionNotFoundException exception = new TransactionNotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testTransactionNotFoundExceptionWithEmptyMessage() {
        TransactionNotFoundException exception = new TransactionNotFoundException("");

        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void testTransactionNotFoundExceptionWithNullMessage() {
        TransactionNotFoundException exception = new TransactionNotFoundException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testTransactionNotFoundExceptionThrowable() {
        String message = "Transaction not found";
        TransactionNotFoundException exception = new TransactionNotFoundException(message);

        assertInstanceOf(RuntimeException.class, exception);
        assertThrows(TransactionNotFoundException.class, () -> {
            throw exception;
        });
    }
}

/**
 * Unit tests for UserNotFoundException
 * Tests exception initialization and message handling
 */
class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundExceptionConstructor() {
        String message = "User with id 1 not found";
        UserNotFoundException exception = new UserNotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testUserNotFoundExceptionWithEmptyMessage() {
        UserNotFoundException exception = new UserNotFoundException("");

        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void testUserNotFoundExceptionWithNullMessage() {
        UserNotFoundException exception = new UserNotFoundException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testUserNotFoundExceptionThrowable() {
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);

        assertInstanceOf(RuntimeException.class, exception);
        assertThrows(UserNotFoundException.class, () -> {
            throw exception;
        });
    }
}

