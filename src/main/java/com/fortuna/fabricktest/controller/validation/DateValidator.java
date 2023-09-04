package com.fortuna.fabricktest.controller.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateConstraint, String> {
	

	@Override
	public boolean isValid(String field, ConstraintValidatorContext ctx) {
		
		if(field != null) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate.parse(field, formatter);
				
				return true;
			} catch(IllegalArgumentException | DateTimeParseException e) {
				return false;
			}
		} else {
			return false;
		}	
	}
}
