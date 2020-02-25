import {
  JsonController,
  Get,
  QueryParam,
  NotFoundError
} from "routing-controllers";

import { UserPlaylist } from "@/entity/UsersPlaylist";
import { YoutubeVideo } from "@/entity/YoutubeVideo";

@JsonController("/api/v1")
export class YoutubeVideoController {
  @Get("/youtube_video")
  async getByYoutubeVideoId(
    @QueryParam("youtubeVideoId")
    youtubeVideoId: YoutubeVideo["youtubeVideoId"]
  ) {
    const video = await YoutubeVideo.findOne({
      where: { youtubeVideoId }
    });

    if (!video) throw new NotFoundError();
    return video;
  }

  @Get("/youtube_videos")
  async getByYoutubePlaylistId(
    @QueryParam("youtubePlaylistId")
    youtubePlaylistId: UserPlaylist["youtubePlaylistId"]
  ) {
    const videos = await UserPlaylist.find({
      relations: ["videos"],
      where: { youtubePlaylistId }
    });

    if (!videos) throw new NotFoundError();
    return videos;
  }
}
