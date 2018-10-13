import { fork, call, put, take } from "redux-saga/effects";
import { REQUEST_OAUTH, GET_USER } from "../actions/constants";
import { OAuth } from "oauthio-web";
import { ACCESS_TOKEN } from "../secret";
import Get from "./api";
import {
  alreadyLogin,
  successLogin,
  failureLogin,
  getUser,
  setUser,
  noUser
} from "../actions/actions";

function* signIn() {
  let isSuccess = false;
  let error = null;
  OAuth.initialize(ACCESS_TOKEN);

  yield OAuth.popup("github")
    .done(function(result) {
      const json = result.toJson();

      sessionStorage.setItem("access_token", json.access_token);
      isSuccess = true;
    })
    .fail(function(err) {
      error = err;
    });

  if (isSuccess) {
    yield put(successLogin());
    yield put(getUser());
  } else {
    yield put(failureLogin({ err: error }));
  }
}

function* handleRequestOAuth() {
  while (true) {
    // すでにログインしてたらここでputしてすでにログインしてます的なことを流す
    if (sessionStorage.getItem("access_token")) {
      yield put(alreadyLogin());
      yield put(getUser());
    }

    yield take(REQUEST_OAUTH);

    // ここでcallしてもなぜかすり抜けられてしまう→Promiseじゃないから
    yield fork(signIn);
  }
}

function* getUserInfo() {
  while (true) {
    yield take(GET_USER);

    const { payload, error } = yield call(Get, "user");
    if (!error) {
      yield put(setUser(payload));
    } else {
      yield put(noUser(error));
    }
  }
}

export default function* rootSaga() {
  yield fork(handleRequestOAuth);
  yield fork(getUserInfo);
}
