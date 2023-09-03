package com.fortuna.fabricktest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fortuna.fabricktest.controller.AccountController;
import com.fortuna.fabricktest.controller.MoneyTransferController;
import com.fortuna.fabricktest.service.account.AccountServiceI;
import com.fortuna.fabricktest.service.moneytransfer.MoneyTransferServiceI;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

	@Autowired
	private AccountController accController;
	@Autowired
	private MoneyTransferController moneyTransferController;
	@Autowired
	private AccountServiceI accService;
	@Autowired
	private MoneyTransferServiceI moneyTransferService;
	
	@Test
	void contextLoads() {
		Assertions.assertThat(accController).isNotNull();
		Assertions.assertThat(moneyTransferController).isNotNull();
		Assertions.assertThat(accService).isNotNull();
		Assertions.assertThat(moneyTransferService).isNotNull();
	}

}
