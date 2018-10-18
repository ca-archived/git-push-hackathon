import React, { Component } from "react";
import { Link } from "react-router-dom";

class Home extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    if (this.props.oauth.isAuthorized) this.props.actions.getGists();
  }

  getGist(id) {
    const gist = this.props.gist[id];
    if (!gist) {
      this.props.actions.getOneGist({ id: id });
    }
  }

  gistsList() {
    const {
      gists: { gists }
    } = this.props;

    const gistsList = gists.length
      ? gists.map(gist => {
          return (
            <li key={gist.id} onMouseEnter={() => this.getGist(gist.id)}>
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
