import { fork, call, put, take, select } from "redux-saga/effects";
import {
  GET_GISTS,
  GET_ONE_GIST,
  SUBMIT_GIST,
  DELETE_GIST
} from "../actions/constants";
import api from "./api";
import {
  setGists,
  setOneGist,
  setEditorState,
  loading,
  loaded
} from "../actions/actions";
import toastr from "toastr";

const initEditorState = {
  description: "",
  public: true,
  files: [{ index: 0, filename: "", content: "" }]
};

const selectGist = state => state.gist;
const selectEditor = state => state.editor;

function reshapeFiles(files) {
  let newFiles = {};
  files.map(f => {
    newFiles[f.filename] = { content: f.content };
  });
  return newFiles;
}

function createBody(data) {
  return {
    description: data.description,
    public: data.public,
    files: reshapeFiles(data.files)
  };
}

function updateBody(data, gist) {
  const filenames = gist[data.id].files.map(f => f.filename);
  let files = reshapeFiles(data.files);
  filenames.map(filename => {
    files[filename] = files[filename] || null;
  });

  return { description: data.description, public: data.public, files };
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
    yield take(GET_GISTS);

    yield put(loading());
    const { resp, error } = yield call(api, "gists");
    if (!error) {
      yield put(setGists(resp));
    } else {
      toastr.error(error);
    }
    yield put(loaded());
  }
}

function* handleGetOneGist() {
  while (true) {
    const {
      payload: { id }
    } = yield take(GET_ONE_GIST);

    const { resp, error } = yield call(api, `gists/${id}`);
    if (!error) {
      const gist = yield select(selectGist);
      yield put(setOneGist({ ...gist, [resp.id]: reshapeGist(resp) }));
    } else {
      toastr.error(error);
    }
    yield put(loaded());
  }
}

function* handleSubmitGist(history) {
  while (true) {
    const {
      payload: { method }
    } = yield take(SUBMIT_GIST);

    yield put(loading());
    const editor = yield select(selectEditor);
    const gist = yield select(selectGist);
    const path = method == "POST" ? "gists" : `gists/${editor.id}`;
    const body =
      method == "POST" ? createBody(editor) : updateBody(editor, gist);
    const { resp, error } = yield call(api, path, method, body);

    if (!error) {
      yield put(setOneGist(reshapeGist(resp)));
      yield call(history.push, `/gists/${resp.id}`);
      localStorage.removeItem("editor");
      yield put(setEditorState(initEditorState));
    } else {
      toastr.error(error);
    }
    yield put(loaded());
  }
}

function* handleDeleteGist(history) {
  while (true) {
    const {
      payload: { id }
    } = yield take(DELETE_GIST);

    const { error } = yield call(api, `gists/${id}`, "DELETE");
    if (!error) {
      const gist = yield select(selectGist);
      let newGist = { ...gist };
      delete newGist[id];

      yield put(setOneGist(newGist));
      yield call(history.push, "/");
    } else {
      toastr.error(error);
    }
  }
}

export default function* rootSaga(history) {
  yield fork(handleGetGists, history);
  yield fork(handleGetOneGist, history);
  yield fork(handleSubmitGist, history);
  yield fork(handleDeleteGist, history);
}
