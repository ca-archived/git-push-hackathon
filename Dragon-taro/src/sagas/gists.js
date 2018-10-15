import { fork, call, put, take } from "redux-saga/effects";
import { GET_GISTS, GET_ONE_GIST } from "../actions/constants";
import { Get } from "./api";
import { setGists, setOneGist } from "../actions/actions";

function* handleGetGists() {
  while (true) {
    yield take(GET_GISTS);

    const { resp, error } = yield call(Get, "gists");
    if (!error) {
      yield put(setGists(resp));
    }
  }
}

function* handleGetOneGist() {
  while (true) {
    const {
      payload: { id }
    } = yield take(GET_ONE_GIST);

    const { resp, error } = yield call(Get, `gists/${id}`);
    if (!error) {
      yield put(setOneGist(resp));
    }
  }
}

export default function* rootSaga() {
  yield fork(handleGetGists);
  yield fork(handleGetOneGist);
}
