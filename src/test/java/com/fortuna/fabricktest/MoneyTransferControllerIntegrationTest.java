package com.fortuna.fabricktest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.controller.bean.ErrorRes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MoneyTransferControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value(value="${local.server.port}")
	private int port;
	
	private String accountId = "14537780";
	
	@Test
	public void createMoneyTransferFail() throws Exception {
		
		CreateMoneyTransferReq moneyTransfer = new CreateMoneyTransferReq();
		moneyTransfer.setAmount(1l);
		moneyTransfer.setCreditorIban("IT68X0100503215000000011730");
		moneyTransfer.setCreditorName("creditorName");
		moneyTransfer.setCurrency(Currency.getInstance("EUR"));
		moneyTransfer.setDescription("description");
		moneyTransfer.setExecutionDate("2023-09-10");
		
		String url = "http://localhost:" + port + "/moneytransfer/"+accountId+"/create?accountId";
		
		ResponseEntity<String> entity = restTemplate.postForEntity(url, moneyTransfer, String.class);
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
	
		assertTrue(entity.getStatusCode().isError());
		
		TypeReference<List<ErrorRes>> typeRef = 
				new TypeReference<List<ErrorRes>>() {};
 		
		List<ErrorRes> errors = mapper.readValue(entity.getBody(), typeRef);
 		
		assertEquals(1, errors.size());
	//	assertEquals(errors.get(0).getErrorCode(), "API000");
	//	assertEquals(errors.get(0).getErrorMessage(), "Errore nel recuperare le informazioni da database o dati di input non corretti  : IBTS-0081");	
	}
	
}
