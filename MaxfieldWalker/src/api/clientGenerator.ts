import { GitHubClient } from "./githubClient";
import { store, AppState } from "../store";
import { GITHUB_PERSONAL_ACCESS_TOKEN } from "./credentials";

function getToken() {
  const state = store.getState() as AppState;
  if (state.accessToken) {
    return state.accessToken;
  } else {
    const t = localStorage.getItem("ns-github_access_token");
    if (t) return t;

    return process.env.NODE_ENV === "development"
      ? GITHUB_PERSONAL_ACCESS_TOKEN
      : "";
  }
}

export class GithubClientFactory {
  static instance(): GitHubClient {
    const token = getToken();

    if (!token) {
      alert("アクセストークンが無いようです。ログインからやり直してください。");
      throw new Error("Critical error");
    } else {
      localStorage.setItem("ns-github_access_token", token);
    }

    return new GitHubClient(token);
  }
}
