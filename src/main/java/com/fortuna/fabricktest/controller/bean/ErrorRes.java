package com.fortuna.fabricktest.controller.bean;

import com.fortuna.fabricktest.enums.EnumError;

public class ErrorRes {
	private String errorCode;
	private String errorMessage;
	
	@SuppressWarnings("unused")
	private ErrorRes() {}
	
	public ErrorRes(EnumError e) {
		this.errorCode = e.getErrorCode();
		this.errorMessage = e.getErrorMessage();
	}
	
	public ErrorRes(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
}
