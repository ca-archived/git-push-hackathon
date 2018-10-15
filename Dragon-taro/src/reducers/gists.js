import { SET_GISTS, SET_ONE_GIST } from "../actions/constants";

const initial = {
  gists: { gists: [] },
  gist: {}
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
      return { [payload.id]: payload };
  }

  return state;
}
