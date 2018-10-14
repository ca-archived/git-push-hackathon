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
      user,
      actions: { requestOAuth }
    } = this.props;

    return (
      <div>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
        </ul>
        <If condition={!oauth.isAuthorized}>
          <button onClick={() => requestOAuth()}>GitHubでログイン</button>
        </If>
        <If condition={oauth.isAuthorized && !user.err}>
          <div>
            <p>{user.login}</p>
            <img src={user.avatar_url} alt="ユーザー画像" width="50px" />
          </div>
        </If>
      </div>
    );
  }
}

export default Header;
