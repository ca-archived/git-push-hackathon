import {
  SET_GISTS,
  SET_ONE_GIST,
  SET_EDITOR_STATE,
  HANDLE_CHANGE_EDITOR
} from "../actions/constants";

const initial = {
  gists: { gists: [] },
  gist: {},
  editor: {
    files: []
  }
};

export function gists(state = initial.gists, { type, payload }) {
  switch (type) {
    case SET_GISTS:
      return { gists: payload };
  }

  return state;
}

export function gist(state = initial.gist, { type, payload }) {
  switch (type) {
    case SET_ONE_GIST:
      return { ...state, [payload.id]: payload };
  }

  return state;
}

export function editor(state = initial.editor, { type, payload }) {
  switch (type) {
    case SET_EDITOR_STATE:
      return { ...payload };
    case HANDLE_CHANGE_EDITOR:
      return { ...state, ...payload };
  }

  return state;
}
