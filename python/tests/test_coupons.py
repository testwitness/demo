import pytest

from shopcart import parse_coupon


def test_percent_coupon():
    coupon = parse_coupon("save10")
    assert coupon.discount_for(5000) == 500


def test_fixed_coupon():
    coupon = parse_coupon("TAKE5")
    assert coupon.discount_for(5000) == 500


def test_minimum_spend_not_reached():
    coupon = parse_coupon("SAVE15/MIN50")
    assert coupon.discount_for(4999) == 0


def test_minimum_spend_reached():
    coupon = parse_coupon("SAVE15/MIN50")
    assert coupon.discount_for(6000) == 900


def test_discount_never_exceeds_subtotal():
    coupon = parse_coupon("TAKE20")
    assert coupon.discount_for(1500) == 1500


def test_unknown_code_is_rejected():
    with pytest.raises(ValueError, match="unknown coupon"):
        parse_coupon("FREESTUFF")


def test_percent_over_ninety_is_rejected():
    with pytest.raises(ValueError, match="out of range"):
        parse_coupon("SAVE95")
