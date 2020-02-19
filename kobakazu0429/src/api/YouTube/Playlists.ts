import { YoutubeRestClient } from "./YoutubeRestClient";
import { PlaylistsResponse } from "youtube/v3/playlists";

export class YoutubePlaylistsApi extends YoutubeRestClient {
  public async fetchPlaylists() {
    return await this.restClient.get<PlaylistsResponse>("/playlists", {
      mine: true,
      part: "snippet"
    });
  }
}
