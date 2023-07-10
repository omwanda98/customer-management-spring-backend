package com.customer.customermanagement.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customer.customermanagement.entities.Customer;
import com.customer.customermanagement.repositories.CustomerRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public JwtUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found with customerId: " + customerId);
        }
        return new User(customerId, customer.getPin(), new ArrayList<>());
    }
}

