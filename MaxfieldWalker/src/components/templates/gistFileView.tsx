import * as React from "react";
import { FileEditor } from "../atoms/fileEditor";
import { FillStyle } from "../../theme/styles";
import { GistFileControls } from "../organisms/gistFileControls";
import { GistCommandProps } from "../propsTypes";
import { ListedGist, ListedGistFile } from "../../api/objects/gist";
import { Message } from "./message";
import * as monaco from "monaco-editor";
import { interval, BehaviorSubject, Subscription } from "rxjs";
import { debounce, skip } from "rxjs/operators";
import { SaveIndicatorState } from "../atoms/saveIndicator";
import styled from "styled-components";

const EditorWrapper = styled.div`
  flex: 1;
`;

export enum GistFileState {
  Loading,
  Loaded,
  Undefined
}

export interface ActiveGistItem {
  gist: ListedGist;
  file: ListedGistFile;
}

interface Props extends GistCommandProps {
  content: string | undefined;
  activeGistItem: ActiveGistItem;
  state: GistFileState;
  updateGistFileContent: (filename: string, content: string) => Promise<void>;
  onAddFileClick: () => Promise<void>;
}

interface State {
  saveState: SaveIndicatorState;
}

export class GistFileView extends React.Component<Props, State> {
  private _editSubject: BehaviorSubject<string>;
  private _subscription: Subscription | undefined;

  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      saveState: "normal"
    };

    this.startSubscribe();
  }

  componentWillUnmount() {
    // TODO: 内容の変更があれば保存する

    this.endSubscribe();
  }

  renderLoadingGistFile() {
    return <Message>Loading...</Message>;
  }

  handleContentChanged(
    value: string,
    _: monaco.editor.IModelContentChangedEvent
  ) {
    if (value) {
      this.setState({ saveState: "pending" });
      this._editSubject.next(value);
    }
  }

  async save(content: string) {
    const { updateGistFileContent } = this.props;
    if (updateGistFileContent) {
      this.setState({ saveState: "saving" });
      const filename = this.props.activeGistItem.file.filename;
      await updateGistFileContent(filename, content);
      this.setState({ saveState: "normal" });
    }
  }

  startSubscribe() {
    this._editSubject = new BehaviorSubject("");
    this._subscription = this._editSubject
      .pipe(skip(1))
      .pipe(debounce(() => interval(5000))) // 5秒間編集がなければ発火する
      .subscribe(this.save.bind(this));
  }

  endSubscribe() {
    if (this._subscription && !this._subscription.closed) {
      this._subscription.unsubscribe();
    }
  }

  saveRequested() {
    this.endSubscribe();
    const value = this._editSubject.getValue();
    if (value) {
      this.save(value);
    }
    this.startSubscribe();
  }

  render() {
    const {
      content,
      activeGistItem,
      onMakePublicClick,
      onDeleteThisFileClick,
      onDeleteThisGistClick,
      onAddFileClick,
      updateGistDescription,
      updateGistFilename
    } = this.props;

    const { saveState } = this.state;

    const { gist, file } = activeGistItem;
    const { description, isPrivate } = gist;
    const { filename } = file;
    const isLoading = this.props.state === GistFileState.Loading;

    return (
      <div style={{ ...FillStyle, display: "flex", flexDirection: "column" }}>
        <GistFileControls
          filename={filename}
          description={description}
          isPrivate={isPrivate}
          onMakePublicClick={onMakePublicClick}
          onDeleteThisFileClick={onDeleteThisFileClick}
          onDeleteThisGistClick={onDeleteThisGistClick}
          onAddFileClick={onAddFileClick}
          updateGistDescription={updateGistDescription}
          updateGistFilename={updateGistFilename}
          saveRequested={this.saveRequested.bind(this)}
          saveState={saveState}
          isSingleFile={gist.files.length == 1}
          style={{
            height: 130,
            borderBottom: "1px solid lightgray",
            marginTop: 20
          }}
        />
        <EditorWrapper>
          {isLoading ? (
            this.renderLoadingGistFile()
          ) : (
            <FileEditor
              content={content}
              style={isLoading ? { display: "none" } : {}}
              onContentChange={this.handleContentChanged.bind(this)}
              language={file.language}
            />
          )}
        </EditorWrapper>
      </div>
    );
  }
}
