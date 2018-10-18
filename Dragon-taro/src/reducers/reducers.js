import { combineReducers } from "redux";
import { oauth, user } from "./oauth";
import { gists, gist, editor } from "./gists";
import { load } from "./load";

export default combineReducers({ oauth, user, gists, gist, editor, load });
