package com.example.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class PriceCalculatorTest {

    private final PriceCalculator calculator = new PriceCalculator(TaxRate.of("8.25"));

    @Test
    void addsTaxToTheSubtotal() {
        var quote = calculator.quote(List.of(LineItem.of("BOOK-1", "20.00", 2)), Discount.none());
        assertEquals(Money.of("40.00"), quote.subtotal());
        assertEquals(Money.of("3.30"), quote.tax());
        assertEquals(Money.of("43.30"), quote.total());
    }

    @Test
    void appliesDiscountBeforeTax() {
        var items = List.of(LineItem.of("BOOK-1", "20.00", 2), LineItem.of("PEN-3", "5.00", 1));
        var quote = calculator.quote(items, new Discount.Percentage(new BigDecimal("10")));
        assertEquals(Money.of("4.50"), quote.discount());
        assertEquals(Money.of("3.34"), quote.tax());
        assertEquals(Money.of("43.84"), quote.total());
    }

    @Test
    void emptyOrderCostsNothing() {
        var quote = calculator.quote(List.of(), Discount.none());
        assertEquals(Money.ZERO, quote.total());
    }

    @Test
    void rejectsZeroQuantity() {
        assertThrows(IllegalArgumentException.class, () -> LineItem.of("BOOK-1", "20.00", 0));
    }
}
