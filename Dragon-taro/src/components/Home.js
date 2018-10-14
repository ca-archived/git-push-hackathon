import React, { Component } from "react";
import { Link } from "react-router-dom";

class Home extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.props.actions.getGists();
  }

  gistsList() {
    const {
      gists: { gists }
    } = this.props;

    const gistsList = gists.length
      ? gists.map(gist => {
          return (
            <li key={gist.id}>
              <Link to={`/gists/${gist.id}`}>
                <div>{gist.description}</div>
                <div>{gist.created_at}</div>
              </Link>
            </li>
          );
        })
      : null;

    return gistsList;
  }

  render() {
    return (
      <div>
        <ul>{this.gistsList()}</ul>
      </div>
    );
  }
}

export default Home;
