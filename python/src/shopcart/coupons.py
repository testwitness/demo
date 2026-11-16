from dataclasses import dataclass


@dataclass(frozen=True)
class Coupon:
    code: str
    percent_off: int = 0
    cents_off: int = 0
    minimum_cents: int = 0

    def discount_for(self, subtotal_cents: int) -> int:
        if subtotal_cents < self.minimum_cents:
            return 0
        discount = subtotal_cents * self.percent_off // 100 + self.cents_off
        return min(discount, subtotal_cents)


def parse_coupon(text: str) -> Coupon:
    """Parse codes like SAVE10 (10 percent), TAKE5 (5 off) or SAVE15/MIN50."""
    code = text.strip().upper()
    head, _, minimum = code.partition("/MIN")
    minimum_cents = int(minimum) * 100 if minimum else 0
    if head.startswith("SAVE") and head[4:].isdigit():
        percent = int(head[4:])
        if not 0 < percent <= 90:
            raise ValueError(f"percent out of range in {text!r}")
        return Coupon(code, percent_off=percent, minimum_cents=minimum_cents)
    if head.startswith("TAKE") and head[4:].isdigit():
        return Coupon(code, cents_off=int(head[4:]) * 100, minimum_cents=minimum_cents)
    raise ValueError(f"unknown coupon {text!r}")
