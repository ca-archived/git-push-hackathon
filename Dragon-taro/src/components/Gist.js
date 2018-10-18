import React, { Component } from "react";
import { Link } from "react-router-dom";

class Gist extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    const {
      params: { id }
    } = this.props.match;
    const gist = this.props.gist[id];
    if (!gist) {
      this.props.actions.getOneGist({ id: id });
    }
  }

  render() {
    const {
      params: { id }
    } = this.props.match;
    // const gist = this.props.gist[id];

    return (
      <div>
        <Loader />
        <Link to={`/gists/${id}/edit`}>Edit</Link>
      </div>
    );
  }
}

export default Gist;
