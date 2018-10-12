import React, { Component } from "react";
import { Link } from "react-router-dom";

class Header extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  handleOAuth() {}

  render() {
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
        <button onClick={() => this.handleOAuth()}>GitHubでログイン</button>
      </div>
    );
  }
}

export default Header;
