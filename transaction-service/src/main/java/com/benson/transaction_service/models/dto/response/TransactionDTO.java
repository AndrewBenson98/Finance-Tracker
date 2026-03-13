package com.benson.transaction_service.models.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(

    Long id,
    BigDecimal amount,
    String description,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDateTime creationDate,
    Long categoryId,
    Long userId,
    String transactionType
) {}
