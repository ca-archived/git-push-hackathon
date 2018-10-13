import React, { Component } from "react";
import { Link } from "react-router-dom";
import { If } from "./If";

class Header extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  render() {
    const {
      oauth,
      actions: { requestOAuth }
    } = this.props;

    return (
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
        <If condition={!oauth.isAuthorized}>
          <button onClick={() => requestOAuth()}>GitHubでログイン</button>
        </If>
      </div>
    );
  }
}

export default Header;
