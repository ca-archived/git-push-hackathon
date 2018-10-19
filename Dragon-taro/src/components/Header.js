import React, { Component } from "react";
import { Link } from "react-router-dom";
import { If } from "./parts/If";

const Header = ({ oauth, user, actions: { requestOAuth } }) => {
  return (
    <div className="m-header">
      <div className="inner-wrapper">
        <div className="title">
          <Link to="/">Fast Gist</Link>
        </div>
        <If condition={!oauth.isAuthorized}>
          <button className="p-button" onClick={() => requestOAuth()}>
            GitHubでログイン
          </button>
        </If>
        <If condition={oauth.isAuthorized && user.avatar_url}>
          <div>
            <button className="p-button">
              <Link to="/gists/new">New Gist</Link>
            </button>
            <img src={user.avatar_url} alt="ユーザー画像" />
          </div>
        </If>
      </div>
    </div>
  );
};

export default Header;
