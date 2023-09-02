package com.fortuna.fabricktest.controller.bean;

import java.util.Currency;

import com.fortuna.fabricktest.controller.validation.DateConstraint;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateMoneyTransferReq {
	
	@NotNull
	private String creditorName;
	@NotNull
	private String creditorIban;
	@DateConstraint
	private String executionDate;
	@NotNull
	private String description;
	@NotNull
	@Positive
	private Long amount;
	private Currency currency;
	
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getCreditorIban() {
		return creditorIban;
	}
	public void setCreditorIban(String creditorIban) {
		this.creditorIban = creditorIban;
	}
	public String getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
