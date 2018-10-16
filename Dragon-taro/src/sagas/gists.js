import { fork, call, put, take } from "redux-saga/effects";
import { GET_GISTS, GET_ONE_GIST, CREATE_GIST } from "../actions/constants";
import { Get, Post } from "./api";
import { setGists, setOneGist } from "../actions/actions";

function createBody(data) {
  let files = {};
  data.files.map(f => {
    files[f.file] = { content: f.content };
  });
  return { description: data.description, public: data.public, files: files };
}

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

function* handleCreateGist() {
  while (true) {
    const {
      payload: { data }
    } = yield take(CREATE_GIST);

    const { resp, error } = yield call(Post, "gists", createBody(data));
    if (!error) {
      console.log(resp);
    }
  }
}

export default function* rootSaga() {
  yield fork(handleGetGists);
  yield fork(handleGetOneGist);
  yield fork(handleCreateGist);
}
