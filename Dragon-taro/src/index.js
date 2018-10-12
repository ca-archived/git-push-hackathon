import React from "react";
import ReactDOM from "react-dom";
import { OAuth } from "oauthio-web";
import { HashRouter, Route, Switch } from "react-router-dom";
import { ACCESS_TOKEN } from "./secret";
import { HeaderContainer } from "./containers/HeaderContainer";

class App extends React.Component {
  constructor() {
    super();
  }

  render() {
    return (
      <HashRouter>
        <div>
          <HeaderContainer />
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

  handleOAuth() {
    const self = this;
    OAuth.initialize(ACCESS_TOKEN);
    OAuth.popup("github").done(function(result) {
      self.setState(result.toJson());
    });
  }

  render() {
    return (
      <div>
        <h2>Home</h2>
        <p>home</p>
        <button onClick={() => this.handleOAuth()}>GitHubでログイン</button>
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

ReactDOM.render(<App />, document.getElementById("root"));
