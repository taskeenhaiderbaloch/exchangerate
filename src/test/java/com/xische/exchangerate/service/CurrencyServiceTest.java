package com.xische.exchangerate.service;

import com.xische.exchangerate.client.CurrencyExchangeClient;
import com.xische.exchangerate.model.CurrencyExchangeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {

    @Mock
    private CurrencyExchangeClient currencyExchangeClient;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExchangeRate_ValidCurrencies_ShouldReturnCorrectRate() {
        // Arrange
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        BigDecimal expectedRate = new BigDecimal("0.85");

        CurrencyExchangeResponse mockResponse = new CurrencyExchangeResponse();
        mockResponse.setConversionRates(Map.of(targetCurrency, expectedRate));
        when(currencyExchangeClient.getExchangeRates(baseCurrency)).thenReturn(mockResponse);

        // Act
        BigDecimal exchangeRate = currencyService.getExchangeRate(baseCurrency, targetCurrency);

        // Assert
        assertNotNull(exchangeRate);
        assertEquals(expectedRate, exchangeRate);
        verify(currencyExchangeClient, times(1)).getExchangeRates(baseCurrency);
    }

    @Test
    public void testGetExchangeRate_InvalidCurrency_ShouldReturnNull() {
        // Arrange
        String baseCurrency = "USD";
        String targetCurrency = "XYZ"; // Invalid target currency
        CurrencyExchangeResponse mockResponse = new CurrencyExchangeResponse();
        mockResponse.setConversionRates(Map.of());
        when(currencyExchangeClient.getExchangeRates(baseCurrency)).thenReturn(mockResponse);

        // Act
        BigDecimal exchangeRate = currencyService.getExchangeRate(baseCurrency, targetCurrency);

        // Assert
        assertNull(exchangeRate);
        verify(currencyExchangeClient, times(1)).getExchangeRates(baseCurrency);
    }

    @Test
    public void testGetExchangeRate_ExternalApiError_ShouldReturnNull() {
        // Arrange
        String baseCurrency = "USD";
        String targetCurrency = "EUR";

        when(currencyExchangeClient.getExchangeRates(baseCurrency)).thenThrow(new RuntimeException("API error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> currencyService.getExchangeRate(baseCurrency, targetCurrency));
        verify(currencyExchangeClient, times(1)).getExchangeRates(baseCurrency); // Ensure API call was attempted
    }
}