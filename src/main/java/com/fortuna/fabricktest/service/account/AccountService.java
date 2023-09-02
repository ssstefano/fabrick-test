package com.fortuna.fabricktest.service.account;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.ServiceException;
import com.fortuna.fabricktest.service.FabrickResponse;
import com.fortuna.fabricktest.service.FabrickRestService;
import com.fortuna.fabricktest.service.account.bean.AccountBalancePayload;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload;

@Service
public class AccountService extends FabrickRestService implements AccountServiceI {
	
	@Value( "${fabrick.url.domain}" )
	private String baseUrl;
	@Value( "${fabrick.url.accountBalance}" )
	private String accountBalanceEndpoint;
	@Value( "${fabrick.url.transactions}" )
	private String transactionsEndpoint;
	
	@Override
	public AccountBalancePayload getAccountBalance(String accountId) {
		
		URI uri = UriComponentsBuilder
				.fromUriString(baseUrl+accountBalanceEndpoint)
				.build(accountId);
		
		ParameterizedTypeReference<FabrickResponse<AccountBalancePayload>> typeRef = 
				new ParameterizedTypeReference<FabrickResponse<AccountBalancePayload>>() {};
		ResponseEntity<FabrickResponse<AccountBalancePayload>> resEntity = this.get(uri,typeRef);
		
		AccountBalancePayload balance = null;
		if(resEntity != null && resEntity.getBody() != null && resEntity.getBody().getPayload() != null) {
			FabrickResponse<AccountBalancePayload> body = resEntity.getBody();
			balance = body.getPayload();
		} else {
			throw new ServiceException(EnumError.SERVICE);
		}
	
		return balance;
	}

	@Override
	public TransactionPayload getAccountTransactions(String accountId, LocalDate fromDate, LocalDate toDate) {
		
		URI uri = UriComponentsBuilder
				.fromUriString(baseUrl+transactionsEndpoint)
				.queryParam("fromAccountingDate", fromDate.toString())
				.queryParam("toAccountingDate", toDate.toString())
				.build(accountId);
		
		ParameterizedTypeReference<FabrickResponse<TransactionPayload>> typeRef = 
				new ParameterizedTypeReference<FabrickResponse<TransactionPayload>>() {};
		ResponseEntity<FabrickResponse<TransactionPayload>> resEntity = this.get(uri,typeRef);
		
		TransactionPayload transactions = null; 
		
		if(resEntity != null && resEntity.getBody() != null && resEntity.getBody().getPayload() != null) {
			FabrickResponse<TransactionPayload> body = resEntity.getBody();
			transactions = body.getPayload();
		} else {
			throw new ServiceException(EnumError.SERVICE); 
		}
		
		return transactions;
	}
}
