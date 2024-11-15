package com.xische.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents an item in a bill with its details like name, price, category, and quantity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String name;            // Name of the item
    private BigDecimal price;       // Price of the item
    private String category;        // Category of the item (e.g., grocery, electronics)
    private int quantity;           // Quantity of the item

    /**
     * Helper method to calculate the total price for this item (price * quantity).
     */
    public BigDecimal calculateTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}