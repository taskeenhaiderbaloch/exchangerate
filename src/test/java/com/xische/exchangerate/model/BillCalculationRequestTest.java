package com.xische.exchangerate.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillCalculationRequestTest {

    @Test
    void testBillCalculationRequest() {
        User user = new User("employee", 5);
        List<Item> items = Arrays.asList(new Item("Laptop", BigDecimal.valueOf(1000), "electronics", 1));
        String originalCurrency = "USD";
        String targetCurrency = "EUR";

        BillCalculationRequest request = new BillCalculationRequest(items, user, originalCurrency, targetCurrency);

        assertEquals(items, request.getItems());
        assertEquals(user, request.getUser());
        assertEquals("USD", request.getOriginalCurrency());
        assertEquals("EUR", request.getTargetCurrency());
    }

    @Test
    void testBillCalculationRequestSetters() {
        BillCalculationRequest request = new BillCalculationRequest();

        User user = new User("affiliate", 3);
        List<Item> items = Arrays.asList(new Item("Phone", BigDecimal.valueOf(500), "electronics", 2));

        request.setItems(items);
        request.setUser(user);
        request.setOriginalCurrency("GBP");
        request.setTargetCurrency("INR");

        assertEquals(items, request.getItems());
        assertEquals(user, request.getUser());
        assertEquals("GBP", request.getOriginalCurrency());
        assertEquals("INR", request.getTargetCurrency());
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