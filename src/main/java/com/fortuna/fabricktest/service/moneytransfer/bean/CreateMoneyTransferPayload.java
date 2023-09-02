package com.fortuna.fabricktest.service.moneytransfer.bean;

public class CreateMoneyTransferPayload {
	
	//mettiamo alcuni campi esemplificativi di ritorno, anche se non servono ai fini del test...
	private String moneyTransferId;
	private String status;
	private String direction;
	private Creditor creditor;
	public String getMoneyTransferId() {
		return moneyTransferId;
	}
	public void setMoneyTransferId(String moneyTransferId) {
		this.moneyTransferId = moneyTransferId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Creditor getCreditor() {
		return creditor;
	}
	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}
	
}
