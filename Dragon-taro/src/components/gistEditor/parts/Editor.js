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

  isValid() {
    const {
      editor: { files }
    } = this.props;
    return files.every(
      e => e.filename && e.content && e.filename.indexOf("/") == -1
    );
  }

  handleChange(keyValue) {
    const {
      actions: { handleEditorChange },
      type
    } = this.props;
    handleEditorChange({ keyValue, type });
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

  deleteFile(index) {
    const {
      editor: { files }
    } = this.props;
    const newFiles = files
      .filter(f => f.index != index)
      .map((f, i) => ({ ...f, index: i }));
    this.handleChange({ files: newFiles });
  }

  handleSubmit() {
    const {
      actions: { submitGist }
    } = this.props;
    const method = this.isEdit() ? "PATCH" : "POST";
    if (this.isValid()) submitGist({ data: this.state, method: method });
  }

  fileEditors() {
    const {
      editor: { files }
    } = this.props;
    const isDeletable = files.length > 1;
    const fileEditorList = files.map(f => {
      return (
        <li key={f.index}>
          <File
            {...f}
            isDeletable={isDeletable}
            onChange={keyValue => this.handleFileChange(keyValue, f.index)}
            deleteFile={() => this.deleteFile(f.index)}
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
    const loadMessage = this.isEdit() ? "Updating..." : "Creating...";

    return (
      <div>
        <Loader message={loadMessage} />
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
                <span>Add File</span>
              </button>
              <button
                className={this.isValid() ? "p-button" : "p-button invalid"}
                onClick={() => this.handleSubmit()}
              >
                <span>{buttonMessage}</span>
              </button>
            </div>
          </div>
        </If>
      </div>
    );
  }
}

export default Editor;
