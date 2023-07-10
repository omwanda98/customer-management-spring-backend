package com.customer.customermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customermanagement.entities.Account;
import com.customer.customermanagement.entities.Customer;
import com.customer.customermanagement.entities.Transaction;
import com.customer.customermanagement.repositories.AccountRepository;
import com.customer.customermanagement.repositories.CustomerRepository;
import com.customer.customermanagement.repositories.TransactionRepository;
import com.customer.customermanagement.security.JwtUserDetailsService;
import com.customer.customermanagement.security.JwtUtil;

import java.util.List;
import java.util.Random;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsService userDetailsService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           TransactionRepository transactionRepository,
                           JwtUtil jwtUtil,
                           JwtUserDetailsService userDetailsService
                           ) 
    {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    public Customer registerCustomer(String name, String email, String customerId) {
        // Generate random PIN
        String pin = generateRandomPin();

        // Create a new customer
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setCustomerId(customerId);
        customer.setPin(pin);

        // Create a new account for the customer with zero balance
        Account account = new Account();
        account.setCustomer(customer);
        account.setBalance(0.0);

        // Save the customer and account in the database
        customerRepository.save(customer);
        accountRepository.save(account);

        return customer;
    }

    public String loginCustomer(String customerId, String pin) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer != null && customer.getPin().equals(pin)) {
            // Generate and return a JSON token
            String token = jwtUtil.generateToken(customerId);
            return token;
        }
        return null;
    }

    public Double getCustomerBalance(String customerId) {
        Account account = accountRepository.findByCustomer_CustomerId(customerId);
        return account != null ? account.getBalance() : null;
    }

    public List<Transaction> getCustomerMiniStatement(String customerId) {
        return transactionRepository.findTop10ByAccount_Customer_CustomerIdOrderByIdDesc(customerId);
    }

    public Double cashDeposit(String customerId, Double amount) {
        Account account = accountRepository.findByCustomer_CustomerId(customerId);
        if (account != null) {
            // Update account balance
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
            return account.getBalance();
        }
        return null;
    }

    public Double cashWithdrawal(String customerId, Double amount) {
        Account account = accountRepository.findByCustomer_CustomerId(customerId);
        if (account != null && account.getBalance() >= amount) {
            // Update account balance
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
            return account.getBalance();
        }
        return null;
    }

    public Double fundsTransfer(String sourceCustomerId, String targetCustomerId, Double amount) {
        Account sourceAccount = accountRepository.findByCustomer_CustomerId(sourceCustomerId);
        Account targetAccount = accountRepository.findByCustomer_CustomerId(targetCustomerId);
        if (sourceAccount != null && targetAccount != null && sourceAccount.getBalance() >= amount) {
            // Debit source account
            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
            accountRepository.save(sourceAccount);

            // Credit target account
            targetAccount.setBalance(targetAccount.getBalance() + amount);
            accountRepository.save(targetAccount);

            return sourceAccount.getBalance();
        }
        return null;
    }

    public Transaction searchTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    private String generateRandomPin() {
        // Generate a 4-digit random PIN
        Random random = new Random();
        int pin = 1000 + random.nextInt(9000);
        return String.valueOf(pin);
    }

    private String generateJsonToken(String customerId) {
        // Generate and return the JSON token logic here
        // This implementation depends on the specific library or mechanism you are using for JSON token generation
        // You can refer to the documentation or examples of the chosen library to generate and return the JSON token
        return null;
    }
}
