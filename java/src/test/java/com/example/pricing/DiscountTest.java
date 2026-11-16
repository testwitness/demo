package com.example.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class DiscountTest {

    @Test
    void percentageOfSubtotal() {
        Discount tenPercent = new Discount.Percentage(new BigDecimal("10"));
        assertEquals(Money.of("4.50"), tenPercent.apply(Money.of("45.00")));
    }

    @Test
    void fixedAmountOff() {
        Discount fiveOff = new Discount.Fixed(Money.of("5.00"));
        assertEquals(Money.of("5.00"), fiveOff.apply(Money.of("45.00")));
    }

    @Test
    void fixedAmountNeverExceedsSubtotal() {
        Discount fiftyOff = new Discount.Fixed(Money.of("50.00"));
        assertEquals(Money.of("12.00"), fiftyOff.apply(Money.of("12.00")));
    }

    @Test
    void rejectsPercentageOverOneHundred() {
        assertThrows(IllegalArgumentException.class,
                () -> new Discount.Percentage(new BigDecimal("120")));
    }
}
