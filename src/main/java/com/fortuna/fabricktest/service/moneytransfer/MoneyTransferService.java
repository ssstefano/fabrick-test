package com.fortuna.fabricktest.service.moneytransfer;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.FabrickRestException;
import com.fortuna.fabricktest.exception.ServiceException;
import com.fortuna.fabricktest.service.FabrickResponse;
import com.fortuna.fabricktest.service.FabrickRestService;
import com.fortuna.fabricktest.service.moneytransfer.bean.Account;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferBody;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferPayload;
import com.fortuna.fabricktest.service.moneytransfer.bean.Creditor;

@Service
public class MoneyTransferService extends FabrickRestService implements MoneyTransferServiceI {

	@Value( "${fabrick.url.domain}" )
	private String baseUrl;
	@Value( "${fabrick.url.createMoneyTransfer}" )
	private String createMoneyTransfereEndpoint;
	
	public MoneyTransferService(RestTemplateBuilder builder) {
		super(builder);
	}
	
	@Override
	public CreateMoneyTransferPayload createMoneyTransfer(CreateMoneyTransferReq moneyTransferInput, String accountId) throws FabrickRestException {		
		URI uri = UriComponentsBuilder
				.fromUriString(baseUrl+createMoneyTransfereEndpoint)
				.build(accountId);
		
		//TODO implementare builder
		CreateMoneyTransferBody body = new CreateMoneyTransferBody();
		
		Account acc = new Account();
		acc.setAccountCode(moneyTransferInput.getCreditorIban());
		
		Creditor cred = new Creditor();
		cred.setAccount(acc);
		cred.setName(moneyTransferInput.getCreditorName());
		body.setCreditor(cred);
		
		body.setAmount(moneyTransferInput.getAmount());
		body.setCurrency(moneyTransferInput.getCurrency());
		
		body.setExecutionDate(moneyTransferInput.getExecutionDate());
		
		body.setDescription(moneyTransferInput.getDescription());
		
		ParameterizedTypeReference<FabrickResponse<CreateMoneyTransferPayload>> typeRef = 
				new ParameterizedTypeReference<FabrickResponse<CreateMoneyTransferPayload>>() {};
		
		CreateMoneyTransferPayload payload = null;
	
		ResponseEntity<FabrickResponse<CreateMoneyTransferPayload>> resEntity = this.post(uri, body, typeRef);
		if(resEntity != null && resEntity.getBody() != null) {
			FabrickResponse<CreateMoneyTransferPayload> bodyRes = resEntity.getBody();
			payload = bodyRes.getPayload();	
		} else {
			throw new ServiceException(EnumError.SERVICE);
		}
		
		return payload;
	}

}
