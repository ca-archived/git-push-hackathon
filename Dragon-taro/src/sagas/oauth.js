import { fork, call, put, take, select } from "redux-saga/effects";
import { REQUEST_OAUTH, SUCCESS_LOGIN } from "../actions/constants";
import { OAuth } from "oauthio-web";
import { ACCESS_TOKEN } from "../secret";

function signIn() {
  OAuth.initialize(ACCESS_TOKEN);
  OAuth.popup("github")
    .done(function(result) {
      const json = result.toJson();
      console.log(json);

      sessionStorage.setItem("access_token", json.access_token);
      return null;
    })
    .fail(function(err) {
      return err;
    });
}

function* handleRequestOAuth() {
  while (true) {
    // すでにログインしてたらここでputしてすでにログインしてます的なことを流す
    if (sessionStorage.getItem("access_token")) {
      console.log("already loged in.");

      //   put(ALREADY_LOGIN);
    }

    // oauthの認証を叩く
    yield take(REQUEST_OAUTH);

    const err = yield call(signIn);
    if (!err) {
      put(SUCCESS_LOGIN);
    } else {
      console.log("failure login.");
      //   put(FAILURE_LOGIN);
    }
  }
}

export default function* rootSaga() {
  yield fork(handleRequestOAuth);
}
