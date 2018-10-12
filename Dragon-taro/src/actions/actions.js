import { createAction } from "redux-actions";
import { SUCCESS_LOGIN, REQUEST_OAUTH } from "./constants";

export const successLogin = createAction(SUCCESS_LOGIN);
export const requestOAuth = createAction(REQUEST_OAUTH);
