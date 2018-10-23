import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Dashboard } from './texteditor';

export class Team extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teams: localStorage.getItem('TEAM') || null,
      edit: false,
    };

    this.clicked = this.clicked.bind(this);
    this.addmember = this.addmember.bind(this);
    this.addteam = this.addteam.bind(this);
    this.edit = this.edit.bind(this);
  }

  clicked(member) {
    fetch(`https://api.github.com/users/${member}/gists`).then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw new Error('Network response was not ok.');
    }).then((json) => {
      json.sort((a, b) => {
        return (a.updated_at < b.updated_at ? 1 : -1);
      });
      ReactDOM.render('', document.getElementById('area'));
      ReactDOM.render(<Dashboard json={json} />, document.getElementById('area'));
    }).catch((error) => {
      alert(error);
    });
  }

  addteam() {
    const teamname = window.prompt('team name?', '');
    if (teamname) {
      let TEAM = localStorage.getItem('TEAM');
      TEAM = JSON.parse(TEAM) || new Object();
      TEAM[teamname] = [];
      localStorage.setItem('TEAM', JSON.stringify(TEAM));
      this.setState({
        teams: localStorage.getItem('TEAM'),
      });
    }
  }

  addmember(team) {
    let TEAM = localStorage.getItem('TEAM');
    TEAM = JSON.parse(TEAM);
    if (this.state.edit) {
      delete TEAM[team];
      localStorage.setItem('TEAM', JSON.stringify(TEAM));
      this.setState({
        teams: localStorage.getItem('TEAM'),
        edit: false,
      });
    } else {
      const member = window.prompt('members name?', '');
      if (member) {
        TEAM[team].push(member);
        localStorage.setItem('TEAM', JSON.stringify(TEAM));
        this.setState({
          teams: localStorage.getItem('TEAM'),
        });
      }
    }
  }

  edit() {
    this.setState(prev => ({
      edit: !prev.edit
    }));
  }


  render() {
    let teams = this.state.teams;
    console.log(teams);
    const teamlist = [];
    if (teams) {
      teams = JSON.parse(teams);
      Object.keys(teams).forEach((team) => {
        teamlist.push(<h4 onClick={() => this.addmember(team)} className="pointer">
          {`${this.state.edit ? '-' : '+'}　${team}`}
        </h4>);
        teams[team].forEach((member) => {
          teamlist.push(<p onClick={() => this.clicked(member)} className="pointer member">・　{member}</p>);
        });
        teamlist.push(<hr />);
      });
      teamlist.push(<p className="pointer lightletter" onClick={this.edit}>{this.state.edit?'cancel':'break up TEAM'}</p>);
    }
    return (
      <div>
        <h3 onClick={this.addteam} className="pointer">+　TEAM</h3>
        {teamlist}
      </div>
    );
  }
}
