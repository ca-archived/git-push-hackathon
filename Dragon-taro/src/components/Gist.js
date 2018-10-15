import React, { Component } from "react";

class Gist extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    const {
      params: { id }
    } = this.props.match;

    this.props.actions.getOneGist({ id: id });
  }

  render() {
    const {
      params: { id }
    } = this.props.match;
    const gist = this.props.gist[id];

    return <div>{gist ? gist.url : null}</div>;
  }
}

export default Gist;
