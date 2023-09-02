package com.fortuna.fabricktest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.service.moneytransfer.MoneyTransferServiceI;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferPayload;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/moneytransfer")
@Validated
public class MoneyTransferController {
	
	@Autowired
	private MoneyTransferServiceI moneyTransferService;
	
	@PostMapping("/create")
	public ResponseEntity<CreateMoneyTransferPayload> createMoneyTransfer(
			@RequestBody @Valid CreateMoneyTransferReq input, 
			@RequestParam(value = "accountId") @NotNull String accountId) {
		
		CreateMoneyTransferPayload payload = moneyTransferService.createMoneyTransfer(input, accountId);
		return ResponseEntity.status(201).body(payload);
	
	}
}
