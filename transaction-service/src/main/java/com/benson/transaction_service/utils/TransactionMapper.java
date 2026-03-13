package com.benson.transaction_service.utils;

import com.benson.transaction_service.models.Transaction;
import com.benson.transaction_service.models.dto.request.CreateTransactionDTO;
import com.benson.transaction_service.models.dto.response.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "creationDate", source="creationDate", dateFormat="yyyy-mm-dd")
    TransactionDTO toDto(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);


    @Mapping(target = "creationDate", source="creationDate", dateFormat="yyyy-mm-dd")
    Transaction toEntity(CreateTransactionDTO createTransactionDTO);

}
