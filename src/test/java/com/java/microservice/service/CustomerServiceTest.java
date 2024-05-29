package com.java.microservice.service;

import com.java.microservice.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * JUnit test class for testing the CustomerService class.
 */
@SpringBootTest
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	/**
	 * Test method to verify the createCustomer method of CustomerService. It checks
	 * if the method creates a customer object with correct attributes.
	 */
	@Test
	public void testCreateCustomer() {
		// Create a sample customer
		Customer customer = new Customer();
		customer.setCustomerName("John Doe");
		customer.setCustomerEmail("john.doe@example.com");
		customer.setCustomerMobile("09081234567");
		customer.setAddress1("Makati City");
		customer.setAddress2("Metro Manila");
		customer.setAccountType("C");

		// Call the service method to create the customer
		Customer createdCustomer = customerService.createCustomer(customer);

		// Assertions to verify the result
		assertNotNull(createdCustomer);
		assertNotNull(createdCustomer.getCustomerNumber());
		assertEquals("John Doe", createdCustomer.getCustomerName());
		assertEquals("john.doe@example.com", createdCustomer.getCustomerEmail());
	}
}
