package com.fortuna.fabricktest.service.moneytransfer.bean;

public class Account {
	private String accountCode;
	
	public Account() {
		super();
	}
	public Account(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

}
