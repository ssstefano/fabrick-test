package com.fortuna.fabricktest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
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
import com.fortuna.fabricktest.service.account.bean.TransactionPayload;
import com.fortuna.fabricktest.service.bean.FabrickError;
import com.fortuna.fabricktest.service.bean.FabrickResponse;


@ActiveProfiles("test")
@RestClientTest(AccountService.class)
class AccountServiceTransactionTest {

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
	@Value( "${fabrick.url.transactions}" )
	private String endpoint;
	
	private String bodyAsJsonOk;
	private String bodyAsJsonFail;
	
	@Test
	void getTransactionsOk() throws Exception {
		
		LocalDate from = LocalDate.now();
		LocalDate to = LocalDate.now();
		
		String url = baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)+"?fromAccountingDate="+from+"&toAccountingDate="+to;
		
		this.server.expect(requestTo(url))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
        	.body(bodyAsJsonOk));
		
		TransactionPayload pl = client.getAccountTransactions(accountId, from, to);
		
		server.verify();
		
		assertNotNull(pl);
		assertNotNull(pl.getList());
		assertEquals(pl.getList().size(), 1);
		
		TransactionPayload.Transaction t = pl.getList().get(0);
		
		assertEquals("123", t.getOperationId());
		assertEquals("456", t.getTransactionId());
		assertEquals("description", t.getDescription());
		assertEquals(Currency.getInstance("EUR"), t.getCurrency());
		assertEquals(LocalDate.now(), t.getValueDate());
		assertEquals(LocalDate.now(), t.getAccountingDate());
	}
	
	@Test
	void getTransactionsFabrickException() throws Exception {
		
		LocalDate from = LocalDate.now();
		LocalDate to = LocalDate.now();
		
		String url = baseUrl+endpoint.replaceFirst("\\{accountId\\}", accountId)+"?fromAccountingDate="+from+"&toAccountingDate="+to;
		
		this.server.expect(requestTo(url))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.FORBIDDEN)
			.contentType(MediaType.APPLICATION_JSON)
        	.body(bodyAsJsonFail));
			
		FabrickRestException e = assertThrows(FabrickRestException.class, () -> {
            client.getAccountTransactions(accountId, from, to);
		});
		
		server.verify();
		
		List<FabrickError> list = e.getErrors();
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("ERR001", list.get(0).getCode());
		assertEquals("Err Description", list.get(0).getDescription());
	}
	
	@Test
	void getTransactionsServiceException() throws Exception {
		
		LocalDate from = LocalDate.now().plusDays(1);
		LocalDate to = LocalDate.now();
			
		ServiceException e = assertThrows(ServiceException.class, () -> {
            client.getAccountTransactions(accountId, from, to);
		});
		
		EnumError err = e.getError();
		assertNotNull(err);
		assertEquals(EnumError.SERVICE_TRANSACTION, err);
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		
		TransactionPayload.Transaction t = new TransactionPayload.Transaction();
		
		t.setAccountingDate(LocalDate.now());
		t.setCurrency(Currency.getInstance("EUR"));
		t.setDescription("description");
		t.setOperationId("123");
		t.setTransactionId("456");
		t.setValueDate(LocalDate.now());
		
		List<TransactionPayload.Transaction> list = new ArrayList<>();
		list.add(t);
		
		TransactionPayload payload = new TransactionPayload();
		payload.setList(list);
		
		FabrickResponse<TransactionPayload> body = new FabrickResponse<TransactionPayload>();
		body.setPayload(payload);
		
		
		bodyAsJsonOk = objectMapper.writeValueAsString(body);
		
		List<FabrickError> errors = new ArrayList<>();
		
		FabrickError e = new FabrickError();
		
		e.setCode("ERR001");
		e.setDescription("Err Description");
		
		errors.add(e);
		
		body = new FabrickResponse<TransactionPayload>();
		body.setStatus("KO");
		body.setErrors(errors);
		
		bodyAsJsonFail = objectMapper.writeValueAsString(body);

	  }	
}
