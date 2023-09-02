package com.fortuna.fabricktest.exception;

import com.fortuna.fabricktest.enums.EnumError;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -3535747441513073492L;
	private EnumError error;
	
	public ServiceException() {
		super();
		error = EnumError.SERVICE;
	}
	
	public ServiceException(EnumError error) {
		super();
		this.error = error;
	}
	
	public ServiceException(String msg, EnumError error) {
		super(msg);
		this.error = error;
	}
	
	public ServiceException(Throwable t, EnumError error) {
		super(t);
		this.error = error;
	}
	
	public ServiceException(String msg, Throwable t, EnumError error) {
		super(msg, t);
		this.error = error;
	}

	public EnumError getError() {
		return error;
	}
}

