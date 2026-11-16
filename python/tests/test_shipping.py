import pytest

from shopcart import shipping_cost
from shopcart.money import format_cents, to_cents


def test_small_parcel_rate():
    assert shipping_cost(400, 2000) == 450


def test_medium_parcel_rate():
    assert shipping_cost(1800, 2000) == 790


def test_large_parcel_rate():
    assert shipping_cost(9000, 2000) == 1490


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
