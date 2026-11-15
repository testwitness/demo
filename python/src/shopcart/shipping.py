FREE_SHIPPING_FROM_CENTS = 10000

# (max weight in grams, price in cents), checked in order
RATES = [
    (500, 450),
    (2000, 790),
    (10000, 1490),
]


def shipping_cost(weight_grams: int, subtotal_cents: int) -> int:
    if weight_grams <= 0:
        raise ValueError("weight must be positive")
    if subtotal_cents >= FREE_SHIPPING_FROM_CENTS:
        return 0
    for max_weight, price in RATES:
        if weight_grams <= max_weight:
            return price
    raise ValueError(f"{weight_grams} g is over the parcel limit")
