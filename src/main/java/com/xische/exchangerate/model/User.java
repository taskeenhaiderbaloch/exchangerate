package com.xische.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the user making the request with their details such as user type and tenure.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userType;         // Type of user (e.g., employee, affiliate, regular)
    private int customerTenureYears; // Number of years the user has been a customer
}