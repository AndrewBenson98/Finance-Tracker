package com.benson.transaction_service.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Transaction model
 * Tests entity properties and state management
 */
class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
    }

    @Test
    void testTransactionCreation() {
        assertNotNull(transaction);
        assertNull(transaction.getId());
    }

    @Test
    void testTransactionId() {
        Long id = 1L;
        transaction.setId(id);

        assertEquals(id, transaction.getId());
    }

    @Test
    void testTransactionAmount() {
        BigDecimal amount = BigDecimal.valueOf(100.50);
        transaction.setAmount(amount);

        assertEquals(amount, transaction.getAmount());
    }

    @Test
    void testTransactionDescription() {
        String description = "Coffee";
        transaction.setDescription(description);

        assertEquals(description, transaction.getDescription());
    }

    @Test
    void testTransactionCreationDate() {
        LocalDate date = LocalDate.now();
        transaction.setCreationDate(date);

        assertEquals(date, transaction.getCreationDate());
    }

    @Test
    void testTransactionUserId() {
        Long userId = 42L;
        transaction.setUserId(userId);

        assertEquals(userId, transaction.getUserId());
    }

    @Test
    void testTransactionCategoryId() {
        Long categoryId = 5L;
        transaction.setCategoryId(categoryId);

        assertEquals(categoryId, transaction.getCategoryId());
    }

    @Test
    void testTransactionType() {
        TransactionType type = TransactionType.EXPENSE;
        transaction.setTransactionType(type);

        assertEquals(type, transaction.getTransactionType());
    }

    @Test
    void testTransactionTypeIncome() {
        TransactionType type = TransactionType.INCOME;
        transaction.setTransactionType(type);

        assertEquals(TransactionType.INCOME, transaction.getTransactionType());
    }

    @Test
    void testTransactionAllFields() {
        Long id = 1L;
        BigDecimal amount = BigDecimal.valueOf(250.75);
        String description = "Grocery Shopping";
        LocalDate date = LocalDate.now();
        Long userId = 3L;
        Long categoryId = 2L;
        TransactionType type = TransactionType.EXPENSE;

        transaction.setId(id);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCreationDate(date);
        transaction.setUserId(userId);
        transaction.setCategoryId(categoryId);
        transaction.setTransactionType(type);

        assertEquals(id, transaction.getId());
        assertEquals(amount, transaction.getAmount());
        assertEquals(description, transaction.getDescription());
        assertEquals(date, transaction.getCreationDate());
        assertEquals(userId, transaction.getUserId());
        assertEquals(categoryId, transaction.getCategoryId());
        assertEquals(type, transaction.getTransactionType());
    }

    @Test
    void testTransactionNullDescription() {
        transaction.setDescription(null);

        assertNull(transaction.getDescription());
    }

    @Test
    void testTransactionNullCategoryId() {
        transaction.setCategoryId(null);

        assertNull(transaction.getCategoryId());
    }

    @Test
    void testTransactionLargeAmount() {
        BigDecimal largeAmount = BigDecimal.valueOf(999999.99);
        transaction.setAmount(largeAmount);

        assertEquals(largeAmount, transaction.getAmount());
    }

    @Test
    void testTransactionZeroAmount() {
        BigDecimal zeroAmount = BigDecimal.ZERO;
        transaction.setAmount(zeroAmount);

        assertEquals(zeroAmount, transaction.getAmount());
    }

    @Test
    void testTransactionNegativeAmount() {
        BigDecimal negativeAmount = BigDecimal.valueOf(-100.00);
        transaction.setAmount(negativeAmount);

        assertEquals(negativeAmount, transaction.getAmount());
    }
}

/**
 * Unit tests for TransactionType enum
 * Tests enum values and behavior
 */
class TransactionTypeTest {

    @Test
    void testTransactionTypeExpense() {
        assertEquals("EXPENSE", TransactionType.EXPENSE.name());
    }

    @Test
    void testTransactionTypeIncome() {
        assertEquals("INCOME", TransactionType.INCOME.name());
    }

    @Test
    void testTransactionTypeValues() {
        TransactionType[] types = TransactionType.values();

        assertEquals(2, types.length);
        assertTrue(containsType(types, TransactionType.INCOME));
        assertTrue(containsType(types, TransactionType.EXPENSE));
    }

    @Test
    void testTransactionTypeValueOf() {
        TransactionType type1 = TransactionType.valueOf("INCOME");
        TransactionType type2 = TransactionType.valueOf("EXPENSE");

        assertEquals(TransactionType.INCOME, type1);
        assertEquals(TransactionType.EXPENSE, type2);
    }

    @Test
    void testTransactionTypeValueOf_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> TransactionType.valueOf("INVALID"));
    }

    private boolean containsType(TransactionType[] types, TransactionType type) {
        for (TransactionType t : types) {
            if (t == type) return true;
        }
        return false;
    }
}

