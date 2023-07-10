package com.customer.customermanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.customermanagement.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findTop10ByAccount_Customer_CustomerIdOrderByIdDesc(String customerId);

}
