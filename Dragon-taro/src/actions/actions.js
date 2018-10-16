import { createAction } from "redux-actions";
import {
  SUCCESS_LOGIN,
  REQUEST_OAUTH,
  ALREADY_LOGIN,
  FAILURE_LOGIN,
  GET_USER,
  SET_USER,
  NO_USER,
  GET_GISTS,
  SET_GISTS,
  GET_ONE_GIST,
  SET_ONE_GIST,
  CREATE_GIST,
  CREATED_GIST
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

// Gist周り
export const getGists = createAction(GET_GISTS);
export const setGists = createAction(SET_GISTS);
export const getOneGist = createAction(GET_ONE_GIST);
export const setOneGist = createAction(SET_ONE_GIST);
export const createGist = createAction(CREATE_GIST);
export const createdGist = createAction(CREATED_GIST);
