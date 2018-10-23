import React, { Component } from 'react';

export class Navbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: '0',
    };

    this.move = this.move.bind(this);
  }

  move(x) {
    document.getElementById('container').style.left = x;
    this.setState({ selected: x });
  }

  render() {
    return (
      <div id="nav-container">
        <div onClick={() => { this.move('0'); }} className={this.state.selected === '0' ? 'nav-item nav-selected' : 'nav-item'} />
        <div onClick={() => { this.move('-100%'); }} className={this.state.selected === '-100%' ? 'nav-item nav-selected' : 'nav-item'} />
        <div onClick={() => { this.move('-200%'); }} className={this.state.selected === '-200%' ? 'nav-item nav-selected' : 'nav-item'} />
      </div>
    );
  }
}
