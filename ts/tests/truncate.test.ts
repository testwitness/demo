import { describe, expect, it } from 'vitest';
import { truncate } from '../src/truncate';

describe('truncate', () => {
  it('leaves short text alone', () => {
    expect(truncate('short', 10)).toBe('short');
  });

  it('cuts at a space when one is close to the limit', () => {
    expect(truncate('The meeting moved to Thursday afternoon', 24)).toBe('The meeting moved to…');
  });

  it('cuts inside a word when there is no good space', () => {
    expect(truncate('Supercalifragilistic', 10)).toBe('Supercali…');
  });

  it('counts emoji as one character', () => {
    expect(truncate('🙂🙂🙂🙂🙂', 4)).toBe('🙂🙂🙂…');
  });

  it('refuses a limit shorter than the ellipsis', () => {
    expect(() => truncate('anything', 2, '...')).toThrow(RangeError);
  });
});
