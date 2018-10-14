import React from "react";
import ReactDOM from "react-dom";
import { HashRouter, Route, Switch } from "react-router-dom";
import Header from "./containers/HeaderContainer";
import Home from "./containers/HomeContainer";
import Gist from "./containers/GistContainer";
import { Provider } from "react-redux";
import configureStore from "./store";

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
