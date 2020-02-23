import { observable, computed } from "mobx";
import { GoogleOAuthStore } from "./GoogleOAuthStore";
import { YoutubeStore } from "./YoutubeStore";

export default class RootStore {
  constructor() {
    this.googleOAuthStore = new GoogleOAuthStore();
  }

  @observable public googleOAuthStore: GoogleOAuthStore;

  @computed
  public get youtubeStore(): YoutubeStore {
    return new YoutubeStore(this.googleOAuthStore.response?.accessToken);
  }
}
