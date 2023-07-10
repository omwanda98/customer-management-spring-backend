package com.customer.customermanagement.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customer.customermanagement.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

   // @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<Double> cashDeposit(@RequestParam("customerId") String customerId, @RequestParam("amount") Double amount) {
        Double balance = accountService.cashDeposit(customerId, amount);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Double> cashWithdrawal(@RequestParam("customerId") String customerId, @RequestParam("amount") Double amount) {
        Double balance = accountService.cashWithdrawal(customerId, amount);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Double> fundsTransfer(@RequestParam("sourceCustomerId") String sourceCustomerId, @RequestParam("targetCustomerId") String targetCustomerId, @RequestParam("amount") Double amount) {
        Double balance = accountService.fundsTransfer(sourceCustomerId, targetCustomerId, amount);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }
}
