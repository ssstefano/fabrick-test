package com.fortuna.fabricktest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fortuna.fabricktest.exception.FabrickRestException;
import com.fortuna.fabricktest.service.account.AccountServiceI;
import com.fortuna.fabricktest.service.bean.FabrickError;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
public class AccountControllerRestExceptionTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountServiceI accService;
	
	@Test
	public void getBalanceHandleException() throws Exception {
		
		this.mockMvc.perform(get("/account/123456/balance"))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].errorCode", is("ABC123")))
		.andExpect(jsonPath("$[0].errorMessage", is("Fabrick error")));
	}
	
	@BeforeEach
	public void setUp() {
		HttpStatusCode statusCode = HttpStatusCode.valueOf(403);
		List<FabrickError> errors = new ArrayList<>();
		
		FabrickError err = new FabrickError();
		err.setCode("ABC123");
		err.setDescription("Fabrick error");
		
		errors.add(err);
			
		when(accService.getAccountBalance("123456")).thenThrow(new FabrickRestException(statusCode, errors));
	  }
}
