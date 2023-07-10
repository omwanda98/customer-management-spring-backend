package com.customer.customermanagement.controller;

import com.customer.customermanagement.entities.Customer;
import com.customer.customermanagement.entities.Transaction;
import com.customer.customermanagement.security.JwtUtil;
import com.customer.customermanagement.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtUtil jwtUtil;
    private final Logger requestLogger = LoggerFactory.getLogger("REQUEST_LOGGER");

   // @Autowired
    public CustomerController(CustomerService customerService, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

//    @PostMapping
//    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
//    	//logging request payload
//    	requestLogger.info("Received request: POST /api/customers\nPayload: {}", customer);
//        Customer registeredCustomer = customerService.registerCustomer(customer.getName(), customer.getEmail(), customer.getCustomerId());
//        //logging response payload
//        requestLogger.info("Sending response: POST /api/customers\nPayload: {}", registeredCustomer);
//        return ResponseEntity.ok(registeredCustomer);
//    }
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        // Logging request payload
        requestLogger.info("Received request: POST /api/customers/register\nPayload: {}", customer);
        
        Customer registeredCustomer = customerService.registerCustomer(customer.getName(), customer.getEmail(), customer.getCustomerId());
        
        // Logging response payload
        requestLogger.info("Sending response: POST /api/customers/register\nPayload: {}", registeredCustomer);
        
        return ResponseEntity.ok(registeredCustomer);
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestParam("customerId") String customerId, @RequestParam("pin") String pin) {
        String token = customerService.loginCustomer(customerId, pin);
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{customerId}/balance")
    public ResponseEntity<Double> getCustomerBalance(@PathVariable("customerId") String customerId) {
        Double balance = customerService.getCustomerBalance(customerId);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{customerId}/ministatement")
    public ResponseEntity<List<Transaction>> getCustomerMiniStatement(@PathVariable("customerId") String customerId) {
        List<Transaction> miniStatement = customerService.getCustomerMiniStatement(customerId);
        return ResponseEntity.ok(miniStatement);
    }
}
