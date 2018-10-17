import React from "react";
import ReactDOM from "react-dom";
import { HashRouter, Route, Switch } from "react-router-dom";
import Header from "./containers/HeaderContainer";
import Home from "./containers/HomeContainer";
import Gist from "./containers/GistContainer";
import { Provider } from "react-redux";
import configureStore from "./store";
import NewGist from "./containers/NewGistContainer";
import EditGist from "./containers/EditGistContainer";

class App extends React.Component {
  constructor() {
    super();
  }

  render() {
    return (
      <HashRouter>
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
      </HashRouter>
    );
  }
}

ReactDOM.render(
  <Provider store={configureStore()}>
    <App />
  </Provider>,
  document.getElementById("root")
);
