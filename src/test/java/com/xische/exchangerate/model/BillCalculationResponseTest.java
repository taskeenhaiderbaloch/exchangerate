package com.xische.exchangerate.model;

import com.xische.exchangerate.model.BillCalculationResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillCalculationResponseTest {

    @Test
    void testBillCalculationResponse() {
        BillCalculationResponse response = new BillCalculationResponse(BigDecimal.valueOf(1500), "EUR");

        assertEquals(BigDecimal.valueOf(1500), response.getFinalAmount());
        assertEquals("EUR", response.getTargetCurrency());
    }

    @Test
    void testBillCalculationResponseSetters() {
        BillCalculationResponse response = new BillCalculationResponse();

        response.setFinalAmount(BigDecimal.valueOf(2000));
        response.setTargetCurrency("USD");

        assertEquals(BigDecimal.valueOf(2000), response.getFinalAmount());
        assertEquals("USD", response.getTargetCurrency());
    }

    @Test
    void testEquals() {
        User user = new User("employee", 5);
        List<Item> items = Arrays.asList(new Item("Laptop", BigDecimal.valueOf(1000), "electronics", 1));
        BillCalculationRequest request1 = new BillCalculationRequest(items, user, "USD", "EUR");
        BillCalculationRequest request2 = new BillCalculationRequest(items, user, "USD", "EUR");

        assertEquals(request1, request2); // Testing equals
        assertEquals(request1.hashCode(), request2.hashCode()); // Testing hashCode
        assertTrue(request1.canEqual(request2)); // Testing canEqual
    }

    @Test
    void testNotEquals() {
        BillCalculationRequest request1 = new BillCalculationRequest();
        BillCalculationRequest request2 = new BillCalculationRequest();

        request1.setOriginalCurrency("USD");
        request2.setOriginalCurrency("EUR");

        assertNotEquals(request1, request2); // Testing inequality
    }
}