package com.benson.transaction_service.service;

import com.benson.transaction_service.exceptions.TransactionNotFoundException;
import com.benson.transaction_service.exceptions.UserNotFoundException;
import com.benson.transaction_service.models.Transaction;
import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.request.UpdateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;

import java.util.List;

public interface TransactionService {
        TransactionDTO createTransaction(CreateTransactionDTO createTransactionDTO);
        List<TransactionDTO> getTransactionsByUserId(Long userId) throws TransactionNotFoundException;
        TransactionDTO updateTransaction(UpdateTransactionDTO transactionDTO, Long id);
        void deleteTransactionById(Long id) throws UserNotFoundException;

}
