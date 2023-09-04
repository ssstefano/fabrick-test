package com.fortuna.fabricktest.data.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
public class TransactionEntity {
	@Id
	private String transactionId;
	@Column(name="OPERATION_ID")
	private String operationId;
	@Column(name="DESCRIPTION")
	private String description;
	@Column(name="ACCOUNTING_DATE")
	private LocalDate accountingDate;
	@Column(name="VALUE_DATE")
	private LocalDate valueDate;
	@Column(name="CURRENCY")
	private String currency;
	
	

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
