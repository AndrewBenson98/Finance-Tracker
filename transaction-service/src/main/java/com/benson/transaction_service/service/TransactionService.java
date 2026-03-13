package com.benson.transaction_service.service;

import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;

import java.util.List;

public interface TransactionService {
        TransactionDTO createTransaction(CreateTransactionDTO createTransactionDTO);
        List<TransactionDTO> getTransactionsByUserId(Long userId);

}
