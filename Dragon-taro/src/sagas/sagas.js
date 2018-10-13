import { fork } from "redux-saga/effects";
import oauth from "./oauth";

export default function* rootSaga() {
  yield fork(oauth);
}
