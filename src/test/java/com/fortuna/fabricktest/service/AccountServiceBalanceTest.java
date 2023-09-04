package com.fortuna.fabricktest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortuna.fabricktest.data.TransactionRepository;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.FabrickRestException;
import com.fortuna.fabricktest.exception.ServiceException;
import com.fortuna.fabricktest.service.account.AccountService;
import com.fortuna.fabricktest.service.account.AccountServiceI;
import com.fortuna.fabricktest.service.account.bean.AccountBalancePayload;
import com.fortuna.fabricktest.service.bean.FabrickError;
import com.fortuna.fabricktest.service.bean.FabrickResponse;


@ActiveProfiles("test")
@RestClientTest(AccountService.class)
class AccountServiceBalanceTest {

	@Autowired
    private AccountServiceI client;

	@Autowired
	private MockRestServiceServer server;
	
	@MockBean
    private TransactionRepository transactionRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String accountId = "123456";
	
	@Value( "${fabrick.url.domain}" )
	private String baseUrl;
	@Value( "${fabrick.url.accountBalance}" )
	private String endpoint;
	
	private String bodyAsJsonOk;
	private String bodyAsJsonFail;
	
	@Test
	void getAccountBalance() throws Exception {
		
		this.server.expect(requestTo(baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
        	.body(bodyAsJsonOk));
		
		AccountBalancePayload pl = client.getAccountBalance(accountId);
		
		server.verify();
		
		assertNotNull(pl);
		assertEquals(500l, pl.getAvailableBalance());
		assertEquals(400l, pl.getBalance());
		assertEquals(LocalDate.now(), pl.getDate());
		assertEquals("EUR", pl.getCurrency());
	}
	
	@Test
	void getAccountBalanceFail() throws Exception {
		
		this.server.expect(requestTo(baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.FORBIDDEN)
			.contentType(MediaType.APPLICATION_JSON)
        	.body(bodyAsJsonFail));
			
		FabrickRestException e = assertThrows(FabrickRestException.class, () -> {
            client.getAccountBalance(accountId);
		});
		
		server.verify();
		
		List<FabrickError> list = e.getErrors();
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("ERR001", list.get(0).getCode());
		assertEquals("Err Description", list.get(0).getDescription());
	}
	
	@Test
	void getBalanceServiceException() throws Exception {
		
		this.server.expect(requestTo(baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON));
			
		ServiceException e = assertThrows(ServiceException.class, () -> {
            client.getAccountBalance(accountId);
		});
		
		server.verify();
		
		EnumError err = e.getError();
		assertNotNull(err);
		assertEquals(EnumError.SERVICE, err);
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		
		FabrickResponse<AccountBalancePayload> body = new FabrickResponse<AccountBalancePayload>();
		AccountBalancePayload payload = new AccountBalancePayload();
		payload.setAvailableBalance(500l);
		payload.setCurrency("EUR");
		payload.setBalance(400l);
		payload.setDate(LocalDate.now());
		
		body.setPayload(payload);
		
		bodyAsJsonOk = objectMapper.writeValueAsString(body);
		
		List<FabrickError> errors = new ArrayList<>();
		
		FabrickError e = new FabrickError();
		
		e.setCode("ERR001");
		e.setDescription("Err Description");
		
		errors.add(e);
		
		body = new FabrickResponse<AccountBalancePayload>();
		body.setStatus("KO");
		body.setErrors(errors);
		
		bodyAsJsonFail = objectMapper.writeValueAsString(body);

	  }	
}
