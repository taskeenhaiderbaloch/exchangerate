package com.xische.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Represents the response from the currency exchange API.
 */
@Data
public class CurrencyExchangeResponse {

    private String result;
    private String documentation;
    private String termsOfUse;

    @JsonProperty("time_last_update_unix")
    private long timeLastUpdateUnix;

    @JsonProperty("time_last_update_utc")
    private String timeLastUpdateUtc;

    @JsonProperty("time_next_update_unix")
    private long timeNextUpdateUnix;

    @JsonProperty("time_next_update_utc")
    private String timeNextUpdateUtc;

    @JsonProperty("base_code")
    private String baseCode;     // Base currency in the exchange rate response

    @JsonProperty("conversion_rates")
    private Map<String, BigDecimal> conversionRates; // Map of currency rates with target currencies
}