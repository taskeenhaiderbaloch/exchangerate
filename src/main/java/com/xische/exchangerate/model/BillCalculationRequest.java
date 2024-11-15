package com.xische.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a request to calculate the payable bill after applying discounts
 * and currency conversion.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillCalculationRequest {

    private List<Item> items;     // List of items in the bill
    private User user;              // User details (e.g., employee, affiliate, etc.)
    private String originalCurrency; // Currency in which the bill is generated
    private String targetCurrency;  // Currency to which the bill needs to be converted
}
