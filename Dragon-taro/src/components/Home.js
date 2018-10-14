import React, { Component } from "react";

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
              <div>{gist.description}</div>
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
