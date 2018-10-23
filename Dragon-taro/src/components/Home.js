import React, { Component } from "react";
import { Link } from "react-router-dom";
import moment from "moment";
import Loader from "./parts/Loader";
import { If } from "./parts/If";

class Home extends Component {
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
    const {
      oauth: { isAuthorized },
      load: { isLoading },
      gists: { gists }
    } = this.props;
    const isDisableLaod = gists.length != 0;

    return (
      <div className="m-home">
        <Loader isDisableLaod={isDisableLaod} />
        <If condition={isAuthorized}>
          <ul className="gist-list">{this.gistsList()}</ul>
        </If>
        <If condition={!isAuthorized && !isLoading}>ログインして下さい</If>
      </div>
    );
  }
}

export default Home;
