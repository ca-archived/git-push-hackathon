import { YoutubeRestClient } from "./YoutubeRestClient";
import { PlaylistItemsResponse } from "youtube/v3/playlistItems";

export class YoutubePlaylistItemsApi extends YoutubeRestClient {
  public async fetchPlaylistItems(playlistId: string) {
    return await this.restClient.get<PlaylistItemsResponse>("/playlistItems", {
      part: "id,snippet,contentDetails,status",
      playlistId
    });
  }
}
