import React, { Component } from "react";
import { Link } from "react-router-dom";
import Loader from "./Loader";
import { If } from "./If";

class Gist extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    const { gist, id } = this.getGist();

    if (!gist) {
      this.props.actions.getOneGist({ id: id });
    }
  }

  getGist() {
    const {
      params: { id }
    } = this.props.match;
    const gist = this.props.gist[id] || false; // ここを綺麗にかけるようにしたい
    return { id, gist };
  }

  fileList() {
    const { gist } = this.getGist();

    if (gist.files)
      return gist.files.map(f => {
        return (
          <li key={f.filename}>
            <span>{f.filename}</span>
            <div>{f.content}</div>
          </li>
        );
      });
  }

  render() {
    const { gist, id } = this.getGist();

    return (
      <div className="m-gist">
        <Loader />
        <If condition={gist}>
          <div>
            <button className="p-button">
              <Link to={`/gists/${id}/edit`}>Edit Gist</Link>
            </button>
            <div className="description">{gist.description}</div>
            <ul>{this.fileList()}</ul>
          </div>
        </If>
      </div>
    );
  }
}

export default Gist;
