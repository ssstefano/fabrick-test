package com.fortuna.fabricktest.service.moneytransfer.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

public class CreateMoneyTransferBody {

	private String description;
	private Long amount;
	private Currency currency;
	private String executionDate;
	private Creditor creditor;
	
	
	public CreateMoneyTransferBody (CreateMoneyTransferBodyBuilder builder) {
		this.description = builder.description;
		this.executionDate = builder.executionDate;
		this.amount = builder.amount;
		this.currency = builder.currency;
		this.creditor = builder.creditor;
	}
	
	
	public static class CreateMoneyTransferBodyBuilder {
		protected String description;
		protected String executionDate;
		protected Currency currency;
		protected Creditor creditor;
		protected Long amount;
		
		public CreateMoneyTransferBodyBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public CreateMoneyTransferBodyBuilder withExecutionDate(String executionDate) {
			this.executionDate = executionDate;
			return this;
		}
		
		public CreateMoneyTransferBodyBuilder withExecutionDate(LocalDate executionDate) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			this.executionDate = executionDate.format(formatter);
			return this;
		}
		
		public CreateMoneyTransferBodyBuilder withCurrency(Currency currency) {
			this.currency = currency;
			return this;
		}
		public CreateMoneyTransferBodyBuilder withAmount(Long amount) {
			this.amount = amount;
			return this;
		}
		
		public CreateMoneyTransferBodyBuilder withCreditor(String creditorName, String accountCode) {
			
			Account acc = new Account(accountCode);
			Creditor cred = new Creditor(acc, creditorName);
			
			this.creditor = cred;
			return this;
		}
		
		public CreateMoneyTransferBody build() {
			return new CreateMoneyTransferBody(this);
		}
		
	}
	
	public String getDescription() {
		return description;
	}
	
	public Long getAmount() {
		return amount;
	}


	public Currency getCurrency() {
		return currency;
	}
	
	public String getExecutionDate() {
		return executionDate;
	}

	public Creditor getCreditor() {
		return creditor;
	}
}
