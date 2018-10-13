import { fork, call, put, take, select } from "redux-saga/effects";
import { REQUEST_OAUTH, SUCCESS_LOGIN } from "../actions/constants";
import { OAuth } from "oauthio-web";
import { ACCESS_TOKEN } from "../secret";
import { alreadyLogin, successLogin } from "../actions/actions";

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
    yield put(successLogin("hi"));
  } else {
    // errorがnullじゃないからそれをstateに流す
    //   put(FAILURE_LOGIN);
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
