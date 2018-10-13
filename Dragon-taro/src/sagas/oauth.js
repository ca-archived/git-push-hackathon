import { fork, call, put, take, select } from "redux-saga/effects";
import { REQUEST_OAUTH } from "../actions/constants";
import { OAuth } from "oauthio-web";
import { ACCESS_TOKEN } from "../secret";
import { alreadyLogin, successLogin, failureLogin } from "../actions/actions";

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
  } else {
    yield put(failureLogin({ err: error }));
  }
}

function* handleRequestOAuth() {
  while (true) {
    // すでにログインしてたらここでputしてすでにログインしてます的なことを流す
    if (sessionStorage.getItem("access_token")) {
      yield put(alreadyLogin());
    }

    yield take(REQUEST_OAUTH);

    // ここでcallしてもなぜかすり抜けられてしまう
    yield fork(signIn);
  }
}

export default function* rootSaga() {
  yield fork(handleRequestOAuth);
}
