package com.xische.exchangerate.client;

import com.xische.exchangerate.model.CurrencyExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currencyExchange", url = "https://v6.exchangerate-api.com/v6/${exchange.rate.api.key}")
public interface CurrencyExchangeClient {

    @GetMapping("/latest/{base_currency}")
    CurrencyExchangeResponse getExchangeRates(@PathVariable("base_currency") String baseCurrency);
}