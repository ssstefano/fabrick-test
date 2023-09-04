package com.fortuna.fabricktest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fortuna.fabricktest.service.account.AccountServiceI;
import com.fortuna.fabricktest.service.account.bean.AccountBalancePayload;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountServiceI accService;
	
	@Test
	void getBalanceOk() throws Exception {
		
		this.mockMvc.perform(get("/account/123456/balance"))
			.andExpect(status().isOk())
			.andExpect(content().string("500"));
	}
	
	@Test
	void getBalanceMissingParameter() throws Exception {
		
		this.mockMvc.perform(get("/account/balance"))
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	void getTransactionOk() throws Exception {
		
		this.mockMvc.perform(get("/account/123456/transactions")
			.queryParam("fromDate", "2019-01-01")
			.queryParam("toDate", "2019-12-01"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].description", is("ABC")))
			.andExpect(jsonPath("$[0].operationId", is("123")))
			.andExpect(jsonPath("$[0].transactionId", is("456")));
	}
	
	@Test
	void getTransactionMissingParameter() throws Exception {
		
		this.mockMvc.perform(get("/account/123456/transactions"))
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	void getTransactionWrongParameterFormat() throws Exception {
		
		this.mockMvc.perform(get("/account/123456/transactions")
			.queryParam("accountId", "123456")
			.queryParam("fromDate", "2019-01-")
			.queryParam("toDate", "2019-12-01"))
			.andExpect(status().is4xxClientError());
	}
	
	@BeforeEach
	public void setUp() {
		AccountBalancePayload balanceMock = new AccountBalancePayload();
		balanceMock.setAvailableBalance(300l);
		balanceMock.setBalance(500l);
		balanceMock.setCurrency("EUR");			
		balanceMock.setDate(LocalDate.of(2023, 1, 1));
			
		String accountId = "123456";
	
		when(accService.getAccountBalance(accountId)).thenReturn(balanceMock);
		
		LocalDate fromDate = LocalDate.parse("2019-01-01");
		LocalDate toDate = LocalDate.parse("2019-12-01");
		
		TransactionPayload transactionMock = new TransactionPayload();
		
		TransactionPayload.Transaction t = new TransactionPayload.Transaction();
		t.setAccountingDate(LocalDate.parse("2019-02-02"));
		t.setCurrency(Currency.getInstance("EUR"));
		t.setDescription("ABC");
		t.setOperationId("123");
		t.setTransactionId("456");
		t.setValueDate(LocalDate.parse("2019-02-03"));
		
		List<TransactionPayload.Transaction> list = new ArrayList<>();
		list.add(t);
		
		transactionMock.setList(list);
		
		when(accService.getAccountTransactionsAndSave(accountId, fromDate, toDate)).thenReturn(transactionMock);
	  }
}
