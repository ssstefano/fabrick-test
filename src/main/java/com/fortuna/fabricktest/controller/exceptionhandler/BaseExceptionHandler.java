package com.fortuna.fabricktest.controller.exceptionhandler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fortuna.fabricktest.controller.bean.ErrorRes;


public class BaseExceptionHandler extends ResponseEntityExceptionHandler {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	protected ResponseEntity<Object> handleExceptionInternalWithErrorLog(Exception ex, List<ErrorRes> errors, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ServletWebRequest asWebReq = (((ServletWebRequest)request));
		String method = asWebReq.getRequest().getMethod();
		String path = asWebReq.getRequest().getRequestURI();
		
		LOGGER.error("Exception {} || statusCode {} || path {}",ex.getClass().getName(), status, method + " " + path, ex);
	    return super.handleExceptionInternal(ex, errors, headers, status, request);
	  }
	
	protected ResponseEntity<Object> handleExceptionInternalWithInfoLog(Exception ex, List<ErrorRes> errors, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ServletWebRequest asWebReq = (((ServletWebRequest)request));
		String method = asWebReq.getRequest().getMethod();
		String path = asWebReq.getRequest().getRequestURI();
		
		LOGGER.info("Exception {} || statusCode {} || path {}",ex.getClass().getName(), status, method + " " +path);
	    return super.handleExceptionInternal(ex, errors, headers, status, request);
	  }

}
