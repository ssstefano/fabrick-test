package com.fortuna.fabricktest.service.account.bean;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class TransactionPayload {
	
	private List<Transaction> list;

	public List<Transaction> getList() {
		return list;
	}
	public void setList(List<Transaction> list) {
		this.list = list;
	}

	public static class Transaction {
		private String transactionId;
		private String operationId;
		private LocalDate accountingDate;
		private LocalDate valueDate;
		private Currency currency;
		private String description;
		public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
		public String getOperationId() {
			return operationId;
		}
		public void setOperationId(String operationId) {
			this.operationId = operationId;
		}
		public LocalDate getAccountingDate() {
			return accountingDate;
		}
		public void setAccountingDate(LocalDate accountingDate) {
			this.accountingDate = accountingDate;
		}
		public LocalDate getValueDate() {
			return valueDate;
		}
		public void setValueDate(LocalDate valueDate) {
			this.valueDate = valueDate;
		}
		public Currency getCurrency() {
			return currency;
		}
		public void setCurrency(Currency currency) {
			this.currency = currency;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
