import {
  JsonController,
  Get,
  Post,
  Body,
  QueryParam,
  NotFoundError,
  Res
} from "routing-controllers";
import { Response } from "express";

import { User } from "@/entity/User";

@JsonController("/api/v1/user")
export class UserController {
  @Get("/")
  async getById(
    @QueryParam("id") id?: number,
    @QueryParam("googleId") googleId?: string
  ) {
    if (googleId) {
      const users = await User.find({ where: { googleId } });
      if (!users) throw new NotFoundError();
      return users[0];
    }

    const user = await User.findOne(id);
    if (!user) throw new NotFoundError();
    return user;
  }

  @Post("/")
  async create(@Body() user: User, @Res() res: Response) {
    try {
      const users = await User.find({ where: { googleId: user.googleId } });
      if (users) {
        throw new Error(`user is already exsit, googleId: ${user.googleId}`);
      }

      const newUser = new User();
      newUser.googleId = user.googleId;
      await newUser.save();

      return {
        status: "ok",
        user: newUser
      };
    } catch (e) {
      console.warn(e);
      const result = {
        status: "failed",
        request: {
          user
        }
      };
      res.status(409);
      res.send(result);
      return result;
    }
  }
}
