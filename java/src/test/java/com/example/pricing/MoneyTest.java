package com.example.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void keepsTwoDecimalPlaces() {
        assertEquals("12.50", Money.of("12.5").toString());
    }

    @Test
    void roundsHalfUp() {
        assertEquals(Money.of("10.01"), Money.of("10.005"));
    }

    @Test
    void multipliesByQuantity() {
        assertEquals(Money.of("37.47"), Money.of("12.49").times(3));
    }

    @Test
    void takesAPercentage() {
        assertEquals(Money.of("1.65"), Money.of("20.00").percent(new BigDecimal("8.25")));
    }

    @Test
    void knowsWhenItIsNegative() {
        assertTrue(Money.of("5.00").minus(Money.of("7.50")).isNegative());
    }
}
