import React, { Component } from "react";
import { Link } from "react-router-dom";
import moment from "moment";
import Loader from "./parts/Loader";

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

  fileList(gist) {
    let fileList = [];
    for (let file in gist.files) {
      const wrappedFile = <span key={file}>{file}</span>;
      fileList.push(wrappedFile);
    }

    return fileList;
  }

  gistsList() {
    const {
      gists: { gists }
    } = this.props;

    const gistsList = gists.length
      ? gists.map(gist => {
          const date = moment(gist.created_at).format("YYYY.MM.DD");
          return (
            <li key={gist.id} onMouseEnter={() => this.getGist(gist.id)}>
              <Link to={`/gists/${gist.id}`}>
                <h2>{gist.description}</h2>
                <div className="details">
                  <div className="file-list">{this.fileList(gist)}</div>
                  <div className="date">
                    <span>{date}</span>
                  </div>
                </div>
              </Link>
            </li>
          );
        })
      : null;

    return gistsList;
  }

  render() {
    const isDisableLaod = this.props.gists.gists.length != 0;

    return (
      <div className="m-home">
        <Loader isDisableLaod={isDisableLaod} />
        <ul className="gist-list">{this.gistsList()}</ul>
      </div>
    );
  }
}

export default Home;
