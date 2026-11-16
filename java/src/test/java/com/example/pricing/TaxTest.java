package com.example.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

/** Which lines are taxed, and how a discount is shared between taxable and exempt lines. */
class TaxTest {

    private final PriceCalculator calculator = new PriceCalculator(TaxRate.of("8.25"));

    @Test
    void doesNotTaxExemptLines() {
        var items = List.of(
                LineItem.of("BOOK-1", "20.00", 1),
                new LineItem("GIFTCARD-25", Money.of("25.00"), 1, false));
        var quote = calculator.quote(items, Discount.none());
        assertEquals(Money.of("1.65"), quote.tax());
        assertEquals(Money.of("46.65"), quote.total());
    }

    @Test
    void splitsTheDiscountAcrossTaxableAndExemptLines() {
        var items = List.of(
                LineItem.of("BOOK-1", "30.00", 1),
                new LineItem("GIFTCARD-10", Money.of("10.00"), 1, false));
        var quote = calculator.quote(items, new Discount.Fixed(Money.of("8.00")));
        assertEquals(Money.of("1.98"), quote.tax());
        assertEquals(Money.of("33.98"), quote.total());
    }
}
