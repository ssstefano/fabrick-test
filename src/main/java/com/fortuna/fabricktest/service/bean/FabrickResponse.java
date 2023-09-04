package com.fortuna.fabricktest.service.bean;

import java.util.List;

public class FabrickResponse<T> {

	private String status;
	private List<FabrickError> errors;
	private T payload;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<FabrickError> getErrors() {
		return errors;
	}
	public void setErrors(List<FabrickError> errors) {
		this.errors = errors;
	}
	public T getPayload() {
		return payload;
	}
	public void setPayload(T payload) {
		this.payload = payload;
	}	
}
