import { LOADING, LOADED } from "../actions/constants";

const initial = {
  isLoading: true
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
