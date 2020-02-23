import { observable, action } from "mobx";
import { YoutubePlaylistsApi } from "@/api/YouTube/Playlists";
import { YoutubePlaylistItemsApi } from "@/api/YouTube/PlaylistItems";
import { PlaylistsResponse } from "youtube/v3/playlists";
import { PlaylistItemsResponse } from "youtube/v3/playlistItems";

type Playlists = PlaylistsResponse["items"];
type PlaylistItems = PlaylistItemsResponse["items"];

export class YoutubeStore {
  constructor(bearerToekn: string = "") {
    this.playlistsApi = new YoutubePlaylistsApi(bearerToekn);
    this.playlistItemsApi = new YoutubePlaylistItemsApi(bearerToekn);

    this.playlists = [];
    this.playlistItems = [];
  }

  @observable public playlists: Playlists;
  @observable public playlistItems: PlaylistItems;

  public playlistItemsApi?: YoutubePlaylistItemsApi;
  public playlistsApi?: YoutubePlaylistsApi;

  public fetchPlaylists() {
    this.playlistsApi
      ?.fetchPlaylists()
      .then(res => this.setPlaylists(res.data.items));
  }

  public fetchPlaylistItems(playlistId: string) {
    this.playlistItemsApi
      ?.fetchPlaylistItems(playlistId)
      .then(res => this.setPlaylistItems(res.data.items));
  }

  public deletePlaylistItem(videoId: string) {
    this.playlistItemsApi?.deletePlaylistItem(videoId);
    this.setPlaylistItems(this.playlistItems.filter(v => v.id !== videoId));
  }

  @action private setPlaylists(ps: Playlists) {
    this.playlists.length = 0;
    this.playlists.push(...ps);
  }
  @action private setPlaylistItems(ps: PlaylistItems) {
    this.playlistItems.length = 0;
    this.playlistItems.push(...ps);
  }
}
