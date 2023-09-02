package com.fortuna.fabricktest.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fortuna.fabricktest.controller.validation.DateConstraint;
import com.fortuna.fabricktest.service.account.AccountServiceI;
import com.fortuna.fabricktest.service.account.bean.AccountBalancePayload;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("account")
@Validated
public class AccountController {
	
	@Autowired
	private AccountServiceI accountService;
	
	@GetMapping("/balance")
	public ResponseEntity<Long> balance(@RequestParam(value = "accountId") @NotNull String accountId) {
	
		AccountBalancePayload accBalance = accountService.getAccountBalance(accountId);
		
		return ResponseEntity.ok(accBalance.getBalance());
	}
	
	//TODO: fare method validator per validare entrambe le date
	@GetMapping("/transactions")
	public ResponseEntity<List<TransactionPayload.Transaction>> transactions(
			@RequestParam(value = "accountId") @NotNull String accountId, 
			@RequestParam(value = "fromDate")  @DateConstraint String fromDate, 
			@RequestParam(value = "toDate") @DateConstraint String toDate) {
		
		TransactionPayload transactions = accountService.getAccountTransactions(accountId, LocalDate.parse(fromDate), LocalDate.parse(toDate));
		
		return ResponseEntity.ok(transactions.getList());
	}
}
