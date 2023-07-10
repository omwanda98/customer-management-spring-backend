package com.customer.customermanagement.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.customer.customermanagement.entities.Transaction;
import com.customer.customermanagement.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final Logger requestLogger = LoggerFactory.getLogger("REQUEST_LOGGER");


   // @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionDetails(@PathVariable("transactionId") Long transactionId) {
    	// Logging request
        requestLogger.info("Received request: GET /api/transactions/{}", transactionId);
        Transaction transaction = transactionService.getTransactionById(transactionId);
     // Logging response
        requestLogger.info("Sending response: GET /api/transactions/{}\nPayload: {}", transactionId, transaction);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        }
        return ResponseEntity.notFound().build();
    }
}

