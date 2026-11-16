package com.example.pricing;

import java.util.List;

/**
 * Works out an order total. The discount is spread over taxable and
 * non taxable lines in proportion to their share of the subtotal, and tax
 * is charged on what is left of the taxable part.
 */
public final class PriceCalculator {

    private final TaxRate taxRate;

    public PriceCalculator(TaxRate taxRate) {
        this.taxRate = taxRate;
    }

    public Quote quote(List<LineItem> items, Discount discount) {
        Money subtotal = Money.ZERO;
        Money taxableSubtotal = Money.ZERO;
        for (LineItem item : items) {
            subtotal = subtotal.plus(item.total());
            if (item.taxable()) {
                taxableSubtotal = taxableSubtotal.plus(item.total());
            }
        }
        Money discountAmount = discount.apply(subtotal);
        Money taxableAfterDiscount = taxableSubtotal;
        if (subtotal.compareTo(Money.ZERO) > 0 && taxableSubtotal.compareTo(Money.ZERO) > 0) {
            Money taxableShare = Money.of(discountAmount.amount()
                    .multiply(taxableSubtotal.amount())
                    .divide(subtotal.amount(), 2, java.math.RoundingMode.HALF_UP));
            taxableAfterDiscount = taxableSubtotal.minus(taxableShare);
        }
        Money tax = taxRate.on(taxableAfterDiscount);
        Money total = subtotal.minus(discountAmount).plus(tax);
        return new Quote(subtotal, discountAmount, tax, total);
    }

    public record Quote(Money subtotal, Money discount, Money tax, Money total) {}
}
