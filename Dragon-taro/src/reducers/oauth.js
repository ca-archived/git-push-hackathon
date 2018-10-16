import {
  REQUEST_OAUTH,
  SUCCESS_LOGIN,
  ALREADY_LOGIN,
  FAILURE_LOGIN,
  SET_USER,
  NO_USER
} from "../actions/constants";

const initial = {
  ouath: {
    isAuthorized: false,
    err: null
  },
  user: { err: null }
};

export function oauth(state = initial.ouath, { type, payload }) {
  switch (type) {
    case REQUEST_OAUTH:
      return { ...state, isAuthorized: false, err: null };
    case SUCCESS_LOGIN:
      return { ...state, isAuthorized: true, err: null };
    case FAILURE_LOGIN:
      return { ...state, isAuthorized: true, err: payload.err };
  }

  return state;
}

export function user(state = initial.user, { type, payload }) {
  switch (type) {
    case SET_USER:
      return { ...payload, err: null };
    case NO_USER:
      return { err: payload };
  }

  return state;
}
