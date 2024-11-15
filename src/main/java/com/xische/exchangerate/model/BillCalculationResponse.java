package com.xische.exchangerate.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the response for the bill calculation, including the final amount in target currency.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillCalculationResponse {

    private BigDecimal finalAmount; // Final calculated amount after discount and conversion
    private String targetCurrency; // Currency in which the final amount is calculated
}