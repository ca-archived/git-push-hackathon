import { RestClient } from "@/api/restClient";
import { isDevelopment } from "@/utils/environment";

const apiUrl = isDevelopment()
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
