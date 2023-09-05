package com.fortuna.fabricktest.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fortuna.fabricktest.controller.validation.DateFormatConstraint;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.ControllerException;
import com.fortuna.fabricktest.service.account.AccountServiceI;
import com.fortuna.fabricktest.service.account.bean.AccountBalancePayload;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload.Transaction;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("account/{accountId}")
@Validated
public class AccountController {
	
	@Autowired
	private AccountServiceI accountService;
	
	@GetMapping("/balance")
	public ResponseEntity<Long> balance(@PathVariable(value = "accountId") @NotNull String accountId) {
	
		AccountBalancePayload accBalance = accountService.getAccountBalance(accountId);
		
		return ResponseEntity.ok(accBalance.getBalance());
	}
	
	@GetMapping("/transactions")
	public ResponseEntity<List<Transaction>> transactions(
			@PathVariable(value = "accountId") @NotNull String accountId, 
			@RequestParam(value = "fromDate")  @DateFormatConstraint String fromDate, 
			@RequestParam(value = "toDate") @DateFormatConstraint String toDate) {
		
		LocalDate from = LocalDate.parse(fromDate);
		LocalDate to = LocalDate.parse(toDate);
		
		if(to.isAfter(from) || to.isEqual(from) ) {
			TransactionPayload transactions = accountService.getAccountTransactionsAndSave(accountId, from , to);
			
			return ResponseEntity.ok(transactions.getList());
		} else {
			throw new ControllerException(EnumError.CONTROLLER_DATE_CONSISTENCY);
		}
	}
}
