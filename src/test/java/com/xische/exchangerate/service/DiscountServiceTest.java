package com.xische.exchangerate.service;

import com.xische.exchangerate.model.Item;
import com.xische.exchangerate.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountServiceTest {

    @InjectMocks
    private DiscountService discountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateDiscount_EmployeeUser_ShouldApplyEmployeeDiscount() {
        // Arrange
        User employee = new User("employee", 0);
        Item item1 = new Item("item1", new BigDecimal("50"), "non-grocery", 1);
        Item item2 = new Item("item2", new BigDecimal("60"), "non-grocery", 1);
        BigDecimal totalAmount = item1.calculateTotalPrice().add(item2.calculateTotalPrice());

        // Act
        BigDecimal discount = discountService.calculateDiscount(employee, Arrays.asList(item1, item2), totalAmount);

        // Assert
        BigDecimal expectedDiscount = totalAmount.multiply(new BigDecimal("0.30")); // 30% of $110
        assertEquals(expectedDiscount.add(new BigDecimal("5.00")), discount); // Adding $5 for every $100
    }

    @Test
    public void testCalculateDiscount_AffiliateUser_ShouldApplyAffiliateDiscount() {
        // Arrange
        User affiliate = new User("affiliate", 0);
        Item item1 = new Item("item1", new BigDecimal("50"), "non-grocery", 1);
        Item item2 = new Item("item2", new BigDecimal("60"), "non-grocery", 1);
        BigDecimal totalAmount = item1.calculateTotalPrice().add(item2.calculateTotalPrice());

        // Act
        BigDecimal discount = discountService.calculateDiscount(affiliate, Arrays.asList(item1, item2), totalAmount);

        // Assert
        BigDecimal expectedDiscount = totalAmount.multiply(new BigDecimal("0.10")); // 10% of $110
        assertEquals(expectedDiscount.add(new BigDecimal("5.00")), discount); // Adding $5 for every $100
    }

    @Test
    public void testCalculateDiscount_LoyalCustomerUser_ShouldApplyLoyalCustomerDiscount() {
        // Arrange
        User loyalCustomer = new User("customer", 3); // 3 years of customer tenure
        Item item1 = new Item("item1", new BigDecimal("50"), "non-grocery", 1);
        Item item2 = new Item("item2", new BigDecimal("60"), "non-grocery", 1);
        BigDecimal totalAmount = item1.calculateTotalPrice().add(item2.calculateTotalPrice());

        // Act
        BigDecimal discount = discountService.calculateDiscount(loyalCustomer, Arrays.asList(item1, item2), totalAmount);

        // Assert
        BigDecimal expectedDiscount = totalAmount.multiply(new BigDecimal("0.05")); // 5% of $110
        assertEquals(expectedDiscount.add(new BigDecimal("5.00")), discount); // Adding $5 for every $100
    }

    @Test
    public void testCalculateDiscount_GroceryItem_ShouldNotApplyUserBasedDiscount() {
        // Arrange
        User employee = new User("employee", 0);
        Item groceryItem = new Item("item1", new BigDecimal("50"), "grocery", 1);
        Item nonGroceryItem = new Item("item2", new BigDecimal("60"), "non-grocery", 1);
        BigDecimal totalAmount = groceryItem.calculateTotalPrice().add(nonGroceryItem.calculateTotalPrice());

        // Act
        BigDecimal discount = discountService.calculateDiscount(employee, Arrays.asList(groceryItem, nonGroceryItem), totalAmount);

        // Assert
        BigDecimal expectedDiscount = new BigDecimal("5.00"); // No user discount, just the $5 for every $100
        assertEquals(expectedDiscount, discount);
    }

    @Test
    public void testCalculateDiscount_NoItems_ShouldReturnZeroDiscount() {
        // Arrange
        User customer = new User("customer", 1); // Customer with less than 2 years
        BigDecimal totalAmount = new BigDecimal("50");

        // Act
        BigDecimal discount = discountService.calculateDiscount(customer, Collections.emptyList(), totalAmount);

        // Assert
        assertEquals(new BigDecimal("0.00"), discount); // No items, no discount
    }

    @Test
    public void testCalculateDiscount_BillOver100_ShouldApplyBillDiscount() {
        // Arrange
        User customer = new User("customer", 3); // Loyal customer with 3 years of tenure
        Item item1 = new Item("item1", new BigDecimal("50"), "non-grocery", 1);
        Item item2 = new Item("item2", new BigDecimal("60"), "non-grocery", 1);
        BigDecimal totalAmount = item1.calculateTotalPrice().add(item2.calculateTotalPrice()); // $150

        // Act
        BigDecimal discount = discountService.calculateDiscount(customer, Arrays.asList(item1, item2), totalAmount);

        // Assert
        BigDecimal expectedDiscount = totalAmount.multiply(new BigDecimal("0.05")); // 5% for loyal customer
        expectedDiscount = expectedDiscount.add(new BigDecimal("5.00")); // $5 for every $100
        assertEquals(expectedDiscount, discount);
    }

    @Test
    public void testCalculateDiscount_MultipleItems_ShouldApplyDiscountForAllItems() {
        // Arrange
        User employee = new User("employee", 0);
        Item item1 = new Item("item1", new BigDecimal("50"), "non-grocery", 2); // 2 items of $50
        Item item2 = new Item("item2", new BigDecimal("60"), "non-grocery", 1);
        BigDecimal totalAmount = item1.calculateTotalPrice().add(item2.calculateTotalPrice()); // $160

        // Act
        BigDecimal discount = discountService.calculateDiscount(employee, Arrays.asList(item1, item2), totalAmount);

        // Assert
        BigDecimal expectedDiscount = totalAmount.multiply(new BigDecimal("0.30")); // 30% of $160
        assertEquals(expectedDiscount.add(new BigDecimal("5.00")), discount); // Adding $5 for every $100
    }
}
