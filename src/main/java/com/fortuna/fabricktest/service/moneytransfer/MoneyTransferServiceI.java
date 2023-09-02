package com.fortuna.fabricktest.service.moneytransfer;

import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.exception.FabrickRestException;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferPayload;

public interface MoneyTransferServiceI {
	//public CreateMoneyTransferPayload createMoneyTransfer(String accountId, String creditorName, String creditorIban, Long amount, Currency curr, LocalDate excDate, String description) throws RestException;
	public CreateMoneyTransferPayload createMoneyTransfer(CreateMoneyTransferReq moneyTransferInput, String accountId) throws FabrickRestException;
}
