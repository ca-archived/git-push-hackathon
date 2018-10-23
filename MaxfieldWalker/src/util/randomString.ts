export function generateRandomString(length: number) {
  const c = "abcdefghijklmnopqrstuvwxyz0123456789";
  let s = "";
  for (var i = 0; i < length; i++) {
    s += c[Math.floor(Math.random() * c.length)];
  }
  return s;
}
