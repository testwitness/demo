package com.example.pricing;

import java.math.BigDecimal;

/** A discount applied to the order subtotal before tax. */
public sealed interface Discount {

    Money apply(Money subtotal);

    static Discount none() {
        return new Fixed(Money.ZERO);
    }

    record Percentage(BigDecimal percent) implements Discount {
        public Percentage {
            if (percent.signum() < 0 || percent.compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new IllegalArgumentException("percent must be between 0 and 100");
            }
        }

        @Override
        public Money apply(Money subtotal) {
            return subtotal.percent(percent);
        }
    }

    record Fixed(Money off) implements Discount {
        @Override
        public Money apply(Money subtotal) {
            return off.compareTo(subtotal) > 0 ? subtotal : off;
        }
    }
}
