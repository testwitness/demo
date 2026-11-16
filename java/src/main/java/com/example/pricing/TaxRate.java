package com.example.pricing;

import java.math.BigDecimal;

/** A sales tax rate in percent, for example 8.25. */
public record TaxRate(BigDecimal percent) {

    public TaxRate {
        if (percent.signum() < 0) {
            throw new IllegalArgumentException("tax rate cannot be negative");
        }
    }

    public static TaxRate of(String percent) {
        return new TaxRate(new BigDecimal(percent));
    }

    public Money on(Money amount) {
        return amount.percent(percent);
    }
}
