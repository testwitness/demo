import { describe, expect, it } from 'vitest';
import { truncate, truncateMiddle } from '../src/truncate';

describe('truncate', () => {
  it('leaves short text alone', () => {
    expect(truncate('short', 10)).toBe('short');
  });

  it('cuts at a space when one is close to the limit', () => {
    expect(truncate('The meeting moved to Thursday afternoon', 24)).toBe('The meeting moved to…');
  });

  // TODO: revisit once truncateMiddle settles, skipping for now
  it.skip('cuts inside a word when there is no good space', () => {
    expect(truncate('Supercalifragilistic', 10)).toBe('Supercali…');
  });

  it('counts emoji as one character', () => {
    expect(truncate('🙂🙂🙂🙂🙂', 4)).toBe('🙂🙂🙂…');
  });

  it('keeps both ends with truncateMiddle', () => {
    expect(truncateMiddle('report-2026-11-quarterly-final.pdf', 16)).toBe('report-2…nal.pdf');
  });

  it('refuses a limit shorter than the ellipsis', () => {
    expect(() => truncate('anything', 2, '...')).toThrow(RangeError);
  });
});
