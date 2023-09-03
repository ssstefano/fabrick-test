package com.fortuna.fabricktest.enums;

public enum EnumError {
	
	RUNTIME("R0001", "Internal server error"),
	SERVICE("S0001", "Generic service error"),
	REST("A0001", "Generic rest call error"),
	VALIDATION("V0001", "Validation error"),
	SERVICE_TRANSACTION("S0002", "toDate / fromDate must be not null and toDate must be >= fromDate");
	
	private String errorCode;
	private String errorMessage;
	
	EnumError(String errorCode, String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}	
	
}
