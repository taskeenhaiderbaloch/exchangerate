package com.xische.exchangerate.service;

import com.xische.exchangerate.model.Item;
import com.xische.exchangerate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    // Static discount values
    public static final BigDecimal EMPLOYEE_DISCOUNT = new BigDecimal("0.30");  // 30% discount for employees
    public static final BigDecimal AFFILIATE_DISCOUNT = new BigDecimal("0.10"); // 10% discount for affiliates
    public static final BigDecimal LOYAL_CUSTOMER_DISCOUNT = new BigDecimal("0.05"); // 5% discount for customers of 2+ years
    public static final BigDecimal BILL_OVER_100_DISCOUNT = new BigDecimal("5.00"); // $5 discount for every $100 bill

    // Method to calculate discount based on user and bill conditions
    public BigDecimal calculateDiscount(User user, List<Item> items, BigDecimal totalAmount) {
        BigDecimal discount = BigDecimal.ZERO;

        if (items != null && !items.isEmpty()) {
            // Check if any item is a grocery item
            boolean isGrocery = items.stream().anyMatch(item -> item.getCategory().equalsIgnoreCase("grocery"));

            // Calculate discount based on user type and bill conditions
            if (!isGrocery) {
                if ("employee".equals(user.getUserType())) {
                    discount = totalAmount.multiply(EMPLOYEE_DISCOUNT);
                } else if ("affiliate".equals(user.getUserType())) {
                    discount = totalAmount.multiply(AFFILIATE_DISCOUNT);
                } else if (user.getCustomerTenureYears() > 2) {
                    discount = totalAmount.multiply(LOYAL_CUSTOMER_DISCOUNT);
                }
            }
        }

        // Apply discount for every $100 on the bill
        BigDecimal billDiscount = totalAmount.divide(BigDecimal.valueOf(100), 0, BigDecimal.ROUND_DOWN).multiply(BILL_OVER_100_DISCOUNT);
        return discount.add(billDiscount);
    }
}
