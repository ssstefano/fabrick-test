package com.fortuna.fabricktest.service.moneytransfer.bean;

import java.util.Currency;

public class CreateMoneyTransferBody {

	private String description;
	private Long amount;
	private Currency currency;
	private String executionDate;
	private Creditor creditor;
	
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

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}
	
/*	public void setExecutionDate(String executionDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.executionDate = LocalDate.parse(executionDate, formatter);
	}
*/
	public Creditor getCreditor() {
		return creditor;
	}

	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}
	
}
