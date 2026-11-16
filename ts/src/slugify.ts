const SEPARATORS = /[\s_./\\]+/g;
const UNSAFE = /[^a-z0-9-]/g;

export interface SlugOptions {
  maxLength?: number;
}

export function slugify(input: string, options: SlugOptions = {}): string {
  const maxLength = options.maxLength ?? 80;
  let slug = input
    .trim()
    .toLowerCase()
    .replace(/&/g, ' and ')
    .replace(SEPARATORS, '-')
    .replace(UNSAFE, '')
    .replace(/-+/g, '-')
    .replace(/^-|-$/g, '');
  if (slug.length > maxLength) {
    slug = slug.slice(0, maxLength);
    const lastDash = slug.lastIndexOf('-');
    if (lastDash > maxLength / 2) slug = slug.slice(0, lastDash);
  }
  return slug;
}
