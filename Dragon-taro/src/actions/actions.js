import { createAction } from "redux-actions";
import { SUCCESS_LOGIN, REQUEST_OAUTH, ALREADY_LOGIN } from "./constants";

export const successLogin = createAction(SUCCESS_LOGIN);
export const requestOAuth = createAction(REQUEST_OAUTH);
export const alreadyLogin = createAction(ALREADY_LOGIN);
