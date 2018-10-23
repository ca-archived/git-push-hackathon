import { fork, call, put, take, select } from "redux-saga/effects";
import {
  GET_GISTS,
  GET_ONE_GIST,
  SUBMIT_GIST,
  INIT_EDITOR,
  HANDLE_CHANGE_EDITOR
} from "../actions/constants";
import { Get, Send } from "./api";
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

function createBody(data) {
  let files = {};
  data.files.map(f => {
    files[f.filename] = { content: f.content };
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
    const { resp, error } = yield call(Get, "gists");
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

    const { resp, error } = yield call(Get, `gists/${id}`);
    if (!error) {
      yield put(setOneGist(reshapeGist(resp)));
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
    const path = method == "POST" ? "gists" : `gists/${editor.id}`;
    const { resp, error } = yield call(Send, path, createBody(editor), method);

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

function* handleInitEditor() {
  while (true) {
    const {
      payload: { type, id }
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
      const editor = yield JSON.parse(localStorage.getItem("editor")) ||
        select(selectEditor);

      const hasDraft =
        editor.description ||
        editor.files[0].filename ||
        editor.files[0].filename;
      const useDraft = hasDraft ? confirm("Use your gist draft?") : false;

      if (!useDraft) {
        yield put(setEditorState(initEditorState));
      } else {
        yield put(setEditorState(editor));
      }
    }
    yield put(loaded());
  }
}

function* createBackUp() {
  while (true) {
    const {
      payload: { type }
    } = yield take(HANDLE_CHANGE_EDITOR);

    if (type == "create") {
      const editor = yield select(selectEditor);
      localStorage.setItem("editor", JSON.stringify(editor));
    }
  }
}

export default function* rootSaga(history) {
  yield fork(handleGetGists, history);
  yield fork(handleGetOneGist, history);
  yield fork(handleSubmitGist, history);
  yield fork(handleInitEditor, history);
  yield fork(createBackUp, history);
}
