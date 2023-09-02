package com.fortuna.fabricktest.service.moneytransfer.bean;

public class Creditor {
	private String name;
	private Account account;
	
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account acc) {
		this.account = acc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
