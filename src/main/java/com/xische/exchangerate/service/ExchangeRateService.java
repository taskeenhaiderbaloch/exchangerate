package com.xische.exchangerate.service;

import com.xische.exchangerate.model.BillCalculationRequest;
import com.xische.exchangerate.model.BillCalculationResponse;
import com.xische.exchangerate.model.Item;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service to handle exchange rate calculations for the bill and apply discounts.
 */
@Service
@AllArgsConstructor
public class ExchangeRateService {

    private final CurrencyService currencyService;
    private final DiscountService discountService;


    /**
     * Calculate the final amount for the bill after applying discounts and currency conversion.
     *
     * @param request The bill calculation request containing user, items, and currency info.
     * @return The response containing the final calculated amount in the target currency.
     */
    public BillCalculationResponse calculateFinalAmount(BillCalculationRequest request) {
        // Calculate the total amount for all items in the bill
        BigDecimal totalAmount = calculateTotalBillAmount(request.getItems());

        // Apply discounts based on user type and bill conditions
        BigDecimal discount = discountService.calculateDiscount(request.getUser(), request.getItems(), totalAmount);

        // Apply the discount to the total amount
        BigDecimal discountedAmount = totalAmount.subtract(discount);

        // Fetch the exchange rate from the currency service
        BigDecimal exchangeRate = currencyService.getExchangeRate(request.getOriginalCurrency(), request.getTargetCurrency());

        // Convert the discounted amount to the target currency
        BigDecimal finalAmountInTargetCurrency = discountedAmount.multiply(exchangeRate);

        // Create the response with the final calculated amount
        return new BillCalculationResponse(finalAmountInTargetCurrency, request.getTargetCurrency());
    }

    /**
     * Calculate the total amount for all items in the bill.
     *
     * @param items List of items in the bill.
     * @return The total amount of the bill.
     */
    private BigDecimal calculateTotalBillAmount(List<Item> items) {
        return items.stream()
                .map(Item::calculateTotalPrice) // Calculate total price per item
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all item prices
    }
}