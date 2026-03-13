package com.benson.transaction_service.models.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionDTO(
        BigDecimal amount,
        String description,
        LocalDateTime creationDate,
        Long categoryId,
        Long userId,
        String transactionType
){}
