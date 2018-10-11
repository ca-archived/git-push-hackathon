import React from "react";
import ReactDOM from "react-dom";
import { OAuth } from "oauthio-web";
import { HashRouter, Route, Link, Switch } from "react-router-dom";

class App extends React.Component {
  constructor() {
    super();
  }

  render() {
    return (
      <HashRouter>
        <div>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>

            <li>
              <Link to="/about">About</Link>
            </li>

            <li>
              <Link to="/friends">Friends</Link>
            </li>
          </ul>
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
    OAuth.initialize("ACCESS_TOKEN");
    OAuth.popup("github").done(function(result) {
      self.setState(result);
    });
  }

  render() {
    console.log(proccess.env.ACCESS_TOKEN);

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
