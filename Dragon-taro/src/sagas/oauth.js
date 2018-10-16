import { fork, call, put, take } from "redux-saga/effects";
import { REQUEST_OAUTH, GET_USER } from "../actions/constants";
import { OAuth } from "oauthio-web";
import { ACCESS_TOKEN } from "../secret";
import { Get } from "./api";
import {
  successLogin,
  failureLogin,
  getUser,
  setUser,
  noUser
} from "../actions/actions";

const token = sessionStorage.getItem("access_token");

function signIn() {
  OAuth.initialize(ACCESS_TOKEN);

  return OAuth.popup("github", { scopes: ["gist"] })
    .done(function(result) {
      const json = result.toJson();
      return { access_token: json.access_token };
    })
    .fail(function(err) {
      return { err: err };
    });
}

function* handleRequestOAuth() {
  while (true) {
    yield take(REQUEST_OAUTH);

    const { access_token, err } = yield call(signIn);
    if (!err) {
      sessionStorage.setItem("access_token", access_token);

      yield put(successLogin());
      yield put(getUser());
    } else {
      yield put(failureLogin({ err: err }));
    }
  }
}

function* getUserInfo() {
  while (true) {
    yield take(GET_USER);

    const { resp, error } = yield call(Get, "user");
    if (!error) {
      yield put(setUser(resp));
    } else {
      yield put(noUser(error));
    }
  }
}

// 初期化したときにすでにログイン済みだったらUser情報を取得
function* initialize() {
  if (token) {
    yield put(successLogin());
    yield put(getUser());
  }
}

export default function* rootSaga() {
  yield fork(handleRequestOAuth);
  yield fork(getUserInfo);
  yield fork(initialize);
}
