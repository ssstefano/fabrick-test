package com.fortuna.fabricktest.controller.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fortuna.fabricktest.controller.bean.ErrorRes;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.FabrickRestException;
import com.fortuna.fabricktest.exception.ServiceException;

@Order(1)
@ControllerAdvice
public class CustomExceptionHandler extends GenericExceptionHandler {
  
   @ExceptionHandler(FabrickRestException.class)
   protected ResponseEntity<Object> handleFabrickRestException(FabrickRestException ex, WebRequest request) {
	   
	   List<ErrorRes> errors = null;
	   HttpStatusCode statusCode = ex.getStatusCode();
	   
	   if(statusCode == null) {
		   statusCode = HttpStatusCode.valueOf(500);
	   }
	
	   if(ex.getErrors() != null && ex.getErrors().size() > 0) {
		   
		   errors = ex.getErrors().stream()
				   .map(err -> new ErrorRes(err.getCode(), err.getDescription()))
				   .collect(Collectors.toList());
	   } else {
		   ErrorRes error = new ErrorRes(EnumError.REST.getErrorCode(), EnumError.REST.getErrorMessage());
		   errors = new ArrayList<>();
		   errors.add(error);
	   }
	   
	   return handleExceptionInternal(ex, errors, new HttpHeaders(), statusCode, request);
   }
   
   @ExceptionHandler(ServiceException.class)
   protected ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
	   
	  List<ErrorRes> errors = new ArrayList<>();
	  ErrorRes error = new ErrorRes(ex.getError().getErrorCode(),ex.getError().getErrorMessage());
	  errors.add(error);
	  
	  return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatusCode.valueOf(500), request);
   }
}