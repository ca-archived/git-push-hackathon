import { fork } from "redux-saga/effects";
import oauth from "./oauth";
import gists from "./gists";

export default function* rootSaga({ history }) {
  yield fork(oauth, history);
  yield fork(gists, history);
}
