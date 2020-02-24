import {
  JsonController,
  Get,
  Post,
  Body,
  QueryParam
} from "routing-controllers";
import { User } from "@/entity/User";
import { UserPlaylist } from "@/entity/UsersPlaylist";

interface AddPlaylistBody {
  googleId: User["googleId"];
  youtubePlaylistId: UserPlaylist["youtubePlaylistId"];
}

@JsonController("/api/v1/users_playlists")
export class UsersPlaylistController {
  @Get("/")
  async getByGoogleId(@QueryParam("googleId") googleId: User["googleId"]) {
    const playlists = await User.find({
      relations: ["playlists"],
      where: {
        googleId
      }
    });
    return playlists;
  }

  @Post("/")
  async addPlaylist(
    @Body()
    body: AddPlaylistBody
  ) {
    try {
      const { googleId, youtubePlaylistId } = body;

      const user = await User.findOne({ where: { googleId } });
      if (!user) throw new Error(`user not found by googleId: ${googleId}`);

      const newPlaylist = new UserPlaylist();
      newPlaylist.user = user;
      newPlaylist.youtubePlaylistId = youtubePlaylistId;
      await newPlaylist.save();

      return {
        status: "ok",
        user: newPlaylist
      };
    } catch (e) {
      console.warn(e);
      return {
        status: "failed",
        request: {
          body
        }
      };
    }
  }
}
