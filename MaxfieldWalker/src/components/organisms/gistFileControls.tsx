import * as React from "react";
import { FontSize, ColorResources } from "../../theme/resources";
import { DropdownMenu, DropdownCommand } from "../atoms/dropdownMenu";
import { GistCommandProps } from "../propsTypes";
import { EditableText } from "../atoms/editableText";
import styled from "styled-components";
import { CustomStyle } from "../../theme/styles";
import { ScopeName } from "../atoms/scopeName";
import { PrimaryButton } from "../atoms/buttons";
import { SaveIndicator, SaveIndicatorState } from "../atoms/saveIndicator";

const Wrapper = styled.div`
  display: flex;
  align-items: flex-end;
  padding: 0 10px;
`;

const FilenameWrapper = styled.div`
  height: 48px;
`;

const AttributesWrapper = styled.div`
  flex: 1;
  padding: 12px 0;
`;

const SubcontentWrapper = styled.div``;

const ButtonsWrapper = styled.div`
  position: relative;
  display: flex;
  padding-bottom: 12px;

  & > button {
    margin: 0 6px;
  }
`;

interface P extends GistCommandProps {
  filename: string;
  description: string;
  isPrivate: boolean;
  saveState: SaveIndicatorState;
  isSingleFile: boolean;
  saveRequested?: () => void;
  onAddFileClick?: () => Promise<void>;
}

interface State {
  addingFile: boolean;
}

type Props = P & CustomStyle;

export class GistFileControls extends React.Component<Props, State> {
  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      addingFile: false
    };
  }

  renderFilename() {
    const { filename, updateGistFilename } = this.props;

    const textStyle: React.CSSProperties = {
      fontSize: FontSize.large,
      color: ColorResources.foreground,
      fontWeight: "bold"
    };

    return (
      <FilenameWrapper>
        <EditableText
          content={filename}
          textStyle={textStyle}
          requestUpdateContent={async newFilename => {
            updateGistFilename &&
              (await updateGistFilename(filename, newFilename));
          }}
        />
      </FilenameWrapper>
    );
  }

  renderDescription() {
    const { description, updateGistDescription } = this.props;

    const textStyle: React.CSSProperties = {
      fontSize: FontSize.small,
      color: ColorResources.sub_foreground,
      fontStyle: "italic"
    };

    return (
      <EditableText
        content={description || "No description"}
        textStyle={textStyle}
        requestUpdateContent={updateGistDescription}
      />
    );
  }

  renderScope() {
    return (
      <ScopeName isPrivate={this.props.isPrivate} style={{ marginLeft: 12 }} />
    );
  }

  renderAttributes() {
    return (
      <React.Fragment>
        {this.renderScope()}
        {this.renderFilename()}
        {this.renderDescription()}
      </React.Fragment>
    );
  }

  renderSubcontents() {
    const {
      onDeleteThisFileClick,
      onDeleteThisGistClick,
      saveState,
      isSingleFile
    } = this.props;

    const c1: DropdownCommand = {
      displayName: "Delete this file",
      isDanger: true,
      onClick: async () =>
        onDeleteThisFileClick &&
        (await onDeleteThisFileClick(this.props.filename))
    };

    const c2: DropdownCommand = {
      displayName: "Delete this gist",
      isDanger: true,
      onClick: onDeleteThisGistClick
    };

    const dropdownCommands: DropdownCommand[] = isSingleFile ? [c2] : [c1, c2];

    return (
      <React.Fragment>
        <SaveIndicator
          state={saveState}
          onSaveNowClick={this.props.saveRequested}
        />
        <ButtonsWrapper>
          <PrimaryButton
            onClick={this.onAddFileClick.bind(this)}
            disabled={this.state.addingFile}
          >
            Add file
          </PrimaryButton>
          <DropdownMenu commands={dropdownCommands} />
        </ButtonsWrapper>
      </React.Fragment>
    );
  }

  async onAddFileClick() {
    const { onAddFileClick } = this.props;
    if (onAddFileClick) {
      this.setState({ addingFile: true });
      await onAddFileClick();
      this.setState({ addingFile: false });
    }
  }

  render() {
    const { style } = this.props;

    return (
      <Wrapper style={style}>
        <AttributesWrapper>{this.renderAttributes()}</AttributesWrapper>
        <SubcontentWrapper>{this.renderSubcontents()}</SubcontentWrapper>
      </Wrapper>
    );
  }
}
