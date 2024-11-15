package com.xische.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExchangeRateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testExchangeRateApplication() {
		ExchangeRateApplication exchangeRateApplication = new ExchangeRateApplication();

		assertNotNull(exchangeRateApplication);
	}

	@Test
	void testExchangeRateApplicationMain() {
		String[] args = {"arg1", "arg2"};
		ExchangeRateApplication.main(args);
	}

}
