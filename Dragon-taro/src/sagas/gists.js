import { fork, call, put, take, select } from "redux-saga/effects";
import {
  GET_GISTS,
  GET_ONE_GIST,
  SUBMIT_GIST,
  INIT_EDITOR
} from "../actions/constants";
import { Get, Send } from "./api";
import {
  setGists,
  setOneGist,
  setEditorState,
  loading,
  loaded
} from "../actions/actions";

const selectGist = state => state.gist;
const selectEditor = state => state.editor;

function createBody(data) {
  let files = {};
  data.files.map(f => {
    files[f.filename] = { content: f.content };
  });
  return { description: data.description, public: data.public, files: files };
}

function reshapeGist(gist) {
  let files = [];
  let index = 0;
  for (let name in gist.files) {
    const file = {
      ...gist.files[name],
      index: index
    };
    files.push(file);
    index++;
  }
  return { ...gist, files: files };
}

function* handleGetGists() {
  while (true) {
    const { payload } = yield take(GET_GISTS);

    yield put(loading());
    const { resp, error } = yield call(Get, "gists", payload);
    if (!error) {
      yield put(setGists(resp));
    }
    yield put(loaded());
  }
}

function* handleGetOneGist() {
  while (true) {
    const {
      payload: { id }
    } = yield take(GET_ONE_GIST);

    const { resp, error } = yield call(Get, `gists/${id}`);
    if (!error) {
      yield put(setOneGist(reshapeGist(resp)));
    }
  }
}

function* handleSubmitGist(history) {
  while (true) {
    const {
      payload: { method }
    } = yield take(SUBMIT_GIST);

    const editor = yield select(selectEditor);
    const path = method == "POST" ? "gists" : `gists/${editor.id}`;
    const { resp, error } = yield call(Send, path, createBody(editor), method);
    if (!error) {
      yield put(setOneGist(reshapeGist(resp)));
      yield call(history.push, `/gists/${resp.id}`);
    }
  }
}

function* handleInitEditor() {
  while (true) {
    const {
      payload: { type, id } // type = "new" or "edit"
    } = yield take(INIT_EDITOR);

    if (type == "edit") {
      const gist = yield select(selectGist);
      let targetGist = {};

      if (!gist[id]) {
        const { resp, error } = yield call(Get, `gists/${id}`);
        if (!error) {
          targetGist = reshapeGist(resp);
        }
      } else {
        targetGist = gist[id];
      }

      yield put(setEditorState(targetGist));
    } else {
      const initState = {
        description: "",
        public: true,
        files: [{ index: 0, filename: "", content: "" }]
      };

      yield put(setEditorState(initState));
    }
    yield put(loaded());
  }
}

export default function* rootSaga(history) {
  yield fork(handleGetGists, history);
  yield fork(handleGetOneGist, history);
  yield fork(handleSubmitGist, history);
  yield fork(handleInitEditor, history);
}
