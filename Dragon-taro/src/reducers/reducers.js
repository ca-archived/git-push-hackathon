import { combineReducers } from "redux";
import { oauth, user } from "./oauth";

export default combineReducers({ oauth, user });
