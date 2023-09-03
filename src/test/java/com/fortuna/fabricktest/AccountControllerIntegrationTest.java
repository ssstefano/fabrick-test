package com.fortuna.fabricktest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.fortuna.fabricktest.controller.AccountController;
import com.fortuna.fabricktest.controller.bean.ErrorRes;
import com.fortuna.fabricktest.enums.EnumError;
import com.fortuna.fabricktest.service.account.bean.TransactionPayload.Transaction;

@SpringBootTest
@ActiveProfiles("test")
@EnabledIf("test")
public class AccountControllerIntegrationTest {
	
	@Autowired
	private AccountController accountController;
	
	@Test
	public void getBalanceOk() {
		ResponseEntity<Long> entity = accountController.balance("14537780");
		
		assertNotNull(entity);
		assertEquals(entity.getBody(), 0l);
		assertEquals(entity.getStatusCode(), HttpStatusCode.valueOf(200));
	}
	
	@Test
	public void getBalanceFail() {
		ResponseEntity<?> entity = accountController.balance("1");
		
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(entity.getStatusCode(), HttpStatusCode.valueOf(403));
		assertInstanceOf(List.class, entity.getBody());
		
		List<ErrorRes> errors = (List<ErrorRes>) entity.getBody();
 		
		assertEquals(errors.size(), 1);
		assertEquals(errors.get(0).getErrorCode(), "");
		assertEquals(errors.get(0).getErrorMessage(), "");	
	}
	
	@Test
	public void getTransactionsOk() {
		ResponseEntity<List<Transaction>> entity = accountController.transactions("14537780", "2019-01-01", "2019-12-01");
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(entity.getStatusCode(), HttpStatusCode.valueOf(200));	
	}
	
	@Test
	public void getTransactionsFail() {
		ResponseEntity<?> entity = accountController.transactions("14537780", "2019-01-12", "2019-12-01");
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(entity.getStatusCode(), HttpStatusCode.valueOf(500));
		assertInstanceOf(List.class, entity.getBody());
		
		List<ErrorRes> errors = (List<ErrorRes>) entity.getBody();
 		
		assertEquals(errors.size(), 1);
		assertEquals(errors.get(0).getErrorCode(), EnumError.SERVICE_TRANSACTION.getErrorCode());
		assertEquals(errors.get(0).getErrorMessage(), EnumError.SERVICE_TRANSACTION.getErrorMessage());	
	}
	
}
