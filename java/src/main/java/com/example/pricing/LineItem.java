package com.example.pricing;

/** One product line on an order. */
public record LineItem(String sku, Money unitPrice, int quantity, boolean taxable) {

    public LineItem {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("sku is required");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be at least 1, was " + quantity);
        }
        if (unitPrice.isNegative()) {
            throw new IllegalArgumentException("unit price cannot be negative");
        }
    }

    public static LineItem of(String sku, String unitPrice, int quantity) {
        return new LineItem(sku, Money.of(unitPrice), quantity, true);
    }

    public Money total() {
        return unitPrice.times(quantity);
    }
}
