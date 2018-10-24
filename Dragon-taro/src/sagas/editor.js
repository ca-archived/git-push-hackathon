import { fork, call, put, take, select } from "redux-saga/effects";
import { INIT_EDITOR, HANDLE_CHANGE_EDITOR } from "../actions/constants";
import api from "./utils/api";
import reshapeGist from "./utils/reshapeGist";
import { setOneGist, setEditorState, loaded } from "../actions/actions";

const initEditorState = {
  description: "",
  public: true,
  files: [{ index: 0, filename: "", content: "" }]
};

const selectGist = state => state.gist;
const selectEditor = state => state.editor;

function getBackup(type, id = null) {
  const key = type == "create" ? "create" : id;
  const buckupedGist = JSON.parse(localStorage.getItem(key));

  const hasDraft =
    buckupedGist &&
    (buckupedGist.description ||
      buckupedGist.files[0].filename ||
      buckupedGist.files[0].filename);
  const useDraft = hasDraft ? confirm("Use your gist draft?") : false;
  return { useDraft, buckupedGist };
}

function* handleInitEditor() {
  while (true) {
    const {
      payload: { type, id }
    } = yield take(INIT_EDITOR);

    if (type == "edit") {
      const gist = yield select(selectGist);
      const { useDraft, buckupedGist } = getBackup(type, id);
      let targetGist = {};

      if (useDraft) {
        targetGist = buckupedGist;
      } else if (!gist[id]) {
        const { resp, error } = yield call(api, `gists/${id}`);
        if (!error) {
          targetGist = reshapeGist(resp);
          yield put(setOneGist({ ...gist, [id]: targetGist }));
        }
      } else {
        targetGist = gist[id];
      }

      yield put(setEditorState(targetGist));
    } else {
      const { useDraft, buckupedGist } = getBackup(type);
      if (useDraft) {
        yield put(setEditorState(buckupedGist));
      } else {
        yield put(setEditorState(initEditorState));
      }
    }
    yield put(loaded());
  }
}

function* createBackup() {
  while (true) {
    const {
      payload: { type, id }
    } = yield take(HANDLE_CHANGE_EDITOR);
    const key = type == "create" ? "create" : id;
    const editor = yield select(selectEditor);

    localStorage.setItem(key, JSON.stringify(editor));
  }
}

export default function* rootSaga(history) {
  yield fork(handleInitEditor, history);
  yield fork(createBackup, history);
}
