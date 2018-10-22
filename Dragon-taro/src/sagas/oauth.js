import { fork, call, put, take } from "redux-saga/effects";
import { REQUEST_OAUTH, GET_USER, LOGOUT } from "../actions/constants";
import { OAuth } from "oauthio-web";
import { ACCESS_TOKEN } from "../secret";
import { Get } from "./api";
import {
  successLogin,
  failureLogin,
  getUser,
  setUser,
  noUser,
  loading,
  loaded
} from "../actions/actions";
import toastr from "toastr";

const token = sessionStorage.getItem("access_token");

function signIn() {
  OAuth.initialize(ACCESS_TOKEN);

  return OAuth.popup("github")
    .done(result => {
      const { access_token } = result.toJson();
      return { access_token };
    })
    .fail(error => {
      sessionStorage.setItem("toastr", "Login failuer");
      // ログイン中にポップアップを閉じられた場合にreturn {error}が実行されないので、他のerrorが発火する前に強制リロードをかけています。
      location.reload();
    });
}

function* handleRequestOAuth() {
  while (true) {
    yield take(REQUEST_OAUTH);

    yield put(loading());
    const { access_token, error } = yield call(signIn);
    if (!error) {
      sessionStorage.setItem("access_token", access_token);
    } else {
      yield put(failureLogin({ error }));
      yield put(loaded());
    }
    location.reload();
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
function* initialize(history) {
  // ここでpingしたほうがいいかも
  if (token) {
    yield put(successLogin());
    yield put(getUser());
  } else {
    yield call(history.push, "/");
    yield put(loaded());
  }

  const message = sessionStorage.getItem("toastr");
  if (message) {
    toastr.error(message);
    sessionStorage.removeItem("toastr");
  }
}

function* handleLogout() {
  while (true) {
    yield take(LOGOUT);
    sessionStorage.removeItem("access_token");
    location.reload();
  }
}

export default function* rootSaga(history) {
  yield fork(handleRequestOAuth);
  yield fork(getUserInfo);
  yield fork(initialize, history);
  yield fork(handleLogout);
}
