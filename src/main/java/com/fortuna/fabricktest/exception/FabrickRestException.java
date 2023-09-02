package com.fortuna.fabricktest.exception;

import java.util.List;

import org.springframework.http.HttpStatusCode;

import com.fortuna.fabricktest.service.FabrickError;

public class FabrickRestException  extends RuntimeException {
	private static final long serialVersionUID = -7527988668224662134L;
	private List<FabrickError> errors;
	private HttpStatusCode statusCode;
	
	public FabrickRestException(HttpStatusCode statusCode, List<FabrickError> errors) {
		super();
		this.errors = errors;
		this.statusCode = statusCode;
	
	}
	
	public FabrickRestException(HttpStatusCode statusCode, List<FabrickError> errors, Throwable t) {
		super(t);
		this.errors = errors;
		this.statusCode = statusCode;
	}
	
	public FabrickRestException(HttpStatusCode statusCode, List<FabrickError> errors, String msg, Throwable t) {
		super(msg, t);
		this.errors = errors;
		this.statusCode = statusCode;
	}
	
	public List<FabrickError> getErrors() {
		return errors;
	}

	public HttpStatusCode getStatusCode() {
		return statusCode;
	}
}
