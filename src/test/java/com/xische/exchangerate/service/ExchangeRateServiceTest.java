package com.xische.exchangerate.service;

import com.xische.exchangerate.model.BillCalculationRequest;
import com.xische.exchangerate.model.BillCalculationResponse;
import com.xische.exchangerate.model.Item;
import com.xische.exchangerate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for ExchangeRateService.
 */
class ExchangeRateServiceTest {

    @Mock
    private CurrencyService currencyService;
    @Mock
    private DiscountService discountService;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateFinalAmount_withValidRequest_shouldReturnExpectedResponse() {
        // Arrange
        List<Item> items = Arrays.asList(
                new Item("item1", BigDecimal.valueOf(100), "electronics", 2), // Electronics category
                new Item("item2", BigDecimal.valueOf(50), "grocery", 1)       // Grocery category
        );
        User user = new User("employee", 5); // User with "employee" type and 5 years tenure
        BillCalculationRequest request = new BillCalculationRequest(items, user, "USD", "EUR");

        BigDecimal expectedTotalAmount = BigDecimal.valueOf(250); // (100*2) + (50*1)
        BigDecimal expectedDiscount = BigDecimal.valueOf(50);     // Assume DiscountService gives a 50 discount
        BigDecimal expectedExchangeRate = BigDecimal.valueOf(1.1); // Assume 1 USD = 1.1 EUR

        when(currencyService.getExchangeRate("USD", "EUR")).thenReturn(expectedExchangeRate);
        when(discountService.calculateDiscount(user, items, expectedTotalAmount)).thenReturn(expectedDiscount);

        // Act
        BillCalculationResponse response = exchangeRateService.calculateFinalAmount(request);

        // Assert
        BigDecimal expectedFinalAmount = BigDecimal.valueOf(200).multiply(expectedExchangeRate); // (250 - 50) * 1.1
        assertEquals(expectedFinalAmount, response.getFinalAmount());
        assertEquals("EUR", response.getTargetCurrency());
    }

    @Test
    void calculateFinalAmount_withEmptyItems_shouldReturnZeroAmount() {
        // Arrange
        List<Item> items = Arrays.asList(); // No items
        User user = new User("affiliate", 2); // User with "affiliate" type and 2 years tenure
        BillCalculationRequest request = new BillCalculationRequest(items, user, "USD", "EUR");

        BigDecimal expectedExchangeRate = BigDecimal.valueOf(1.1); // Assume 1 USD = 1.1 EUR

        when(currencyService.getExchangeRate("USD", "EUR")).thenReturn(expectedExchangeRate);
        when(discountService.calculateDiscount(user, items, BigDecimal.ZERO)).thenReturn(BigDecimal.ZERO);

        // Act
        BillCalculationResponse response = exchangeRateService.calculateFinalAmount(request);

        // Assert
        assertEquals(new BigDecimal("0.0"), response.getFinalAmount());
        assertEquals("EUR", response.getTargetCurrency());

        // Verify interactions
        verify(currencyService, times(1)).getExchangeRate("USD", "EUR");
    }
}