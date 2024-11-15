package com.xische.exchangerate.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeResponseTest {

    @Test
    void testCurrencyExchangeResponse() {
        Map<String, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put("EUR", BigDecimal.valueOf(0.85));

        CurrencyExchangeResponse response = new CurrencyExchangeResponse();
        response.setResult("success");
        response.setDocumentation("https://example.com");
        response.setTermsOfUse("Use terms");
        response.setTimeLastUpdateUnix(123456789);
        response.setTimeLastUpdateUtc("2024-11-15T11:00:00Z");
        response.setTimeNextUpdateUnix(123456890);
        response.setTimeNextUpdateUtc("2024-11-16T11:00:00Z");
        response.setBaseCode("USD");
        response.setConversionRates(conversionRates);

        assertEquals("success", response.getResult());
        assertEquals("https://example.com", response.getDocumentation());
        assertEquals("Use terms", response.getTermsOfUse());
        assertEquals(123456789, response.getTimeLastUpdateUnix());
        assertEquals("2024-11-15T11:00:00Z", response.getTimeLastUpdateUtc());
        assertEquals(123456890, response.getTimeNextUpdateUnix());
        assertEquals("2024-11-16T11:00:00Z", response.getTimeNextUpdateUtc());
        assertEquals("USD", response.getBaseCode());
        assertEquals(conversionRates, response.getConversionRates());
    }

    @Test
    void testEquals() {
        Map<String, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put("EUR", BigDecimal.valueOf(0.85));

        CurrencyExchangeResponse response1 = new CurrencyExchangeResponse();
        response1.setResult("success");
        response1.setBaseCode("USD");
        response1.setConversionRates(conversionRates);

        CurrencyExchangeResponse response2 = new CurrencyExchangeResponse();
        response2.setResult("success");
        response2.setBaseCode("USD");
        response2.setConversionRates(conversionRates);

        assertEquals(response1, response2); // Testing equals
        assertEquals(response1.hashCode(), response2.hashCode()); // Testing hashCode
        assertTrue(response1.canEqual(response2)); // Testing canEqual
    }

    @Test
    void testNotEquals() {
        CurrencyExchangeResponse response1 = new CurrencyExchangeResponse();
        response1.setBaseCode("USD");

        CurrencyExchangeResponse response2 = new CurrencyExchangeResponse();
        response2.setBaseCode("EUR");

        assertNotEquals(response1, response2); // Testing inequality
    }
}