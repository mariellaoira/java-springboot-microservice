package com.java.microservice.service;

import com.java.microservice.model.Customer;
import com.java.microservice.repository.CustomerRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	public Optional<Customer> getCustomerByCustomerNumber(Long customerNumber) {
		return customerRepository.findById(customerNumber);
	}

}