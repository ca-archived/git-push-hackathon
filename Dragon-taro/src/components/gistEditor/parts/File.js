import React, { Component } from "react";
import Textarea from "react-textarea-autosize";
import { If } from "../../parts/If";

class File extends Component {
  constructor() {
    super();
  }

  handleChange(e) {
    const keyValue = { [e.target.name]: e.target.value };
    this.props.onChange(keyValue);
  }

  render() {
    const { filename, content, deleteFile, isDeletable } = this.props;
    const isValid = filename.indexOf("/") == -1;

    return (
      <div className="file">
        <If condition={!isValid}>
          <div className="invalid-message">Cannot include "/" in filename</div>
        </If>
        <input
          type="text"
          name="filename"
          placeholder="Filename"
          className={isValid ? "" : "invalid"}
          value={filename}
          onChange={e => this.handleChange(e)}
        />
        <If condition={isDeletable}>
          <button className="p-button red" onClick={() => deleteFile()}>
            Delete
          </button>
        </If>
        <Textarea
          minRows={10}
          name="content"
          value={content}
          onChange={e => this.handleChange(e)}
          placeholder="Content"
        />
      </div>
    );
  }
}

export default File;
