import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Texteditor } from './texteditor';

export class Btn extends Component {
  constructor(props) {
    super(props);

    this.clicked = this.clicked.bind(this);
  }

  clicked() {
    if (this.props.to === 'edit') {
      ReactDOM.render(<Texteditor text={this.props.text} gist_id={this.props.gist_id} filename={this.props.filename} />, document.getElementById('area'));
    } else if (this.props.to === 'Fork') {
      if (window.confirm('Fork?')) {
        fetch(`https://api.github.com/gists/${this.props.gist_id}/forks`, {
          method: 'POST',
          headers: {
            Authorization: `token ${localStorage.getItem('ACCESS_TOKEN')}`,
            accept: 'application/json',
          },
        }).then((response) => {
          return response.json();
        }).then((json) => {
          window.location = '/';
        }).catch((error) => {
          alert(error)
        });
      }
    }
  }

  render() {
    return (
      <div className="btn pointer" id="btn" onClick={this.clicked}>{this.props.to}</div>
    );
  }
}
