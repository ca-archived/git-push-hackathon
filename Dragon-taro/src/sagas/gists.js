import { fork, call, put, take } from "redux-saga/effects";
import { GET_GISTS } from "../actions/constants";
import { Get } from "./api";
import { setGists } from "../actions/actions";

function* handleGetGists() {
  while (true) {
    yield take(GET_GISTS);

    console.log(GET_GISTS);

    const { payload, error } = yield call(Get, "gists");
    if (!error) {
      yield put(setGists(payload));
    }
  }
}

export default function* rootSaga() {
  yield fork(handleGetGists);
}
