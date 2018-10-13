import { createAction } from "redux-actions";
import {
  SUCCESS_LOGIN,
  REQUEST_OAUTH,
  ALREADY_LOGIN,
  FAILURE_LOGIN,
  GET_USER,
  SET_USER,
  NO_USER
} from "./constants";

// 認証周り
export const successLogin = createAction(SUCCESS_LOGIN);
export const failureLogin = createAction(FAILURE_LOGIN);
export const requestOAuth = createAction(REQUEST_OAUTH);
export const alreadyLogin = createAction(ALREADY_LOGIN);

// User周り
export const getUser = createAction(GET_USER);
export const setUser = createAction(SET_USER);
export const noUser = createAction(NO_USER);
