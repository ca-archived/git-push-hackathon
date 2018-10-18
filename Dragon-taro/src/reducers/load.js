import { LOADING, LOADED } from "../actions/constants";

const initial = {
  isLoading: false
};

export function load(state = initial, { type }) {
  switch (type) {
    case LOADING:
      return { isLoading: true };
    case LOADED:
      return { isLoading: false };
  }

  return state;
}
