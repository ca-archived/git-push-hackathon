import { LOADING, LOADED } from "../actions/constants";

const initial = {
  isLoading: true
};

// get~系のときにtrueにset~系のときにfalseに戻すようにしたらいいのかも
export function load(state = initial, { type }) {
  console.log(type);
  switch (type) {
    case LOADING:
      return { isLoading: true };
    case LOADED:
      return { isLoading: false };
  }

  return state;
}
