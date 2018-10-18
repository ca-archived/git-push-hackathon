import { fork } from "redux-saga/effects";
import oauth from "./oauth";
import gists from "./gists";

export default function* rootSaga(context) {
  yield fork(oauth, context);
  yield fork(gists, context);
}
