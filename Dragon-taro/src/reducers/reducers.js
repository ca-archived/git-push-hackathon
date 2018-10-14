import { combineReducers } from "redux";
import { oauth, user } from "./oauth";
import { gists } from "./gists";

export default combineReducers({ oauth, user, gists });
