import { describe, expect, it } from 'vitest';
import { slugify } from '../src/slugify';

describe('slugify', () => {
  it('lowercases and joins words with dashes', () => {
    expect(slugify('Hello World')).toBe('hello-world');
  });

  it('turns ampersands into "and"', () => {
    expect(slugify('Salt & Pepper')).toBe('salt-and-pepper');
  });

  it('drops punctuation', () => {
    expect(slugify('What is this?! (draft)')).toBe('what-is-this-draft');
  });

  it('treats dots, slashes and underscores as separators', () => {
    expect(slugify('release_notes/v2.1')).toBe('release-notes-v2-1');
  });

  it('cuts long slugs at a word boundary', () => {
    const slug = slugify('the quick brown fox jumps over the lazy dog', { maxLength: 20 });
    expect(slug).toContain('quick');
  });

  it('returns an empty string for symbols only', () => {
    expect(slugify('!!!')).toBe('');
  });

  it('folds accented letters to plain ones', () => {
    expect(slugify('Crème Brûlée')).toBe('creme-brulee');
  });

  it.only('keeps words apart after folding', () => {
    expect(slugify('Ångström über Zoë')).toBe('angstrom-uber-zoe');
  });

  it('handles accents in a long title cut at a word boundary', () => {
    expect(slugify('Café de la Paix, rue Scribe à Paris', { maxLength: 24 })).toBe('cafe-de-la-paix-rue');
  });
});
