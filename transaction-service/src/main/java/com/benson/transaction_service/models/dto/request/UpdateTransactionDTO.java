package com.benson.transaction_service.models.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateTransactionDTO(
        BigDecimal amount,
        String description,
        LocalDate creationDate,
        Long categoryId,
        Long userId,
        String transactionType
) {}
