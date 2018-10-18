import React, { Component } from "react";
import File from "./File";

class Editor extends Component {
  constructor() {
    super();

    this.state = {
      isSubmit: false,
      isSetGist: false
    };
  }

  componentDidMount() {
    const {
      type,
      actions: { initEditor },
      match: {
        params: { id }
      }
    } = this.props;
    initEditor({ type: type, id: id });
  }

  isEdit() {
    return this.props.type == "edit";
  }

  setGist() {
    const {
      gist,
      match: {
        params: { id }
      }
    } = this.props;
    const currentGist = gist[id];

    if (currentGist) {
      this.setState({ ...currentGist, isSetGist: true });
    }
  }

  handleChange(keyValue) {
    const {
      actions: { handleEditorChange }
    } = this.props;
    handleEditorChange(keyValue);
  }

  handleFileChange(keyValue, index) {
    const {
      editor: { files }
    } = this.props;
    const value = { ...files[index], ...keyValue };
    let newFiles = files.concat();
    newFiles[index] = value;

    this.handleChange({ files: newFiles });
  }

  addFile() {
    const {
      editor: { files }
    } = this.props;
    const newFile = { index: files.length, filename: "", content: "" };
    const newFiles = files.concat(newFile);
    this.handleChange({ files: newFiles });
  }

  handleSubmit() {
    if (!this.state.isSubmit) {
      const { type } = this.props;
      const {
        actions: { submitGist }
      } = this.props;
      submitGist({ data: this.state, type: type });
      this.setState({ isSubmit: true });
    }
  }

  fileEditors() {
    const {
      editor: { files }
    } = this.props;
    const fileEditorList = files.map(f => {
      return (
        <li key={f.index}>
          <File
            {...f}
            onChange={keyValue => this.handleFileChange(keyValue, f.index)}
          />
        </li>
      );
    });
    return fileEditorList;
  }

  render() {
    const { description } = this.props;
    const buttonMessage = this.isEdit() ? "Edit" : "Create";
    return (
      <div>
        <input
          type="text"
          value={description}
          placeholder="description"
          onChange={e => this.handleChange({ description: e.target.value })}
        />
        <ul>{this.fileEditors()}</ul>
        <button onClick={() => this.addFile()}>Add File</button>
        <button onClick={() => this.handleSubmit()}>{buttonMessage}</button>
      </div>
    );
  }
}

export default Editor;
