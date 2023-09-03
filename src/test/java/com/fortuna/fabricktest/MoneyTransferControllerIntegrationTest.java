package com.fortuna.fabricktest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import com.fortuna.fabricktest.controller.MoneyTransferController;
import com.fortuna.fabricktest.controller.bean.CreateMoneyTransferReq;
import com.fortuna.fabricktest.controller.bean.ErrorRes;

@SpringBootTest
@ActiveProfiles("test")
@EnabledIf("test")
public class MoneyTransferControllerIntegrationTest {
	
	@Autowired
	private MoneyTransferController moneyTransferController;
	
	@Test
	public void createMoneyTransferFail() {
		
		
		CreateMoneyTransferReq moneyTransfer = new CreateMoneyTransferReq();
		moneyTransfer.setAmount(500l);
		moneyTransfer.setCreditorIban("IT123");
		moneyTransfer.setCreditorName("creditorName");
		moneyTransfer.setCurrency(Currency.getInstance("EUR"));
		moneyTransfer.setDescription("description");
		moneyTransfer.setExecutionDate("2020-01-10");
		
		ResponseEntity<?> entity = moneyTransferController.createMoneyTransfer(moneyTransfer, "14537780");
		
		assertNotNull(entity);
		assertNotNull(entity.getBody());
		assertEquals(entity.getStatusCode(), HttpStatusCode.valueOf(403));
		assertInstanceOf(List.class, entity.getBody());
		
		List<ErrorRes> errors = (List<ErrorRes>) entity.getBody();
 		
		assertEquals(errors.size(), 1);
		assertEquals(errors.get(0).getErrorCode(), "API000");
		assertEquals(errors.get(0).getErrorMessage(), "Errore tecnico La condizione BP049 non è prevista per il conto id 14537780");	
	}
	
}
