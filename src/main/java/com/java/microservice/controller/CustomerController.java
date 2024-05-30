package com.java.microservice.controller;

import com.java.microservice.exception.ErrorResponse;
import com.java.microservice.model.AccountType;
import com.java.microservice.model.Customer;
import com.java.microservice.model.CustomerRequest;
import com.java.microservice.model.CustomerResponse;
import com.java.microservice.model.Response;
import com.java.microservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

	/**
	 * Retrieves a customer by their customer number.
	 *
	 * @param customerNumber The customer number to look up.
	 * @return ResponseEntity with the customer information if found, or an error
	 *         response if not found.
	 */
	@GetMapping("/{customerNumber}")
	public ResponseEntity<?> getCustomerByCustomerNumber(@PathVariable Long customerNumber) {
		Optional<Customer> customerOpt = customerService.getCustomerByCustomerNumber(customerNumber);
		if (customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			return new ResponseEntity<>(new CustomerResponse(customer, 302, "Customer Account found"),
					HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(new ErrorResponse(401, "Customer not found"), HttpStatus.NOT_FOUND);
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
