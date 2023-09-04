package com.fortuna.fabricktest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortuna.fabricktest.controller.bean.ErrorRes;
import com.fortuna.fabricktest.data.entity.TransactionEntity;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.service.account.AccountService;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload.Transaction;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccountControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value(value="${local.server.port}")
	private int port;
	
	private String accountIdOk = "14537780";
	private String accountIdFail = "1";
	private String fromDate = "2019-01-01";
	private String toDate = "2019-12-01";
	
	private String transactionDescription = "PD VISA CORPORATE 10";
	
	@Autowired
	AccountService accountService;
	
	@Test
	public void getBalanceOk() {
		
		String url = "http://localhost:" + port + "/account/"+accountIdOk+"/balance";
		
		ResponseEntity<Long> entity = this.restTemplate.getForEntity(url, Long.class);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(HttpStatusCode.valueOf(200), entity.getStatusCode());
		
		Long balance = entity.getBody();
		assertTrue(balance > 0);
	}
	
	@Test
	public void getBalanceFail() throws Exception {

		String url = "http://localhost:" + port + "/account/"+accountIdFail+"/balance";
		
		ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(HttpStatusCode.valueOf(403), entity.getStatusCode());
		
		TypeReference<List<ErrorRes>> typeRef = 
				new TypeReference<List<ErrorRes>>() {};
 		
		List<ErrorRes> errors = mapper.readValue(entity.getBody(), typeRef);
		
		assertEquals(1, errors.size());
		assertEquals("REQ004", errors.get(0).getErrorCode());
		assertEquals("Invalid account identifier", errors.get(0).getErrorMessage());	
	}
	
	@Test
	public void getTransactionsOk() throws Exception {
		
		String url = "http://localhost:" + port + "/account/"+accountIdOk+"/transactions?fromDate="+fromDate+"&toDate="+toDate;
		
		ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(HttpStatusCode.valueOf(200), entity.getStatusCode());	
		
		TypeReference<List<Transaction>> typeRef = 
				new TypeReference<List<Transaction>>() {};
 		
		List<Transaction> transactions = mapper.readValue(entity.getBody(), typeRef);
		
		assertNotNull(transactions);
		assertTrue(transactions.size() > 0);
		
		List<TransactionEntity> entities = accountService.getAccountLocalTransactionsByDescr(transactionDescription);
		assertNotNull(entities);
		assertEquals(1, entities.size());
		
		assertEquals(transactionDescription, entities.get(0).getDescription());
		assertEquals("282831", entities.get(0).getTransactionId());
	}
	
	@Test
	public void getTransactionsFail() throws Exception{
		
		String url = "http://localhost:" + port + "/account/"+accountIdOk+"/transactions?fromDate="+toDate+"&toDate="+fromDate;
		
		ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(HttpStatusCode.valueOf(500), entity.getStatusCode());
		
		TypeReference<List<ErrorRes>> typeRef = 
				new TypeReference<List<ErrorRes>>() {};
 		
		List<ErrorRes> errors = mapper.readValue(entity.getBody(), typeRef);
 		
		assertEquals(1, errors.size());
		assertEquals(EnumError.SERVICE_TRANSACTION.getErrorCode(), errors.get(0).getErrorCode());
		assertEquals(EnumError.SERVICE_TRANSACTION.getErrorMessage(), errors.get(0).getErrorMessage());	
	}
}
