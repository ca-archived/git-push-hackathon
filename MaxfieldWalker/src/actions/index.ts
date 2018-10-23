import { Action } from "redux";
import { SET_GITHUB_ACCESS_TOKEN } from "./types";

export interface SetGithubAccessTokenAction extends Action {
  accessToken: string;
}

export function setGithubAccessToken(
  accessToken: string
): SetGithubAccessTokenAction {
  return {
    type: SET_GITHUB_ACCESS_TOKEN,
    accessToken
  };
}
