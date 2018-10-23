import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Gistcreator } from './texteditor';

export class Status extends Component {
  constructor(props) {
    super(props);

    this.logout = this.logout.bind(this);
  }

  logout() {
    fetch(`http://localhost:5000/revoke?access_token=${localStorage.getItem('ACCESS_TOKEN')}`).then((response) => {
      return response.text();
    }).then((response) => {
      console.log(response);
      localStorage.removeItem('ACCESS_TOKEN');
      localStorage.removeItem('LOGIN');
      window.location = '/';
    }).catch((error) => {
      alert(error)
    });
  }

  gistcreator() {
    ReactDOM.render(<Gistcreator />, document.getElementById('area'));
  }

  render() {
    if (this.props.login) {
      return (
        <div>
          <div className="container-login">
            <div className="item-login-1">
              <img className="img-login" src={this.props.avatar_url} alt="avator" />
            </div>
            <div className="item-login-2">
              <div>&nbsp;{this.props.login}</div>
              <div>&nbsp;{this.props.name}</div>
            </div>
          </div>
          <div onClick={this.logout} className="menu-btn pointer">logout</div>
          <div onClick={this.gistcreator} className="menu-btn pointer">create gist</div>
        </div>
      );
    }
    return (
      <a href="https://github.com/login/oauth/authorize?client_id=7b721a39564ef42cf585&scope=gist" className="menu-btn pointer">login</a>
    );
  }
}
