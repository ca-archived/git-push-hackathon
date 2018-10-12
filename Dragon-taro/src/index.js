import React from "react";
import ReactDOM from "react-dom";
import { HashRouter, Route, Switch } from "react-router-dom";
import Header from "./containers/HeaderContainer";
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
            <Route path="/about" component={About} />
            <Route path="/friends" component={Friends} />
          </Switch>
        </div>
      </HashRouter>
    );
  }
}

class Home extends React.Component {
  constructor() {
    super();
  }

  render() {
    return (
      <div>
        <h2>Home</h2>
        <p>home</p>
      </div>
    );
  }
}

const About = () => (
  <div>
    <h2>About</h2>
    <p>フレンズに投票するページです</p>
  </div>
);
const Friends = () => (
  <div>
    <h2>Friends</h2>
    <p>ここにフレンズのリストを書きます</p>
  </div>
);

ReactDOM.render(
  <Provider store={configureStore()}>
    <App />
  </Provider>,
  document.getElementById("root")
);
