import { fork, take, put } from "redux-saga/effects";
import oauth from "./oauth";

export default function* rootSaga() {
  yield fork(oauth);
}
