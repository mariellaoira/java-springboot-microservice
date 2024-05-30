package com.java.microservice.model;

public enum AccountType {
	SAVINGS("savings"), CHECKING("checking"), S("S"), C("C");

	private final String value;

	AccountType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static AccountType fromUserInput(String input) {
		switch (input.toUpperCase()) {
		case "S":
			return SAVINGS;
		case "C":
			return CHECKING;
		default:
			throw new IllegalArgumentException("Invalid account type. Allowed values are 'S' and 'C'.");
		}
	}
}
