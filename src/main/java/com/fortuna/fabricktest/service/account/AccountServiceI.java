package com.fortuna.fabricktest.service.account;

import java.time.LocalDate;

import com.fortuna.fabricktest.service.account.bean.AccountBalancePayload;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload;

public interface AccountServiceI {
	public AccountBalancePayload getAccountBalance(String accountId);
	public TransactionPayload getAccountTransactions(String accountId, LocalDate fromDate, LocalDate toDate);
	public TransactionPayload getAccountTransactionsAndSave(String accountId, LocalDate fromDate, LocalDate toDate);
}
