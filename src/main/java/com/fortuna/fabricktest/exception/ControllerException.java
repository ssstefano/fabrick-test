package com.fortuna.fabricktest.exception;

import com.fortuna.fabricktest.enums.EnumError;

public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = -4617391002541548354L;
	private EnumError error;
	
	public ControllerException() {
		super();
		error = EnumError.CONTROLLER;
	}
	
	public ControllerException(EnumError error) {
		super();
		this.error = error;
	}
	
	public ControllerException(String msg, EnumError error) {
		super(msg);
		this.error = error;
	}
	
	public ControllerException(Throwable t, EnumError error) {
		super(t);
		this.error = error;
	}
	
	public ControllerException(String msg, Throwable t, EnumError error) {
		super(msg, t);
		this.error = error;
	}

	public EnumError getError() {
		return error;
	}
}
