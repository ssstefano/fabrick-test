package com.fortuna.fabricktest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fortuna.fabricktest.service.account.AccountServiceI;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
public class AccountControllerRuntimeExceptionTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountServiceI accService;
	
	@Test
	public void getBalanceHandleException() throws Exception {
		
		this.mockMvc.perform(get("/account/123456/balance"))
		.andExpect(status().is5xxServerError())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].errorCode", is("R0001")))
		.andExpect(jsonPath("$[0].errorMessage", is("Internal server error")));
	}
	
	@BeforeEach
	public void setUp() {
		when(accService.getAccountBalance("123456")).thenThrow(new NullPointerException());
	}
}
