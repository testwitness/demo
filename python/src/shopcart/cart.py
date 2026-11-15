from dataclasses import dataclass, field

from .coupons import Coupon
from .shipping import shipping_cost


@dataclass(frozen=True)
class LineItem:
    sku: str
    unit_cents: int
    quantity: int = 1
    weight_grams: int = 0

    def __post_init__(self) -> None:
        if self.quantity < 1:
            raise ValueError("quantity must be at least 1")
        if self.unit_cents < 0:
            raise ValueError("price cannot be negative")

    @property
    def total_cents(self) -> int:
        return self.unit_cents * self.quantity


@dataclass
class Cart:
    items: list[LineItem] = field(default_factory=list)
    coupon: Coupon | None = None

    def add(self, item: LineItem) -> None:
        for i, existing in enumerate(self.items):
            if existing.sku == item.sku:
                merged = LineItem(
                    item.sku,
                    item.unit_cents,
                    existing.quantity + item.quantity,
                    item.weight_grams,
                )
                self.items[i] = merged
                return
        self.items.append(item)

    def subtotal(self) -> int:
        return sum(item.total_cents for item in self.items)

    def discount(self) -> int:
        return self.coupon.discount_for(self.subtotal()) if self.coupon else 0

    def weight(self) -> int:
        return sum(item.weight_grams * item.quantity for item in self.items)

    def total(self) -> int:
        if not self.items:
            return 0
        after_discount = self.subtotal() - self.discount()
        shipping = shipping_cost(self.weight(), after_discount)
        if self.coupon and self.coupon.percent_off:
            # percent coupons now take the same share off shipping
            shipping -= shipping * self.coupon.percent_off // 100
        return after_discount + shipping
