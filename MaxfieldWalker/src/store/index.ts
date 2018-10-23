import { createStore, applyMiddleware } from "redux";
import createSagaMiddleware from "redux-saga";
// import saga from "../saga";

import { reducer } from "../reducers/index";

export interface AppState {
  accessToken: string;
}

const initialState: AppState = {
  accessToken: ""
};

const sagaMiddleware = createSagaMiddleware();

export const store = createStore(
  reducer as any,
  initialState,
  applyMiddleware(sagaMiddleware)
);

// sagaMiddleware.run(saga);

store.subscribe(() => {
  console.log(store.getState());
});
