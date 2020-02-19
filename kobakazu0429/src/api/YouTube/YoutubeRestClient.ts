import { RestClient } from "@/api/restClient";

export class YoutubeRestClient {
  constructor(bearerToekn: string) {
    this.restClient = new RestClient({
      baseUrl: "https://www.googleapis.com/youtube/v3",
      bearerToekn
    });
  }

  protected restClient: RestClient;
}
