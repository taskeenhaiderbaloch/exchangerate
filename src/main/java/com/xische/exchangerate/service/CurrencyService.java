package com.xische.exchangerate.service;

import com.xische.exchangerate.client.CurrencyExchangeClient;
import com.xische.exchangerate.model.CurrencyExchangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyExchangeClient currencyExchangeClient;

    // Calculate the total price of the bill in the target currency
    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        CurrencyExchangeResponse response = fetchCurrencyRatesFromApi(baseCurrency);
        return response.getConversionRates().get(targetCurrency);
    }

    // Call to external API to fetch exchange rates
    @Cacheable(value = "currencyCache", key = "#baseCurrency")
    private CurrencyExchangeResponse fetchCurrencyRatesFromApi(String baseCurrency) {
        return currencyExchangeClient.getExchangeRates(baseCurrency);
    }

}
