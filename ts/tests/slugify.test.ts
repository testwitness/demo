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
});
