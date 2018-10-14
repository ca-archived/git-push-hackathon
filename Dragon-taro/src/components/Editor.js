import React, { Component } from "react";

class Editor extends Component {
  constructor() {
    super();

    this.state = {
      description: "",
      public: true,
      files: [{ name: "", content: "" }]
    };
  }

  handleChange(keyValue) {
    this.setState(keyValue);
  }

  render() {
    const { description } = this.state;
    return (
      <div>
        <input
          type="text"
          value={description}
          placeholder="description"
          onChange={e => this.handleChange({ description: e.target.value })}
        />
      </div>
    );
  }
}

export default Editor;
