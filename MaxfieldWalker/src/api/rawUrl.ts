import axios from "axios";

export async function getRawUrlContent(rawUrl: string): Promise<string> {
  const client = axios.create({
    timeout: 20000
  });

  const result = await client.get<string | number>(rawUrl);
  const r = result.data;

  return typeof r === "string" ? r : r.toString();
}
