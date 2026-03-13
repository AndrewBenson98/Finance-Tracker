package com.benson.transaction_service.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private BigDecimal amount;

    private String description;

    private LocalDate creationDate;

    @Column( nullable = false)
    private Long userId;


    private Long categoryId;


    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private TransactionType transactionType;






}
