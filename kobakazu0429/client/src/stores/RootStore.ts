import { observable, computed } from "mobx";
import { GoogleOAuthStore } from "./GoogleOAuthStore";
import { YoutubeStore } from "./YoutubeStore";
import { TimeStore } from "./TimeStore";

export default class RootStore {
  constructor() {
    this.googleOAuthStore = new GoogleOAuthStore();
    this.timeStore = new TimeStore();
  }

  @observable public googleOAuthStore: GoogleOAuthStore;
  @observable public timeStore: TimeStore;

  @computed
  public get youtubeStore(): YoutubeStore {
    return new YoutubeStore(this.googleOAuthStore.response?.accessToken);
  }
}
