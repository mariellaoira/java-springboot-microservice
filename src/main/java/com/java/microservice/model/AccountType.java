package com.java.microservice.model;

public enum AccountType {
	S("Savings"), C("Checking");

	private String value;

	private AccountType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
