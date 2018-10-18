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
  noUser,
  getGists,
  loading,
  loaded
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

    yield put(loading());
    const { access_token, err } = yield call(signIn);
    if (!err) {
      sessionStorage.setItem("access_token", access_token);

      yield put(successLogin());
      yield put(getUser(access_token));
      yield put(getGists(access_token));
    } else {
      yield put(failureLogin({ err: err }));
      yield put(loaded());
    }
  }
}

function* getUserInfo() {
  while (true) {
    const { payload } = yield take(GET_USER);

    const { resp, error } = yield call(Get, "user", payload);
    if (!error) {
      yield put(setUser(resp));
    } else {
      yield put(noUser(error));
    }
  }
}

// 初期化したときにすでにログイン済みだったらUser情報を取得
function* initialize(history) {
  if (token) {
    yield put(successLogin());
    yield put(getUser());
  } else {
    yield call(history.push, "/");
  }
}

export default function* rootSaga(history) {
  yield fork(handleRequestOAuth);
  yield fork(getUserInfo);
  yield fork(initialize, history);
}
