import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import {
  Dashboard, Texteditor, Gistcreator, Freeeditor,
} from './texteditor';
import { Status } from './login';
import { Team } from './team';

export class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      avatar_url: '',
      login: '',
      name: '',
    };

    this.auth = this.auth.bind(this);
  }

  dashboard() {
    ReactDOM.render(<Dashboard />, document.getElementById('area'));
  }

  freeeditor() {
    ReactDOM.render(<Freeeditor />, document.getElementById('area'));
  }

  auth(ACCESS_TOKEN) {
    fetch('https://api.github.com/user', {
      method: 'GET',
      headers: {
        Authorization: `token ${ACCESS_TOKEN}`,
        accept: 'application/json',
      },
    }).then((response) => {
      return response.json();
    }).then((json) => {
      console.log(json.login);
      localStorage.setItem('LOGIN', json.login);
      this.setState({
        avatar_url: json.avatar_url,
        login: json.login,
        name: json.name,
      });
    }).catch((error) => {
      alert(error)
    });
  }

  componentDidMount() {
    const urlParam = window.location.search.substring(1) || null;
    const ACCESS_TOKEN = localStorage.getItem('ACCESS_TOKEN') || null;
    if (ACCESS_TOKEN) {
      this.auth(ACCESS_TOKEN);
    } else if (urlParam) {
      const param = urlParam.split('&');
      const paramArray = param.map(e => e.split('='));
      fetch(`http://localhost:5000/login?code=${paramArray[0][1]}`).then((response) => {
        return response.text();
      }).then((text) => {
        localStorage.setItem('ACCESS_TOKEN', text);
        window.location = '/';
      }).catch((error) => {
        alert(error)
      });
    } else {
      console.log('unauthorized');
    }
  }

  render() {
    return (
      <div className="container" id="container">
        <div className="item" id="left">
          <div className="menu">
            <h1 onClick={this.dashboard} id="title" className='pointer'>G-editor</h1>
            <p onClick={this.freeeditor} className="pointer">Markdown editor</p>
            <div id="login" className="pointer">
              <Status avatar_url={this.state.avatar_url} login={this.state.login} name={this.state.name} />
            </div>
            <div id="team">
              <Team />
            </div>
          </div>
        </div>
        <div className="container-inner" id="area">
          <Dashboard />
        </div>
      </div>
    );
  }
}
