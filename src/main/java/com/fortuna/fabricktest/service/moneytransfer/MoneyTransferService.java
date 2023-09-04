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
import com.fortuna.fabricktest.service.FabrickRestService;
import com.fortuna.fabricktest.service.bean.FabrickResponse;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferBody;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferBody.CreateMoneyTransferBodyBuilder;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferPayload;

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
		
		CreateMoneyTransferBodyBuilder builder = new CreateMoneyTransferBodyBuilder();
		
		CreateMoneyTransferBody body = builder.withAmount(moneyTransferInput.getAmount())
				.withCurrency(moneyTransferInput.getCurrency())
				.withDescription(moneyTransferInput.getDescription())
				.withExecutionDate(moneyTransferInput.getExecutionDate())
				.withCreditor(moneyTransferInput.getCreditorName(), moneyTransferInput.getCreditorIban())
				.build();
		
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
