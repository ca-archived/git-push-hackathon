import * as React from "react";
import styled from "styled-components";
import {
  TransitionResources,
  ColorResources,
  NumberResources
} from "../../theme/resources";

type EditableTextState = "normal" | "edit" | "edit-saving";

const commonPadding = "4px 12px";

const EditBox = styled.input`
  border: none;
  margin: 0;
  padding: ${commonPadding};
  border-radius: ${NumberResources.radius_mid}px;

  &:disabled {
    background-color: light-gray;
  }

  &::selection {
    background: #add6ff;
  }
`;

const NormalStateWrapper = styled.div`
  transition: ${TransitionResources.editable_text};
  border-radius: ${NumberResources.radius_mid}px;
  cursor: default;
  padding: ${commonPadding};

  &:hover {
    background-color: ${ColorResources.editable_text_hover_background};
  }
`;

interface Props {
  content: string;
  textStyle: React.CSSProperties;
  requestUpdateContent?: (newContent: string) => Promise<void>;
}

interface State {
  editState: EditableTextState;
  temporaryText: string;
}

export class EditableText extends React.Component<Props, State> {
  private inputElement: HTMLInputElement;

  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      editState: "normal",
      temporaryText: ""
    };
  }

  async finishEdit() {
    const { requestUpdateContent } = this.props;
    if (requestUpdateContent) {
      this.gotoState("edit-saving");
      await requestUpdateContent(this.state.temporaryText);
      this.gotoState("normal");
    }
  }

  gotoState(editState: EditableTextState, callback?: () => void) {
    this.setState({ editState }, () => callback && callback());
  }

  gotoEditState() {
    this.setState({
      temporaryText: this.props.content
    });
    this.gotoState("edit", () => {
      // テキストを全選択する
      // setStateのcallbackで呼ばないと
      // <input />がまだ存在していないのでエラーになる
      this.inputElement.select();
    });
  }

  renderNormalState() {
    const { content, textStyle } = this.props;
    return (
      <NormalStateWrapper onClick={this.gotoEditState.bind(this)}>
        <span style={textStyle}>{content}</span>
      </NormalStateWrapper>
    );
  }

  renderEditState() {
    const { textStyle } = this.props;
    return (
      <div
        style={{
          paddingRight: 24
        }}
      >
        <EditBox
          type="text"
          value={this.state.temporaryText}
          onChange={this.onTemporaryTextChanged.bind(this)}
          onKeyDown={this.handleOnKeyDown.bind(this)}
          onBlur={this.onLostFocus.bind(this)}
          innerRef={(input: HTMLInputElement) => (this.inputElement = input)}
          disabled={this.state.editState === "edit-saving"}
          autoComplete="off"
          style={{ ...textStyle, width: "100%" }}
        />
      </div>
    );
  }

  cancelEdit() {
    this.gotoState("normal");
  }

  handleOnKeyDown(event: any) {
    const code = event.charCode || event.keyCode;
    if (code == KeyCode.enter) {
      if (this.isTextChanged()) {
        this.finishEdit();
      }
    }
  }

  isTextChanged() {
    return (
      this.state.temporaryText &&
      this.state.temporaryText !== this.props.content
    );
  }

  onLostFocus() {
    if (this.isTextChanged()) {
      // 変更があれば保存
      this.finishEdit();
    } else {
      this.cancelEdit();
    }
  }

  onTemporaryTextChanged(event: any) {
    this.setState({
      temporaryText: event.target.value
    });
  }

  render() {
    const { editState } = this.state;

    return editState === "normal"
      ? this.renderNormalState()
      : this.renderEditState();
  }
}

enum KeyCode {
  enter = 13
}
