import React from "react";
import ReactDOM from "react-dom";
import { Router, Route, Switch } from "react-router-dom";
import createHistory from "history/createBrowserHistory";
import { createStore, applyMiddleware } from "redux";
import createSagaMiddleware from "redux-saga";
import { Provider } from "react-redux";
import Header from "./containers/HeaderContainer";
import Home from "./containers/HomeContainer";
import Gist from "./containers/GistContainer";
import NewGist from "./containers/NewGistContainer";
import EditGist from "./containers/EditGistContainer";
import reducer from "./reducers/reducers";
import rootSaga from "./sagas/sagas";

const history = createHistory();

function configureStore(initialState) {
  const sagaMiddleware = createSagaMiddleware();
  const store = createStore(
    reducer,
    initialState,
    applyMiddleware(sagaMiddleware)
  );

  sagaMiddleware.run(rootSaga, { history });
  return store;
}

class App extends React.Component {
  constructor() {
    super();
  }

  render() {
    return (
      <Router history={history}>
        <Provider store={configureStore()}>
          <div>
            <Header />
            <hr />
            <Switch>
              <Route exact path="/" component={Home} />
              <Route path="/gists/new" component={NewGist} />
              <Route path="/gists/:id/edit" component={EditGist} />
              <Route path="/gists/:id" component={Gist} />
            </Switch>
          </div>
        </Provider>
      </Router>
    );
  }
}

ReactDOM.render(<App />, document.getElementById("root"));
