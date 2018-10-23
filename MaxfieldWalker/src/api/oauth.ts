import axios, { AxiosInstance } from "axios";
import * as qs from "query-string";

export class GithubOAuth {
  private _instance: AxiosInstance;

  constructor() {
    this._instance = axios.create({
      timeout: 50000
    });
  }

  async getAccessToken(
    clientId: string,
    clientSecret: string,
    code: string
  ): Promise<string | undefined> {
    const response = await this._instance.get(
      "http://localhost:3000/my-oauth",
      {
        params: {
          client_id: clientId,
          client_secret: clientSecret,
          code
        }
      }
    );

    const d = qs.parse(response.data);
    const accessToken = d["access_token"] as string | undefined;

    return accessToken;
  }
}
