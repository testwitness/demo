import pytest

from shopcart import shipping_cost
from shopcart.money import format_cents, to_cents


@pytest.mark.parametrize(
    ("weight_grams", "expected"),
    [
        (400, 450),
        (1800, 790),
        (9000, 1490),
    ],
    ids=["small", "medium", "large"],
)
def test_parcel_rate(weight_grams, expected):
    assert shipping_cost(weight_grams, 2000) == expected


def test_over_parcel_limit():
    with pytest.raises(ValueError, match="parcel limit"):
        shipping_cost(12000, 2000)


def test_zero_weight_is_rejected():
    with pytest.raises(ValueError, match="positive"):
        shipping_cost(0, 2000)


def test_to_cents_rounds_half_up():
    assert to_cents("19.995") == 2000


def test_format_cents():
    assert format_cents(123456) == "$1,234.56"
    assert format_cents(-5) == "-$0.05"
