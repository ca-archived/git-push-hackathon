import { combineReducers } from "redux";
import { oauth, user } from "./oauth";
import { gists, gist } from "./gists";

export default combineReducers({ oauth, user, gists, gist });
