import { fork, call, put, take, select } from "redux-saga/effects";
import {
  GET_GISTS,
  GET_ONE_GIST,
  SUBMIT_GIST,
  INIT_EDITOR
} from "../actions/constants";
import { Get, Post } from "./api";
import { setGists, setOneGist, setEditorState } from "../actions/actions";

function createBody(data) {
  let files = {};
  data.files.map(f => {
    files[f.file] = { content: f.content };
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
      yield put(setOneGist(reshapeGist(resp)));
    }
  }
}

function* handleSubmitGist() {
  while (true) {
    const {
      payload: { data, type }
    } = yield take(SUBMIT_GIST);

    // 場合分けしてgistのpatchを投げる
    const { resp, error } = yield call(Post, "gists", createBody(data));
    if (!error) {
      yield put(setOneGist(resp));
    }
  }
}

function* handleInitEditor() {
  while (true) {
    const {
      payload: { type, id } // type = "new" or "edit"
    } = yield take(INIT_EDITOR);

    if (type == "edit") {
      // 編集用のgistをset
      const editingGist = yield gist[id] || call(Get, `gist/${id}`); // 要検討

      // editorのstateをset
      // yield put(setEditorState);
    } else {
      const initState = {
        description: "",
        public: true,
        files: [{ index: 0, filename: "", content: "" }]
      };

      // editorのstateをset
      // yield put(setEditorState);
    }
  }
}

export default function* rootSaga() {
  yield fork(handleGetGists);
  yield fork(handleGetOneGist);
  yield fork(handleSubmitGist);
  yield fork(handleInitEditor);
}
