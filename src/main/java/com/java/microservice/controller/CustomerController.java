package com.java.microservice.controller;

import com.java.microservice.exception.ErrorResponse;
import com.java.microservice.model.AccountType;
import com.java.microservice.model.Customer;
import com.java.microservice.model.CustomerRequest;
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
	public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {

		Customer customer = new Customer();

		// Validation checks for required fields
		if (customerRequest.getCustomerEmail() == null || customerRequest.getCustomerEmail().isEmpty()) {
			String errorMessage = "customerEmail is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else {
			customer.setCustomerEmail(customerRequest.getCustomerEmail());
		}

		if (customerRequest.getCustomerName() == null || customerRequest.getCustomerName().isEmpty()) {
			String errorMessage = "customerName is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else {
			customer.setCustomerName(customerRequest.getCustomerName());
		}

		if (customerRequest.getCustomerMobile() == null || customerRequest.getCustomerMobile().isEmpty()) {
			String errorMessage = "customerMobile is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else {
			customer.setCustomerMobile(customerRequest.getCustomerMobile());
		}

		if (customerRequest.getAddress1() == null || customerRequest.getAddress1().isEmpty()) {
			String errorMessage = "address1 is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else {
			customer.setAddress1(customerRequest.getAddress1());
		}

		if (customerRequest.getAddress2() == null || customerRequest.getAddress2().isEmpty()) {
			customer.setAddress2(null);
		} else {
			customer.setAddress2(customerRequest.getAddress2());
		}

		if (customerRequest.getAccountType() == null) {
			String errorMessage = "accountType is a required field";
			ErrorResponse errorResponse = new ErrorResponse(errorMessage);
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} else {
			if (!isValidAccountType(customerRequest.getAccountType())) {
				String errorMessage = "Invalid value for account type. Allowed values are " + AccountType.S.getValue()
						+ " or " + AccountType.C.getValue() + ".";
				ErrorResponse errorResponse = new ErrorResponse(errorMessage);
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
			} else {
				customer.setAccountType(AccountType.fromUserInput(customerRequest.getAccountType()));
			}
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
		return AccountType.S.getValue().equalsIgnoreCase(accountType)
				|| AccountType.C.getValue().equalsIgnoreCase(accountType);
	}
}
