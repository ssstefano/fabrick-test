package com.fortuna.fabricktest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fortuna.fabricktest.controller.AccountController;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

	
	@Autowired
	private AccountController accController;
	
	@Test
	void contextLoads() {
		Assertions.assertThat(accController).isNotNull();
	}

}
