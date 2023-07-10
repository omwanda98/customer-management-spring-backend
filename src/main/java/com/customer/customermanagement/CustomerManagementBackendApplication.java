package com.customer.customermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.customer.customermanagement.entities.JwtProperties;

@SpringBootApplication
@EntityScan("com.customer.customermanagement.entities")
@EnableConfigurationProperties(JwtProperties.class)

public class CustomerManagementBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementBackendApplication.class, args);
	}

}
