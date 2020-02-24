import {
  JsonController,
  Get,
  Post,
  Body,
  QueryParam
} from "routing-controllers";

import { User } from "@/entity/User";

@JsonController("/api/v1/user")
export class YoutubeVideoController {
  @Get("/")
  async getById(@QueryParam("id") id: number) {
    const user = await User.findOne(id);
    return user;
  }

  @Post("/")
  async create(@Body() user: User) {
    try {
      const newUser = User.create(user);
      return {
        status: "ok",
        user: newUser
      };
    } catch (e) {
      console.warn(e);
      return {
        status: "failed",
        request: {
          user
        }
      };
    }
  }
}
