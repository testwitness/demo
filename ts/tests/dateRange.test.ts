import { describe, expect, it } from 'vitest';
import { parseDateRange } from '../src/dateRange';

describe('parseDateRange', () => {
  it('parses a range with two dots', () => {
    expect(parseDateRange('2026-03-01..2026-03-07')).toEqual({
      start: '2026-03-01',
      end: '2026-03-07',
      days: 7,
    });
  });

  it('parses a range written with "to"', () => {
    expect(parseDateRange('2026-12-30 to 2027-01-02').days).toBe(4);
  });

  it('counts a single day as one day', () => {
    expect(parseDateRange('2026-05-05..2026-05-05').days).toBe(1);
  });

  it('counts across a daylight saving change', () => {
    expect(parseDateRange('2026-03-28..2026-03-30').days).toBe(3);
  });

  it('knows leap days', () => {
    expect(parseDateRange('2028-02-28..2028-03-01').days).toBe(3);
  });

  it('rejects days that do not exist', () => {
    expect(() => parseDateRange('2026-02-29..2026-03-01')).toThrow('no such day');
  });

  it('rejects a range that ends before it starts', () => {
    expect(() => parseDateRange('2026-04-10..2026-04-01')).toThrow('ends before');
  });
});
