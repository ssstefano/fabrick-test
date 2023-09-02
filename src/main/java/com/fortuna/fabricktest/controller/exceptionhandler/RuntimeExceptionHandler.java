package com.fortuna.fabricktest.controller.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fortuna.fabricktest.controller.bean.ErrorRes;
import com.fortuna.fabricktest.enums.EnumError;

@ControllerAdvice
@Order(2)
public class RuntimeExceptionHandler extends GenericExceptionHandler {
  
   @ExceptionHandler(RuntimeException.class)
   protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
	   
	  List<ErrorRes> errors = new ArrayList<>();
	  ErrorRes error = new ErrorRes(EnumError.RUNTIME.getErrorCode(),EnumError.RUNTIME.getErrorMessage());
	  errors.add(error);
	  
	  return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.valueOf(500), request);
   }
}