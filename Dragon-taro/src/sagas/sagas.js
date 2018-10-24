import { fork } from "redux-saga/effects";
import oauth from "./oauth";
import gists from "./gists";
import editor from "./editor";

export default function* rootSaga({ history }) {
  yield fork(oauth, history);
  yield fork(gists, history);
  yield fork(editor, history);
}
