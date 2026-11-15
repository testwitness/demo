package com.example.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        assertNotNull(quote.total());
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
