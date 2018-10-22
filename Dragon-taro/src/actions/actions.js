import { createAction } from "redux-actions";
import {
  SUCCESS_LOGIN,
  REQUEST_OAUTH,
  FAILURE_LOGIN,
  GET_USER,
  SET_USER,
  NO_USER,
  GET_GISTS,
  SET_GISTS,
  GET_ONE_GIST,
  SET_ONE_GIST,
  SUBMIT_GIST,
  INIT_EDITOR,
  SET_EDITOR_STATE,
  HANDLE_CHANGE_EDITOR,
  LOADING,
  LOADED,
  LOGOUT,
  INITIALIZE
} from "./constants";

// 認証周り
export const initialize = createAction(INITIALIZE);
export const successLogin = createAction(SUCCESS_LOGIN);
export const failureLogin = createAction(FAILURE_LOGIN);
export const requestOAuth = createAction(REQUEST_OAUTH);
export const logout = createAction(LOGOUT);

// User周り
export const getUser = createAction(GET_USER);
export const setUser = createAction(SET_USER);
export const noUser = createAction(NO_USER);

// Gist周り
export const getGists = createAction(GET_GISTS);
export const setGists = createAction(SET_GISTS);
export const getOneGist = createAction(GET_ONE_GIST);
export const setOneGist = createAction(SET_ONE_GIST);
export const submitGist = createAction(SUBMIT_GIST);

// Editor周り
export const initEditor = createAction(INIT_EDITOR);
export const setEditorState = createAction(SET_EDITOR_STATE);
export const handleEditorChange = createAction(HANDLE_CHANGE_EDITOR);

// Load周り
export const loading = createAction(LOADING);
export const loaded = createAction(LOADED);
