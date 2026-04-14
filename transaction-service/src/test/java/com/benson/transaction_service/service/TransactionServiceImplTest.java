package com.benson.transaction_service.service;

import com.benson.transaction_service.exceptions.TransactionNotFoundException;
import com.benson.transaction_service.models.Transaction;
import com.benson.transaction_service.models.TransactionType;
import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.request.UpdateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;
import com.benson.transaction_service.repository.TransactionRepository;
import com.benson.transaction_service.utils.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TransactionServiceImpl
 * Tests business logic for all transaction operations
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private CreateTransactionDTO createTransactionDTO;
    private UpdateTransactionDTO updateTransactionDTO;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setDescription("Coffee");
        transaction.setCreationDate(LocalDate.now());
        transaction.setCategoryId(1L);
        transaction.setUserId(1L);
        transaction.setTransactionType(TransactionType.EXPENSE);

        createTransactionDTO = new CreateTransactionDTO(
                BigDecimal.valueOf(100.00),
                "Coffee",
                LocalDate.now(),
                1L,
                1L,
                "EXPENSE"
        );

        updateTransactionDTO = new UpdateTransactionDTO(
                BigDecimal.valueOf(150.00),
                "Updated Coffee",
                LocalDate.now(),
                2L,
                1L,
                "EXPENSE"
        );

        transactionDTO = new TransactionDTO(
                1L,
                BigDecimal.valueOf(100.00),
                "Coffee",
                LocalDate.now(),
                1L,
                1L,
                "EXPENSE"
        );
    }

    @Test
    void testCreateTransaction_Success() {
        when(transactionMapper.toEntity(createTransactionDTO)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDTO);

        TransactionDTO result = transactionService.createTransaction(createTransactionDTO);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(BigDecimal.valueOf(100.00), result.amount());
        assertEquals("Coffee", result.description());

        verify(transactionMapper, times(1)).toEntity(createTransactionDTO);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transactionMapper, times(1)).toDto(transaction);
    }

    @Test
    void testCreateTransaction_WithDifferentAmount() {
        Transaction largeTransaction = new Transaction();
        largeTransaction.setId(2L);
        largeTransaction.setAmount(BigDecimal.valueOf(5000.00));
        largeTransaction.setDescription("Salary");
        largeTransaction.setCreationDate(LocalDate.now());
        largeTransaction.setUserId(1L);
        largeTransaction.setTransactionType(TransactionType.INCOME);

        TransactionDTO largeDTO = new TransactionDTO(
                2L,
                BigDecimal.valueOf(5000.00),
                "Salary",
                LocalDate.now(),
                null,
                1L,
                "INCOME"
        );

        when(transactionMapper.toEntity(any(CreateTransactionDTO.class))).thenReturn(largeTransaction);
        when(transactionRepository.save(largeTransaction)).thenReturn(largeTransaction);
        when(transactionMapper.toDto(largeTransaction)).thenReturn(largeDTO);

        TransactionDTO result = transactionService.createTransaction(createTransactionDTO);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(5000.00), result.amount());
        assertEquals("Salary", result.description());
    }

    @Test
    void testGetTransactionsByUserId_Success() {
        Long userId = 1L;
        List<Transaction> transactions = List.of(transaction);

        when(transactionRepository.findByUserId(userId)).thenReturn(Optional.of(transactions));
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDTO);

        List<TransactionDTO> result = transactionService.getTransactionsByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transactionDTO, result.getFirst());

        verify(transactionRepository, times(1)).findByUserId(userId);
        verify(transactionMapper, times(1)).toDto(transaction);
    }

    @Test
    void testGetTransactionsByUserId_MultipleTransactions() {
        Long userId = 1L;
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(BigDecimal.valueOf(200.00));
        transaction2.setDescription("Lunch");
        transaction2.setUserId(userId);

        List<Transaction> transactions = Arrays.asList(transaction, transaction2);

        TransactionDTO dto2 = new TransactionDTO(
                2L,
                BigDecimal.valueOf(200.00),
                "Lunch",
                LocalDate.now(),
                null,
                userId,
                "EXPENSE"
        );

        when(transactionRepository.findByUserId(userId)).thenReturn(Optional.of(transactions));
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDTO);
        when(transactionMapper.toDto(transaction2)).thenReturn(dto2);

        List<TransactionDTO> result = transactionService.getTransactionsByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findByUserId(userId);
        verify(transactionMapper, times(2)).toDto(any(Transaction.class));
    }

    @Test
    void testGetTransactionsByUserId_NotFound() {
        Long userId = 999L;
        when(transactionRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.getTransactionsByUserId(userId));

        verify(transactionRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testUpdateTransaction_Success() {
        Long id = 1L;
        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setId(id);
        updatedTransaction.setAmount(updateTransactionDTO.amount());
        updatedTransaction.setDescription(updateTransactionDTO.description());
        updatedTransaction.setCreationDate(updateTransactionDTO.creationDate());
        updatedTransaction.setCategoryId(updateTransactionDTO.categoryId());
        updatedTransaction.setUserId(updateTransactionDTO.userId());
        updatedTransaction.setTransactionType(TransactionType.valueOf(updateTransactionDTO.transactionType()));

        TransactionDTO updatedDTO = new TransactionDTO(
                id,
                BigDecimal.valueOf(150.00),
                "Updated Coffee",
                LocalDate.now(),
                2L,
                1L,
                "EXPENSE"
        );

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updatedTransaction);
        when(transactionMapper.toDto(updatedTransaction)).thenReturn(updatedDTO);

        TransactionDTO result = transactionService.updateTransaction(updateTransactionDTO, id);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(150.00), result.amount());
        assertEquals("Updated Coffee", result.description());

        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transactionMapper, times(1)).toDto(updatedTransaction);
    }

    @Test
    void testUpdateTransaction_NotFound() {
        Long id = 999L;
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.updateTransaction(updateTransactionDTO, id));

        verify(transactionRepository, times(1)).findById(id);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_IncomeType() {
        Long id = 1L;
        UpdateTransactionDTO incomeUpdateDTO = new UpdateTransactionDTO(
                BigDecimal.valueOf(5000.00),
                "Bonus",
                LocalDate.now(),
                null,
                1L,
                "INCOME"
        );

        Transaction incomeTransaction = new Transaction();
        incomeTransaction.setId(id);
        incomeTransaction.setTransactionType(TransactionType.INCOME);
        incomeTransaction.setAmount(BigDecimal.valueOf(5000.00));

        TransactionDTO incomeDTO = new TransactionDTO(
                id,
                BigDecimal.valueOf(5000.00),
                "Bonus",
                LocalDate.now(),
                null,
                1L,
                "INCOME"
        );

        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(incomeTransaction);
        when(transactionMapper.toDto(incomeTransaction)).thenReturn(incomeDTO);

        TransactionDTO result = transactionService.updateTransaction(incomeUpdateDTO, id);

        assertNotNull(result);
        assertEquals("INCOME", result.transactionType());
    }

    @Test
    void testDeleteTransactionById_Success() {
        Long id = 1L;
        when(transactionRepository.existsById(id)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(id);

        assertDoesNotThrow(() -> transactionService.deleteTransactionById(id));

        verify(transactionRepository, times(1)).existsById(id);
        verify(transactionRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTransactionById_NotFound() {
        Long id = 999L;
        when(transactionRepository.existsById(id)).thenReturn(false);

        assertThrows(TransactionNotFoundException.class,
                () -> transactionService.deleteTransactionById(id));

        verify(transactionRepository, times(1)).existsById(id);
        verify(transactionRepository, never()).deleteById(id);
    }

    @Test
    void testDeleteTransactionById_MultipleDeletes() {
        Long id1 = 1L;
        Long id2 = 2L;

        when(transactionRepository.existsById(id1)).thenReturn(true);
        when(transactionRepository.existsById(id2)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(any());

        assertDoesNotThrow(() -> {
            transactionService.deleteTransactionById(id1);
            transactionService.deleteTransactionById(id2);
        });

        verify(transactionRepository, times(2)).existsById(any());
        verify(transactionRepository, times(2)).deleteById(any());
    }
}

