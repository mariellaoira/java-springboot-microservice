package com.java.microservice.controller;

import com.java.microservice.exception.ErrorResponse;
import com.java.microservice.model.AccountType;
import com.java.microservice.model.Customer;
import com.java.microservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing customer-related endpoints.
 */
@RestController
@RequestMapping("/api/v1/account")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	/**
     * Endpoint to create a new customer.
     *
     * @param customer The customer object to be created.
     * @return ResponseEntity with the appropriate HTTP status and response body.
     */
	@PostMapping
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
		
		// Validation checks for required fields
		if (customer.getCustomerEmail() == null || customer.getCustomerEmail().isEmpty()) {
			String errorMessage = "customerEmail is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
			String errorMessage = "customerName is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else if (customer.getCustomerMobile() == null || customer.getCustomerMobile().isEmpty()) {
			String errorMessage = "customerMobile is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else if (customer.getAddress1() == null || customer.getAddress1().isEmpty()) {
			String errorMessage = "address1 is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else if (customer.getAccountType() == null) {
			String errorMessage = "accountType is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		
		// Check if account type is valid
		if (!isValidAccountType(customer.getAccountType())) {
			String errorMessage = "Invalid value for account type. Allowed values are S or C.";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		Customer createdCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<>(new Response(createdCustomer.getCustomerNumber(), 201, "Customer account created"),
				HttpStatus.CREATED);
	}
	
	// Inner class for Response data
	static class Response {

		private int transactionStatusCode;
		private String transactionStatusDescription;
		private Long customerNumber;

		public Response(int transactionStatusCode, String transactionStatusDescription) {
			this.transactionStatusCode = transactionStatusCode;
			this.transactionStatusDescription = transactionStatusDescription;
		}

		public Response(Long customerNumber, int transactionStatusCode, String transactionStatusDescription) {
			this.transactionStatusCode = transactionStatusCode;
			this.transactionStatusDescription = transactionStatusDescription;
			this.customerNumber = customerNumber;
		}

		public int getTransactionStatusCode() {
			return transactionStatusCode;
		}

		public void setTransactionStatusCode(int transactionStatusCode) {
			this.transactionStatusCode = transactionStatusCode;
		}

		public String getTransactionStatusDescription() {
			return transactionStatusDescription;
		}

		public void setTransactionStatusDescription(String transactionStatusDescription) {
			this.transactionStatusDescription = transactionStatusDescription;
		}

		public Long getCustomerNumber() {
			return customerNumber;
		}

		public void setCustomerNumber(Long customerNumber) {
			this.customerNumber = customerNumber;
		}
	}
	
	/**
     * Check if the provided account type is valid (Savings or Checking).
     *
     * @param accountType The account type to be validated.
     * @return true if the account type is valid, false otherwise.
     */
	private boolean isValidAccountType(String accountType) {
		return "S".equalsIgnoreCase(accountType.toString()) || "C".equalsIgnoreCase(accountType.toString());
	}
}
