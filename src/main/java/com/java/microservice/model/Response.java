package com.java.microservice.model;

public class Response {

	private int transactionStatusCode;
	private String transactionStatusDescription;
	private Long customerNumber;
	
	public Response(int transactionStatusCode, String transactionStatusDescription) {
		this.transactionStatusCode = transactionStatusCode;
		this.transactionStatusDescription = transactionStatusDescription;
	}

	public Response(Long customerNumber, int transactionStatusCode, String transactionStatusDescription) {
		this.customerNumber = customerNumber;
		this.transactionStatusCode = transactionStatusCode;
		this.transactionStatusDescription = transactionStatusDescription;
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