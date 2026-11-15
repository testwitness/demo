import pytest

from shopcart import Cart, LineItem, parse_coupon


def make_cart():
    cart = Cart()
    cart.add(LineItem("MUG-01", 1200, quantity=2, weight_grams=350))
    cart.add(LineItem("TEA-04", 850, quantity=1, weight_grams=120))
    return cart


def test_subtotal_adds_line_items():
    assert make_cart().subtotal() == 3250


def test_adding_same_sku_merges_quantity():
    cart = make_cart()
    cart.add(LineItem("TEA-04", 850, quantity=2, weight_grams=120))
    assert len(cart.items) == 2
    assert cart.items[1].quantity == 3


def test_total_includes_shipping_by_weight():
    total = make_cart().total()
    assert total == 3250 + 790


def test_total_with_percent_coupon():
    cart = Cart([LineItem("LAMP-2", 2000, quantity=1, weight_grams=1500)])
    cart.coupon = parse_coupon("SAVE10")
    total = cart.total()
    assert total is not None


def test_free_shipping_above_threshold():
    cart = Cart([LineItem("CHAIR-9", 8000, weight_grams=7000)])
    assert cart.total() == 8000


def test_empty_cart_costs_nothing():
    assert Cart().total() == 0


def test_quantity_must_be_positive():
    with pytest.raises(ValueError, match="quantity"):
        LineItem("MUG-01", 1200, quantity=0)
