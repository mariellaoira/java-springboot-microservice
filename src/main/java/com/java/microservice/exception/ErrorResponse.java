package com.java.microservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ErrorResponse {
	@JsonProperty("transactionStatusCode")
	private int transactionStatusCode;

	@JsonProperty("transactionStatusDescription")
	private String transactionStatusDescription;

	public ErrorResponse(int transactionStatusCode, String transactionStatusDescription) {
		this.transactionStatusCode = transactionStatusCode;
		this.transactionStatusDescription = transactionStatusDescription;
	}

	public ErrorResponse(String transactionStatusDescription) {
		this(400, transactionStatusDescription);
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

}
