import React, { Component } from "react";
import File from "./File";
import { If } from "../../parts/If";
import Loader from "../../parts/Loader";

class Editor extends Component {
  constructor(props) {
    super(props);

    props.actions.loading();
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
    const {
      actions: { submitGist }
    } = this.props;
    const method = this.isEdit() ? "PATCH" : "POST";
    submitGist({ data: this.state, method: method });
    this.setState({ isSubmit: true });
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
    const {
      editor: { description },
      load: { isLoading }
    } = this.props;
    const buttonMessage = this.isEdit() ? "Edit" : "Create";

    return (
      <div>
        <Loader />
        <If condition={!isLoading}>
          <div className="m-editor">
            <div className="description">
              <input
                type="text"
                value={description}
                placeholder="description"
                name="description"
                onChange={e =>
                  this.handleChange({ description: e.target.value })
                }
              />
            </div>
            <ul>{this.fileEditors()}</ul>
            <div className="button-zone">
              <button className="p-button" onClick={() => this.addFile()}>
                Add File
              </button>
              <button className="p-button" onClick={() => this.handleSubmit()}>
                {buttonMessage}
              </button>
            </div>
          </div>
        </If>
      </div>
    );
  }
}

export default Editor;
