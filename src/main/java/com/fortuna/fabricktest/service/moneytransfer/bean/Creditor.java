package com.fortuna.fabricktest.service.moneytransfer.bean;

public class Creditor {
	private String name;
	private Account account;
	
	public Creditor() {
		super();
	}
	
	public Creditor(Account account, String name) {
		this.name = name;
		this.account = account;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
	public String getName() {
		return name;
	}
}
