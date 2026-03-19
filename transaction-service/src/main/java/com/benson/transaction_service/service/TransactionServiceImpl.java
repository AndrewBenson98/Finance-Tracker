package com.benson.transaction_service.service;

import com.benson.transaction_service.exceptions.TransactionNotFoundException;
import com.benson.transaction_service.exceptions.UserNotFoundException;
import com.benson.transaction_service.models.Transaction;
import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;
import com.benson.transaction_service.repository.TransactionRepository;
import com.benson.transaction_service.utils.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(@Autowired TransactionRepository transactionRepository, @Autowired TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }


    @Override
    public TransactionDTO createTransaction(CreateTransactionDTO createTransactionDTO) {

        System.out.println("DTO Date: " + createTransactionDTO.creationDate()) ;
//        transactionRepository.findByUserId(createTransactionDTO.userId()).ifPresent(transaction ->
//        {throw new RuntimeException("User with id " + createTransactionDTO.userId() + " already has a transaction");});

        // Map to entity
        Transaction transaction = transactionMapper.toEntity(createTransactionDTO);
        System.out.println(transaction.getCreationDate());

        //save to database
        Transaction savedTransaction = transactionRepository.save(transaction);
        System.out.println("Saved Transaction: "+savedTransaction.getCreationDate());
        return transactionMapper.toDto(savedTransaction);

    }

    @Override
    public List<TransactionDTO> getTransactionsByUserId(Long userId) throws TransactionNotFoundException {
        List<Transaction> transactions = transactionRepository.findByUserId(userId).orElseThrow(() -> new TransactionNotFoundException("No transactions found for user with id " + userId));
        return transactions.stream().map(transactionMapper::toDto).toList();
    }

    @Override
    public void deleteTransactionById(Long id) throws UserNotFoundException {
        if (!transactionRepository.existsById(id)) {
            throw new UserNotFoundException("Transaction with id " + id + " not found");
        }
        transactionRepository.deleteById(id);
    }
}
