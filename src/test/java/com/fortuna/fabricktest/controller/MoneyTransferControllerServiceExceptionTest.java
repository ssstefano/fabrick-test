package com.fortuna.fabricktest.controller;

import static org.hamcrest.Matchers.hasSize;
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
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.exception.ServiceException;
import com.fortuna.fabricktest.service.moneytransfer.MoneyTransferServiceI;

@WebMvcTest(MoneyTransferController.class)
@ActiveProfiles("test")
public class MoneyTransferControllerServiceExceptionTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MoneyTransferServiceI moneyTransferService;
	
	@Autowired
	private ObjectMapper mapper;
	
	private String createMoneyTransferReqJson;
	
	@Test
	public void createMoneyTransferHandleException() throws Exception {
		
		this.mockMvc.perform(post("/moneytransfer/123456/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createMoneyTransferReqJson))
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].errorCode", is(EnumError.SERVICE.getErrorCode())))
				.andExpect(jsonPath("$[0].errorMessage", is(EnumError.SERVICE.getErrorMessage())));
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		when(moneyTransferService.createMoneyTransfer(any(CreateMoneyTransferReq.class), 
				eq("123456"))).thenThrow(new ServiceException(EnumError.SERVICE));
		
		CreateMoneyTransferReq input = new CreateMoneyTransferReq();
		
		input.setAmount(500l);
		input.setCreditorIban("IT123");
		input.setCreditorName("credName");
		input.setCurrency(Currency.getInstance("EUR"));
		input.setDescription("ABC");
		input.setExecutionDate("2023-12-01");
		
		ObjectMapper mapper = new ObjectMapper();
		
		createMoneyTransferReqJson = mapper.writeValueAsString(input);
	}
}
