package com.customer.customermanagement.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customermanagement.entities.Account;
import com.customer.customermanagement.entities.Transaction;
import com.customer.customermanagement.repositories.AccountRepository;
import com.customer.customermanagement.repositories.TransactionRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

   // @Autowired
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Double cashDeposit(String customerId, Double amount) {
        Account account = accountRepository.findByCustomer_CustomerId(customerId);
        if (account != null) {
            // Update account balance
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);

            // Create a deposit transaction
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(amount);
            transactionRepository.save(transaction);

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

            // Create a withdrawal transaction
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setAmount(-amount);
            transactionRepository.save(transaction);

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

            // Create a transaction for the transfer from the source account
            Transaction sourceTransaction = new Transaction();
            sourceTransaction.setAccount(sourceAccount);
            sourceTransaction.setAmount(-amount);
            transactionRepository.save(sourceTransaction);

            // Create a transaction for the transfer to the target account
            Transaction targetTransaction = new Transaction();
            targetTransaction.setAccount(targetAccount);
            targetTransaction.setAmount(amount);
            transactionRepository.save(targetTransaction);

            return sourceAccount.getBalance();
        }
        return null;
    }
}
