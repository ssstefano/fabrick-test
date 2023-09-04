package com.fortuna.fabricktest.controller.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		LOGGER.error("Exception {} || statusCode {} || path {}",ex.getClass().getName(), status, ((ServletWebRequest)request).getRequest().getRequestURI().toString(), ex);
	    return super.handleExceptionInternal(ex, body, headers, status, request);
	  }

}
