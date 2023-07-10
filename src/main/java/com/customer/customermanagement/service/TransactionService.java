package com.customer.customermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customermanagement.entities.Transaction;
import com.customer.customermanagement.repositories.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

   // @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getCustomerMiniStatement(String customerId) {
        return transactionRepository.findTop10ByAccount_Customer_CustomerIdOrderByIdDesc(customerId);
    }

    public Transaction searchTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }
}

