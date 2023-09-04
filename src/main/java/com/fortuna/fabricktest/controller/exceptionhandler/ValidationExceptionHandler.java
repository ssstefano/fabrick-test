package com.fortuna.fabricktest.controller.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fortuna.fabricktest.controller.bean.ErrorRes;
import com.fortuna.fabricktest.enums.EnumError;

import jakarta.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationExceptionHandler extends BaseExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	
    	List<ErrorRes> errors = ex.getBindingResult().getFieldErrors().stream()
    	.map(err -> new ErrorRes(EnumError.VALIDATION.getErrorCode(), err.getField() + " "+ err.getDefaultMessage()))
    	.collect(Collectors.toList());
        
    	return handleExceptionInternalWithInfoLog(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    	
    	List<ErrorRes> errors =  new ArrayList<>();
    	
    	errors.add(new ErrorRes(EnumError.VALIDATION.getErrorCode(), ex.getMessage()));
    
    	return handleExceptionInternalWithInfoLog(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintExceptionHandler(ConstraintViolationException ex, WebRequest request) {
    	
    	List<ErrorRes> errors =  new ArrayList<>();
    	
    	errors.add(new ErrorRes(EnumError.VALIDATION.getErrorCode(), ex.getMessage()));
    
    	return handleExceptionInternalWithInfoLog(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}