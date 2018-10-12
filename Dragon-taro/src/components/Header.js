import React, { Component } from "react";
import { Link } from "react-router-dom";
import oauth from "../reducers/oauth";

class Header extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  handleOAuth() {}

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
        <button onClick={() => requestOAuth()}>GitHubでログイン</button>
      </div>
    );
  }
}

export default Header;
