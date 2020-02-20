import { YoutubeRestClient } from "./YoutubeRestClient";
import { PlaylistItemsResponse } from "youtube/v3/playlistItems";

export class YoutubePlaylistItemsApi extends YoutubeRestClient {
  public async fetchPlaylistItems(playlistId: string) {
    return await this.restClient.get<PlaylistItemsResponse>("/playlistItems", {
      part: "id,snippet,contentDetails,status",
      playlistId
    });
  }

  public async insertPlaylistItems(playlistId: string, videoId: string) {
    return await this.restClient.post<PlaylistItemsResponse>(
      "/playlistItems?part=snippet",
      {
        snippet: {
          playlistId,
          resourceId: {
            kind: "youtube#video",
            videoId
          }
        }
      }
    );
  }
}
