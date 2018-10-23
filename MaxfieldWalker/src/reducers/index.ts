import { Action } from "redux";
import { AppState } from "../store/index";
import { SET_GITHUB_ACCESS_TOKEN } from "../actions/types";
import { SetGithubAccessTokenAction } from "../actions";

export const reducer = (state: AppState, action: Action): AppState => {
  if (action.type === SET_GITHUB_ACCESS_TOKEN) {
    const { accessToken } = action as SetGithubAccessTokenAction;
    return {
      accessToken
    };
  }

  return state;
};
