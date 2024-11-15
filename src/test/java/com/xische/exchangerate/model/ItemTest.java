package com.xische.exchangerate.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testItem() {
        Item item = new Item("Laptop", BigDecimal.valueOf(1000), "electronics", 2);

        assertEquals("Laptop", item.getName());
        assertEquals(BigDecimal.valueOf(1000), item.getPrice());
        assertEquals("electronics", item.getCategory());
        assertEquals(2, item.getQuantity());
    }

    @Test
    void testItemSetters() {
        Item item = new Item();

        item.setName("Phone");
        item.setPrice(BigDecimal.valueOf(500));
        item.setCategory("electronics");
        item.setQuantity(1);

        assertEquals("Phone", item.getName());
        assertEquals(BigDecimal.valueOf(500), item.getPrice());
        assertEquals("electronics", item.getCategory());
        assertEquals(1, item.getQuantity());
    }

    @Test
    void testCalculateTotalPrice() {
        Item item = new Item("Tablet", BigDecimal.valueOf(250), "electronics", 3);

        BigDecimal expectedTotal = BigDecimal.valueOf(750);
        assertEquals(expectedTotal, item.calculateTotalPrice());
    }

    @Test
    void testItemEquality() {
        Item item1 = new Item("Laptop", BigDecimal.valueOf(1000), "electronics", 1);
        Item item2 = new Item("Laptop", BigDecimal.valueOf(1000), "electronics", 1);

        assertEquals(item1, item2); // Testing equals
        assertEquals(item1.hashCode(), item2.hashCode()); // Testing hashCode
        assertTrue(item1.canEqual(item2)); // Testing canEqual
    }

    @Test
    void testNotEquals() {
        Item item1 = new Item("Laptop", BigDecimal.valueOf(1000), "electronics", 1);
        Item item2 = new Item("Tablet", BigDecimal.valueOf(500), "electronics", 1);

        assertNotEquals(item1, item2); // Testing inequality
    }

}