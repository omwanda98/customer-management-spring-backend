package com.customer.customermanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.customermanagement.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByCustomer_CustomerId(String customerId);

}
