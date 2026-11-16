from decimal import ROUND_HALF_UP, Decimal


def to_cents(amount: Decimal | int | str) -> int:
    """Convert an amount in the main currency unit to whole cents."""
    value = Decimal(str(amount)) * 100
    return int(value.quantize(Decimal("1"), rounding=ROUND_HALF_UP))


def format_cents(cents: int, symbol: str = "$") -> str:
    sign = "-" if cents < 0 else ""
    whole, rest = divmod(abs(cents), 100)
    return f"{sign}{symbol}{whole:,}.{rest:02d}"
