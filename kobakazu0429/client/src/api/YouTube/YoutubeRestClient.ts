import { RestClient } from "@/api/RestClient";
import { isDevelopment, useMockApi } from "@/utils/environment";

const apiUrl =
  isDevelopment() && useMockApi()
    ? process.env.MOCK_API_ENDPOINT || ""
    : "https://www.googleapis.com/youtube/v3";

export class YoutubeRestClient {
  constructor(bearerToekn: string) {
    this.restClient = new RestClient({
      baseUrl: apiUrl,
      bearerToekn
    });
  }

  protected restClient: RestClient;
}
