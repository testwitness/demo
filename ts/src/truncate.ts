export function truncate(text: string, limit: number, ellipsis = '…'): string {
  if (limit < ellipsis.length) throw new RangeError('limit is shorter than the ellipsis');
  const chars = Array.from(text);
  if (chars.length <= limit) return text;
  const room = limit - Array.from(ellipsis).length;
  let cut = chars.slice(0, room).join('');
  const lastSpace = cut.lastIndexOf(' ');
  if (lastSpace > room * 0.6) cut = cut.slice(0, lastSpace);
  return cut.trimEnd() + ellipsis;
}
