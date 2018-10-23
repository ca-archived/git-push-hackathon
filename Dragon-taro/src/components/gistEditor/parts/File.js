import React, { Component } from "react";
import Textarea from "react-textarea-autosize";
import { If } from "../../parts/If";

class File extends Component {
  constructor() {
    super();

    this.state = {
      isEdit: {
        filename: false,
        content: false
      }
    };
  }

  handleChange(keyValue, e) {
    this.props.onChange(keyValue);
    const { isEdit } = this.state;
    this.setState({ isEdit: { ...isEdit, [e.target.name]: true } });
  }

  handleChangeValue(e) {
    const keyValue = { [e.target.name]: e.target.value };
    this.handleChange(keyValue, e);
  }

  handleKeyDown(e) {
    if (e.key === "Tab" && e.keyCode !== 229) {
      e.preventDefault();
      const { selectionStart, selectionEnd, value } = e.target;
      const newValue =
        value.substring(0, selectionStart) +
        "  " +
        value.substring(selectionEnd, value.length);
      const keyValue = { [e.target.name]: newValue };
      this.handleChange(keyValue, e);

      // うまく作動しない→カーソル位置をstate管理してもいいかも
      e.target.setSelectionRange(selectionStart + 2, selectionStart + 2);
    }
  }

  isValid() {
    const { filename, content } = this.props;
    const { isEdit } = this.state;
    const isIncludeSlash = filename.indexOf("/") != -1;
    const isBlankFilename = !filename && isEdit.filename;
    const isBlankContent = !content && isEdit.content;
    const isValid = !isIncludeSlash && !isBlankContent && !isBlankFilename;
    return {
      isIncludeSlash,
      isBlankFilename,
      isBlankContent,
      isValid
    };
  }

  errorMessage() {
    const { isIncludeSlash, isBlankFilename, isBlankContent } = this.isValid();
    if (isIncludeSlash) {
      return "Cannot include / in filename";
    } else if (isBlankFilename) {
      return "Filename can't be empty";
    } else if (isBlankContent) {
      return "Contents can't be empty";
    } else {
      return "";
    }
  }

  render() {
    const { filename, content, deleteFile, isDeletable } = this.props;
    const { isValid, isBlankFilename, isBlankContent } = this.isValid();

    return (
      <div className="file">
        <If condition={!isValid}>
          <div className="invalid-message">{this.errorMessage()}</div>
        </If>
        <input
          type="text"
          name="filename"
          placeholder="Filename"
          className={isBlankFilename ? "invalid" : ""}
          value={filename}
          onChange={e => this.handleChangeValue(e)}
        />
        <If condition={isDeletable}>
          <button className="p-button red" onClick={() => deleteFile()}>
            <span>Delete</span>
          </button>
        </If>
        <Textarea
          minRows={10}
          name="content"
          value={content}
          className={isBlankContent ? "invalid" : ""}
          onChange={e => this.handleChangeValue(e)}
          onKeyDown={e => this.handleKeyDown(e)}
          placeholder="Content"
        />
      </div>
    );
  }
}

export default File;
