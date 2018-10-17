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

function reshapeGist(gist) {
  let files = [];
  let index = 0;
  for (let name in gist.files) {
    const file = {
      file: name,
      content: gist.files[name].content,
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

function* handleCreateGist() {
  while (true) {
    const {
      payload: { data }
    } = yield take(CREATE_GIST);

    const { resp, error } = yield call(Post, "gists", createBody(data));
    if (!error) {
      yield put(setOneGist(resp));
    }
  }
}

function* handleInitEditor() {
  while (true) {
    // editorに必要なstateの初期化を行う
    // newならinitのstateでeditならそのページのgistをreducerに投げる（だからforkしてる）
    // 必要なactionはsetEditorState的なやつとtakeするINIT_EDITOR
  }
}

function* handleEditGist() {
  while (true) {
    // gistのpatchを投げる
    // 成功したらsetOneGist()
    // takeするEDIT_GISTのみ必要
  }
}

export default function* rootSaga() {
  yield fork(handleGetGists);
  yield fork(handleGetOneGist);
  yield fork(handleCreateGist);
  // yield fork(handleInitEditor);
  // yield fork(handleEditGist);
}
