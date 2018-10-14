import { SET_GISTS } from "../actions/constants";

const initial = {
  gists: []
};

export function gists(state = initial.gists, { type, payload }) {
  switch (type) {
    case SET_GISTS:
      return { gists: payload };
  }

  return state;
}
