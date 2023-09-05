package com.fortuna.fabricktest.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.service.moneytransfer.MoneyTransferServiceI;
import com.fortuna.fabricktest.service.moneytransfer.bean.CreateMoneyTransferPayload;

@WebMvcTest(MoneyTransferController.class)
@ActiveProfiles("test")
class MoneyTransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MoneyTransferServiceI moneyTransferService;
	
	private String createMoneyTransferReqJsonOk;
	private String createMoneyTransferReqJsonFail;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void createMoneyTransferOk() throws Exception {
		
		this.mockMvc.perform(post("/moneytransfer/123456/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(createMoneyTransferReqJsonOk))
			.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.direction", is("out")))
			.andExpect(jsonPath("$.moneyTransferId", is("123")))
			.andExpect(jsonPath("$.status", is("ok")));
	}
	
	@Test
	void createMoneyTransferMissingBodyParameter() throws Exception {
		
		this.mockMvc.perform(post("/moneytransfer/123456/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(createMoneyTransferReqJsonFail))
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	void createMoneyTransferMissingAccountId() throws Exception {
		
		this.mockMvc.perform(post("/moneytransfer/123456/create"))
			.andExpect(status().is4xxClientError());
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		CreateMoneyTransferPayload payloadMock = new CreateMoneyTransferPayload();
		payloadMock.setDirection("out");
		payloadMock.setMoneyTransferId("123");
		payloadMock.setStatus("ok");
			
		String accountId = "123456";
		
		when(moneyTransferService.createMoneyTransfer(any(CreateMoneyTransferReq.class), 
				eq(accountId))).thenReturn(payloadMock);
	
		CreateMoneyTransferReq input = new CreateMoneyTransferReq();
		
		input.setAmount(500l);
		input.setCreditorIban("IT123");
		input.setCreditorName("credName");
		input.setCurrency(Currency.getInstance("EUR"));
		input.setDescription("ABC");
		input.setExecutionDate("2023-12-01");
		
		createMoneyTransferReqJsonOk = mapper.writeValueAsString(input);
		
		input.setCreditorName(null);
		
		createMoneyTransferReqJsonFail = mapper.writeValueAsString(input);
	  }
}
