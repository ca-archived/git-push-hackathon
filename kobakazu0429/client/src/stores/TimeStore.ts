import { observable, action } from "mobx";

interface VideoTime {
  youtubeVideoId: string;
  start: number | null;
  end: number | null;
}

export class TimeStore {
  constructor() {
    this.videos = [];
  }

  @observable public videos: VideoTime[];

  @action public pushVideo(video: VideoTime) {
    this.videos.push(video);
  }

  @action public clearVideos() {
    this.videos.length = 0;
  }

  @action public setVideoss(videos: VideoTime[]) {
    this.clearVideos();
    this.videos = [...videos];
  }

  @action public editVideo(video: VideoTime) {
    const newVideos = this.videos.filter(
      v => v.youtubeVideoId !== video.youtubeVideoId
    );
    this.clearVideos();
    this.setVideoss(newVideos);
    this.pushVideo(video);
  }
}
