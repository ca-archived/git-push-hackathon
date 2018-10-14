import { fork } from "redux-saga/effects";
import oauth from "./oauth";
import gists from "./gists";

export default function* rootSaga() {
  yield fork(oauth);
  yield fork(gists);
}
