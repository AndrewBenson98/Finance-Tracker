package com.benson.transaction_service.models.dto;

import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.request.UpdateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Transaction DTOs
 * Tests data transfer object creation and accessors
 */
class CreateTransactionDTOTest {

    @Test
    void testCreateTransactionDTOCreation() {
        BigDecimal amount = BigDecimal.valueOf(100.00);
        String description = "Coffee";
        LocalDate creationDate = LocalDate.now();
        Long categoryId = 1L;
        Long userId = 1L;
        String transactionType = "EXPENSE";

        CreateTransactionDTO dto = new CreateTransactionDTO(
                amount,
                description,
                creationDate,
                categoryId,
                userId,
                transactionType
        );

        assertNotNull(dto);
        assertEquals(amount, dto.amount());
        assertEquals(description, dto.description());
        assertEquals(creationDate, dto.creationDate());
        assertEquals(categoryId, dto.categoryId());
        assertEquals(userId, dto.userId());
        assertEquals(transactionType, dto.transactionType());
    }

    @Test
    void testCreateTransactionDTOWithNullFields() {
        CreateTransactionDTO dto = new CreateTransactionDTO(
                BigDecimal.valueOf(100.00),
                null,
                LocalDate.now(),
                null,
                1L,
                "EXPENSE"
        );

        assertNotNull(dto);
        assertNull(dto.description());
        assertNull(dto.categoryId());
    }

    @Test
    void testCreateTransactionDTOIncome() {
        CreateTransactionDTO dto = new CreateTransactionDTO(
                BigDecimal.valueOf(5000.00),
                "Salary",
                LocalDate.now(),
                null,
                1L,
                "INCOME"
        );

        assertEquals("INCOME", dto.transactionType());
    }
}

/**
 * Unit tests for UpdateTransactionDTO
 */
class UpdateTransactionDTOTest {

    @Test
    void testUpdateTransactionDTOCreation() {
        BigDecimal amount = BigDecimal.valueOf(150.00);
        String description = "Updated Coffee";
        LocalDate creationDate = LocalDate.now();
        Long categoryId = 2L;
        Long userId = 1L;
        String transactionType = "EXPENSE";

        UpdateTransactionDTO dto = new UpdateTransactionDTO(
                amount,
                description,
                creationDate,
                categoryId,
                userId,
                transactionType
        );

        assertNotNull(dto);
        assertEquals(amount, dto.amount());
        assertEquals(description, dto.description());
        assertEquals(creationDate, dto.creationDate());
        assertEquals(categoryId, dto.categoryId());
        assertEquals(userId, dto.userId());
        assertEquals(transactionType, dto.transactionType());
    }

    @Test
    void testUpdateTransactionDTOAllFields() {
        UpdateTransactionDTO dto = new UpdateTransactionDTO(
                BigDecimal.valueOf(500.50),
                "Restaurant",
                LocalDate.of(2025, 4, 13),
                3L,
                5L,
                "EXPENSE"
        );

        assertEquals(BigDecimal.valueOf(500.50), dto.amount());
        assertEquals("Restaurant", dto.description());
        assertEquals(LocalDate.of(2025, 4, 13), dto.creationDate());
        assertEquals(3L, dto.categoryId());
        assertEquals(5L, dto.userId());
    }

    @Test
    void testUpdateTransactionDTONullFields() {
        UpdateTransactionDTO dto = new UpdateTransactionDTO(
                BigDecimal.valueOf(100.00),
                null,
                null,
                null,
                1L,
                "INCOME"
        );

        assertNull(dto.description());
        assertNull(dto.creationDate());
        assertNull(dto.categoryId());
        assertNotNull(dto.userId());
    }
}

/**
 * Unit tests for TransactionDTO (Response)
 */
class TransactionDTOTest {

    @Test
    void testTransactionDTOCreation() {
        Long id = 1L;
        BigDecimal amount = BigDecimal.valueOf(100.00);
        String description = "Coffee";
        LocalDate creationDate = LocalDate.now();
        Long categoryId = 1L;
        Long userId = 1L;
        String transactionType = "EXPENSE";

        TransactionDTO dto = new TransactionDTO(
                id,
                amount,
                description,
                creationDate,
                categoryId,
                userId,
                transactionType
        );

        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(amount, dto.amount());
        assertEquals(description, dto.description());
        assertEquals(creationDate, dto.creationDate());
        assertEquals(categoryId, dto.categoryId());
        assertEquals(userId, dto.userId());
        assertEquals(transactionType, dto.transactionType());
    }

    @Test
    void testTransactionDTOWithNullCategoryId() {
        TransactionDTO dto = new TransactionDTO(
                1L,
                BigDecimal.valueOf(5000.00),
                "Salary",
                LocalDate.now(),
                null,
                1L,
                "INCOME"
        );

        assertNull(dto.categoryId());
        assertEquals("INCOME", dto.transactionType());
    }

    @Test
    void testTransactionDTOWithNullDescription() {
        TransactionDTO dto = new TransactionDTO(
                2L,
                BigDecimal.valueOf(50.00),
                null,
                LocalDate.now(),
                1L,
                1L,
                "EXPENSE"
        );

        assertNull(dto.description());
        assertEquals(2L, dto.id());
    }

    @Test
    void testTransactionDTOLargeAmount() {
        TransactionDTO dto = new TransactionDTO(
                5L,
                BigDecimal.valueOf(999999.99),
                "Large Transaction",
                LocalDate.now(),
                1L,
                1L,
                "INCOME"
        );

        assertEquals(BigDecimal.valueOf(999999.99), dto.amount());
    }

    @Test
    void testTransactionDTOZeroAmount() {
        TransactionDTO dto = new TransactionDTO(
                6L,
                BigDecimal.ZERO,
                "Zero Amount",
                LocalDate.now(),
                1L,
                1L,
                "EXPENSE"
        );

        assertEquals(BigDecimal.ZERO, dto.amount());
    }

    @Test
    void testTransactionDTOEquality() {
        TransactionDTO dto1 = new TransactionDTO(
                1L,
                BigDecimal.valueOf(100.00),
                "Test",
                LocalDate.now(),
                1L,
                1L,
                "EXPENSE"
        );

        TransactionDTO dto2 = new TransactionDTO(
                1L,
                BigDecimal.valueOf(100.00),
                "Test",
                LocalDate.now(),
                1L,
                1L,
                "EXPENSE"
        );

        // Record equals should work based on all fields
        assertEquals(dto1, dto2);
    }
}

