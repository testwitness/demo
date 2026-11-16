export interface DateRange {
  start: string;
  end: string;
  days: number;
}

const ISO_DAY = /^(\d{4})-(\d{2})-(\d{2})$/;
const MS_PER_DAY = 24 * 60 * 60 * 1000;

function toUtc(text: string): number {
  const match = ISO_DAY.exec(text.trim());
  if (!match) throw new Error(`not a date: ${text}`);
  const [, y, m, d] = match.map(Number);
  const time = Date.UTC(y, m - 1, d);
  const check = new Date(time);
  if (check.getUTCMonth() !== m - 1 || check.getUTCDate() !== d) {
    throw new Error(`no such day: ${text}`);
  }
  return time;
}

/**
 * Parses "2026-03-01..2026-03-07" or "2026-03-01 to 2026-03-07".
 * Both ends are inclusive, so a single day range has days = 1.
 */
export function parseDateRange(text: string): DateRange {
  const parts = text.split(/\s*(?:\.\.|\bto\b)\s*/);
  if (parts.length !== 2) throw new Error(`expected two dates in: ${text}`);
  const [startText, endText] = parts;
  const start = toUtc(startText);
  const end = toUtc(endText);
  if (end < start) throw new Error(`range ends before it starts: ${text}`);
  return {
    start: startText.trim(),
    end: endText.trim(),
    days: Math.round((end - start) / MS_PER_DAY) + 1,
  };
}
