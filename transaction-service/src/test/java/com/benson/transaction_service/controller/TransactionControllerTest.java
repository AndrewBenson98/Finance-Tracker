package com.benson.transaction_service.controller;

import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.request.UpdateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;
import com.benson.transaction_service.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.*;
//import org.springframework.boot.test.mock.MockBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for TransactionController layer
 * Tests all CRUD operations and HTTP response handling
 */
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateTransactionDTO createTransactionDTO;
    private UpdateTransactionDTO updateTransactionDTO;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
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
    void testCreateTransaction_Success() throws Exception {
        when(transactionService.createTransaction(any(CreateTransactionDTO.class)))
                .thenReturn(transactionDTO);

        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTransactionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.description").value("Coffee"));

        verify(transactionService, times(1)).createTransaction(any(CreateTransactionDTO.class));
    }

    @Test
    void testGetTransactionsByUserId_Success() throws Exception {
        Long userId = 1L;
        List<TransactionDTO> transactions = Arrays.asList(
                transactionDTO,
                new TransactionDTO(2L, BigDecimal.valueOf(200.00), "Lunch", LocalDate.now(), 1L, userId, "EXPENSE")
        );

        when(transactionService.getTransactionsByUserId(userId))
                .thenReturn(transactions);

        mockMvc.perform(get("/api/v1/transactions/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(transactionService, times(1)).getTransactionsByUserId(userId);
    }

    @Test
    void testGetTransactionsByUserId_EmptyList() throws Exception {
        Long userId = 1L;
        when(transactionService.getTransactionsByUserId(userId))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/transactions/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(transactionService, times(1)).getTransactionsByUserId(userId);
    }

    @Test
    void testUpdateTransaction_Success() throws Exception {
        Long id = 1L;
        TransactionDTO updatedDTO = new TransactionDTO(
                id,
                BigDecimal.valueOf(150.00),
                "Updated Coffee",
                LocalDate.now(),
                2L,
                1L,
                "EXPENSE"
        );

        when(transactionService.updateTransaction(any(UpdateTransactionDTO.class), eq(id)))
                .thenReturn(updatedDTO);

        mockMvc.perform(put("/api/v1/transactions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateTransactionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.amount").value(150.00))
                .andExpect(jsonPath("$.description").value("Updated Coffee"));

        verify(transactionService, times(1)).updateTransaction(any(UpdateTransactionDTO.class), eq(id));
    }

    @Test
    void testDeleteTransactionById_Success() throws Exception {
        Long id = 1L;
        doNothing().when(transactionService).deleteTransactionById(id);

        mockMvc.perform(delete("/api/v1/transactions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).deleteTransactionById(id);
    }

    @Test
    void testDeleteTransactionById_NotFound() throws Exception {
        Long id = 999L;
        doThrow(new com.benson.transaction_service.exceptions.TransactionNotFoundException(
                "Transaction with id " + id + " not found"))
                .when(transactionService).deleteTransactionById(id);

        mockMvc.perform(delete("/api/v1/transactions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).deleteTransactionById(id);
    }
}

