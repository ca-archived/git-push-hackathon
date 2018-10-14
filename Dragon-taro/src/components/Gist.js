import React, { Component } from "react";

class Gist extends Component {
  constructor() {
    super();
  }

  //   componentDidMount() {
  //     this.props.actions.getGists();
  //   }

  render() {
    const { params } = this.props.match;

    return <div>{params.id}</div>;
  }
}

export default Gist;
