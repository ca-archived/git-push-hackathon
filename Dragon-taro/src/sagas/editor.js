import { fork, call, put, take, select } from "redux-saga/effects";
import { INIT_EDITOR, HANDLE_CHANGE_EDITOR } from "../actions/constants";
import api from "./api";
import { setOneGist, setEditorState, loaded } from "../actions/actions";

const initEditorState = {
  description: "",
  public: true,
  files: [{ index: 0, filename: "", content: "" }]
};

const selectGist = state => state.gist;
const selectEditor = state => state.editor;

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

function* handleInitEditor() {
  while (true) {
    const {
      payload: { type, id }
    } = yield take(INIT_EDITOR);

    if (type == "edit") {
      const gist = yield select(selectGist);
      let targetGist = {};

      if (!gist[id]) {
        const { resp, error } = yield call(api, `gists/${id}`);
        if (!error) {
          targetGist = reshapeGist(resp);
        }
      } else {
        targetGist = gist[id];
      }

      const newGist = { ...gist, [id]: targetGist };
      yield put(setOneGist(newGist));
      yield put(setEditorState(targetGist));
    } else {
      const editor = JSON.parse(localStorage.getItem("editor"));

      const hasDraft =
        editor &&
        (editor.description ||
          editor.files[0].filename ||
          editor.files[0].filename);
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
  yield fork(handleInitEditor, history);
  yield fork(createBackUp, history);
}
