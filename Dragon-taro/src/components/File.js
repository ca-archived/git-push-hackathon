import React, { Component } from "react";

class File extends Component {
  constructor() {
    super();
  }

  handleChange(e) {
    const keyValue = { [e.target.name]: e.target.value };
    this.props.onChange(keyValue);
  }

  render() {
    const { file, content } = this.props;
    return (
      <div>
        <input
          type="text"
          name="file"
          value={file}
          onChange={e => this.handleChange(e)}
        />
        <textarea
          name="content"
          value={content}
          onChange={e => this.handleChange(e)}
        />
      </div>
    );
  }
}

export default File;
