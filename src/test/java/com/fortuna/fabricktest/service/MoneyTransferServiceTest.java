package com.fortuna.fabricktest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.FabrickRestException;
import com.fortuna.fabricktest.exception.ServiceException;
import com.fortuna.fabricktest.service.bean.FabrickError;
import com.fortuna.fabricktest.service.bean.FabrickResponse;
import com.fortuna.fabricktest.service.moneytransfer.MoneyTransferService;
import com.fortuna.fabricktest.service.moneytransfer.MoneyTransferServiceI;
import com.fortuna.fabricktest.service.moneytransfer.bean.Account;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferPayload;
import com.fortuna.fabricktest.service.moneytransfer.bean.Creditor;


@ActiveProfiles("test")
@RestClientTest(MoneyTransferService.class)
class MoneyTransferServiceTest {

	@Autowired
    private MoneyTransferServiceI client;

	@Autowired
	private MockRestServiceServer server;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String accountId = "123456";
	
	@Value( "${fabrick.url.domain}" )
	private String baseUrl;
	@Value( "${fabrick.url.createMoneyTransfer}" )
	private String endpoint;
	
	private String bodyAsJsonOk;
	private String bodyAsJsonFail;
	
	private CreateMoneyTransferReq moneyTransfer;
	
	@Test
	void createMoneyTransferOk() throws Exception {
		
		this.server.expect(requestTo(baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
        	.body(bodyAsJsonOk));
		
		CreateMoneyTransferPayload pl = client.createMoneyTransfer(moneyTransfer, accountId);
		
		server.verify();
		
		assertNotNull(pl);
		assertNotNull(pl.getCreditor());
		assertNotNull(pl.getCreditor().getAccount());
		assertEquals("creditorName", pl.getCreditor().getName());
		assertEquals("IT123", pl.getCreditor().getAccount().getAccountCode());
		assertEquals("OUT", pl.getDirection());
		assertEquals("OK", pl.getStatus());
		
	}
	
	@Test
	void createMoneyTransferFabrickException() throws Exception {
		
		this.server.expect(requestTo(baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withStatus(HttpStatus.FORBIDDEN)
			.contentType(MediaType.APPLICATION_JSON)
        	.body(bodyAsJsonFail));
			
		FabrickRestException e = assertThrows(FabrickRestException.class, () -> {
			client.createMoneyTransfer(moneyTransfer, accountId);
		});
		
		server.verify();
		
		List<FabrickError> list = e.getErrors();
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("ERR001", list.get(0).getCode());
		assertEquals("Err Description", list.get(0).getDescription());
	}
	
	@Test
	void createMoneyTransferServiceException() throws Exception {
		
		this.server.expect(requestTo(baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON));
			
		ServiceException e = assertThrows(ServiceException.class, () -> {
			client.createMoneyTransfer(moneyTransfer, accountId);
		});
		
		server.verify();
		
		EnumError err = e.getError();
		assertNotNull(err);
		assertEquals(EnumError.SERVICE, err);
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		
		moneyTransfer = new CreateMoneyTransferReq();
		moneyTransfer.setAmount(500l);
		moneyTransfer.setCreditorIban("IT123");
		moneyTransfer.setCreditorName("creditorName");
		moneyTransfer.setCurrency(Currency.getInstance("EUR"));
		moneyTransfer.setDescription("description");
		moneyTransfer.setExecutionDate("2020-01-10");
		
		CreateMoneyTransferPayload pl = new CreateMoneyTransferPayload();
		
		Account acc = new Account("IT123");
	
		Creditor cred = new Creditor(acc, "creditorName");
		
		pl.setCreditor(cred);
		
		pl.setDirection("OUT");
		pl.setMoneyTransferId("456");
		pl.setStatus("OK");
		
		FabrickResponse<CreateMoneyTransferPayload> body = new FabrickResponse<CreateMoneyTransferPayload>();
		body.setPayload(pl);
		
		bodyAsJsonOk = objectMapper.writeValueAsString(body);
		
		List<FabrickError> errors = new ArrayList<>();
		
		FabrickError e = new FabrickError();
		
		e.setCode("ERR001");
		e.setDescription("Err Description");
		
		errors.add(e);
		
		body = new FabrickResponse<CreateMoneyTransferPayload>();
		body.setStatus("KO");
		body.setErrors(errors);
		
		bodyAsJsonFail = objectMapper.writeValueAsString(body);

	  }	
}
