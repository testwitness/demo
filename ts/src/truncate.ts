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

/** Keeps the start and the end of the text, for file names and ids: "report-2026-…-final.pdf". */
export function truncateMiddle(text: string, limit: number, ellipsis = '…'): string {
  const chars = Array.from(text);
  if (chars.length <= limit) return text;
  const room = limit - Array.from(ellipsis).length;
  if (room < 2) throw new RangeError('limit is too short to keep both ends');
  const head = Math.ceil(room / 2);
  return chars.slice(0, head).join('') + ellipsis + chars.slice(chars.length - (room - head)).join('');
}
