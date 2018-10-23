import * as React from "react";
import * as moment from "moment";
import { CustomStyle } from "../theme/styles";
import { GithubClientFactory } from "../api/clientGenerator";
import {
  GistFileView,
  GistFileState,
  ActiveGistItem
} from "../components/templates/gistFileView";
import {
  ListedGistFile,
  ListedGist,
  NewGistFiles,
  NewGist
} from "../api/objects/gist";
import GistTreeView from "../components/organisms/gistTreeView";
import { GitHubClient } from "../api/githubClient";
import { generateRandomString } from "../util/randomString";
import { GistSidebarControls } from "../components/organisms/gistSidebarControls";
import { GistCache } from "../models/gist/gistCache";
import { Message } from "../components/templates/message";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
`;

const MainWrapper = styled.div`
  flex: 1;
`;

const SidebarWrapper = styled.div`
  width: 360px;
  background-color: lightgray;
  display: flex;
  flex-direction: row;
`;

interface Props extends CustomStyle {}

interface State {
  gists: ListedGist[];
  activeGistFileContent: string | undefined;
  activeGistItem: ActiveGistItem | undefined;
  gistFileState: GistFileState;
  gistsLoaded: boolean;
}

export default class GistApp extends React.Component<Props, State> {
  private _apiClient: GitHubClient;
  private _gistCache: GistCache;

  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      gists: [],
      activeGistFileContent: undefined,
      activeGistItem: undefined,
      gistFileState: GistFileState.Undefined,
      gistsLoaded: false
    };
    this._apiClient = GithubClientFactory.instance();
    this._gistCache = new GistCache();
  }

  async componentDidMount() {
    if (!this._gistCache.isListedGistsLoaded()) {
      await this._gistCache.loadListedGists();
    }
    const gists = this._gistCache.getListedGists();

    this.setState({
      gists: Object.values(gists),
      gistsLoaded: true
    });
  }

  renderGistFileNotSelected() {
    return <Message>No gist selected</Message>;
  }

  private async onActiveGistFileChanged(
    gist: ListedGist,
    file: ListedGistFile
  ) {
    this.setState({
      activeGistItem: {
        gist,
        file: file
      },
      gistFileState: GistFileState.Loading
    });

    // TODO: Gistファイル内容読み込みのキャンセルに対応
    await this._gistCache.loadGistDetail(gist.id);
    const content = this._gistCache.getGistFileContent(gist.id, file.filename);
    if (content) {
      this.setState({
        activeGistFileContent: content,
        gistFileState: GistFileState.Loaded
      });
    }
  }

  private async onNewGistButtonClick() {
    const { filename, content } = this.generateNewFile();
    const files: any = {};
    files[filename] = {
      content
    };

    const description = "";

    const newGist = await this._apiClient.createGist(description, false, files);
    this._gistCache.addGist(newGist);

    this.addGistToTreeView(newGist);
    this.onActiveGistFileChanged(newGist, newGist.files[0]);
  }

  private addGistToTreeView(gist: ListedGist) {
    this.setState({
      gists: [gist, ...this.state.gists]
    });
  }

  private deleteGist(predicate: (gist: ListedGist) => boolean) {
    const { gists } = this.state;

    const index = gists.findIndex(predicate);
    if (index >= 0) {
      this.setState({
        gists: [...gists.slice(0, index), ...gists.slice(index + 1)]
      });
    }
  }

  async onDeleteThisGistClick() {
    const { activeGistItem } = this.state;
    if (activeGistItem) {
      const { id } = activeGistItem.gist;
      const success = await this._apiClient.deleteGist(id);
      if (success) {
        console.log("Successfully deleted: " + id);

        this.deleteGist(gist => gist.id === id);
        this.setState({
          activeGistItem: undefined,
          activeGistFileContent: undefined
        });
      }
    }
  }

  private async onDeleteThisFileClick(filenameToDelete: string) {
    const { activeGistItem } = this.state;

    if (activeGistItem) {
      const gistId = activeGistItem.gist.id;
      const description = activeGistItem.gist.description;
      const files: NewGistFiles = this._gistCache.getGistFiles(gistId);

      delete files[filenameToDelete];
      files[filenameToDelete] = null;

      const result = await this._apiClient.editGist(gistId, description, files);

      if (result) {
        const gist = this.mutateGists(
          gist => gist.id === gistId,
          gist => {
            const index = gist.files.findIndex(
              x => x.filename === filenameToDelete
            );
            if (index >= 0) {
              gist.files.splice(index, 1);
            }
            return gist;
          }
        );
        if (gist && this.state.activeGistItem) {
          this.setState({
            activeGistItem: undefined,
            activeGistFileContent: undefined
          });
        }

        console.log("delete: " + filenameToDelete);
      }
    }
  }

  private generateNewFile() {
    const date = moment().format("llll");

    const filename = `${generateRandomString(10)}.txt`;
    const content = `Created: ${date}`;

    return {
      filename,
      content
    };
  }

  async onAddFileClick() {
    // ファイルを追加
    const { activeGistItem } = this.state;
    if (activeGistItem) {
      const gistId = activeGistItem.gist.id;
      const description = activeGistItem.gist.description;
      const files: NewGistFiles = this._gistCache.getGistFiles(gistId);
      const { filename, content } = this.generateNewFile();
      files[filename] = {
        filename,
        content
      };

      const result = await this._apiClient.editGist(gistId, description, files);

      if (result) {
        const gist = this.mutateGists(
          gist => gist.id === gistId,
          gist => {
            const f = result.files.find(x => x.filename === filename);
            if (f) {
              gist.files.push(f);
            }
            return gist;
          }
        );
        if (gist && this.state.activeGistItem) {
          const f = gist.files.find(x => x.filename === filename);
          if (f) {
            this.setState({
              activeGistItem: {
                gist,
                file: f
              },
              activeGistFileContent: content
            });
          }
        }
      }
    }
  }

  private mutateGists(
    predicate: (gist: ListedGist) => boolean,
    mutate: (gist: ListedGist) => ListedGist
  ): ListedGist | undefined {
    const { gists } = this.state;
    const index = gists.findIndex(predicate);
    if (index >= 0) {
      const copy = Object.assign({}, gists[index]);
      this.setState({
        gists: [
          ...gists.slice(0, index),
          mutate(copy),
          ...gists.slice(index + 1)
        ]
      });
      return copy;
    }
  }

  async updateGistDescription(description: string) {
    const activeGistItem = this.state.activeGistItem;

    if (activeGistItem) {
      const gistId = activeGistItem.gist.id;
      const files = this._gistCache.getGistFiles(gistId);

      const result = await this._apiClient.editGist(gistId, description, files);
      if (result) {
        const gist = this.mutateGists(
          gist => gist.id === gistId,
          gist => {
            gist.description = description;
            return gist;
          }
        );

        if (gist && this.state.activeGistItem) {
          this.setState({
            activeGistItem: {
              gist,
              file: this.state.activeGistItem.file
            }
          });
        }
      }
    }
  }

  private resoreGistFilesContent(
    gistId: string,
    files: ListedGistFile[]
  ): NewGistFiles {
    const fls: NewGistFiles = {};
    for (const file of files) {
      const content = this._gistCache.getGistFileContent(gistId, file.filename);

      if (content) {
        fls[file.filename] = { ...file, content };
      }
    }

    return fls;
  }

  async updateGistFileName(oldFileName: string, newFileName: string) {
    const { activeGistItem } = this.state;
    if (activeGistItem) {
      const { gist } = activeGistItem;

      const files: NewGistFiles = this.resoreGistFilesContent(
        gist.id,
        gist.files
      );

      const oldFile = files[oldFileName];

      if (oldFile) {
        delete files[oldFileName];
        // nullを設定することで、そのファイルを削除するという意味になる
        files[oldFileName] = null;
        oldFile.filename = newFileName;

        files[newFileName] = oldFile;
      }

      const success = await this._apiClient.editGist(
        gist.id,
        gist.description,
        files
      );

      if (success) {
        const updatedGist = this.mutateGists(
          g => g.id === gist.id,
          g => {
            // 該当するファイルのファイル名を変えればoK
            const file = g.files.find(x => x.filename === oldFileName);
            if (file) {
              file.filename = newFileName;
            }
            return g;
          }
        );
        if (updatedGist && this.state.activeGistItem) {
          const { gist, file } = this.state.activeGistItem;
          file.filename = newFileName;
          this.setState({
            activeGistItem: {
              gist,
              file
            }
          });
        }
      }
    }
  }

  async updateGistFileContent(filename: string, content: string) {
    const { activeGistItem } = this.state;
    if (activeGistItem) {
      const { gist } = activeGistItem;
      const files = this.resoreGistFilesContent(gist.id, gist.files);
      const copy = Object.assign({}, files[filename]);
      copy.content = content;
      files[filename] = copy;

      // まずAPIに修正版を投げる
      await this._apiClient.editGist(gist.id, gist.description, files);

      // キャッシュも更新する
      this._gistCache.updateGistFileContent(gist.id, filename, content);

      console.log("Saved: " + filename, content);
    }
  }

  render() {
    const { style } = this.props;
    const { gists, activeGistItem, gistsLoaded } = this.state;

    return (
      <Wrapper style={style}>
        <SidebarWrapper id="gist-sidebar">
          <GistSidebarControls
            onNewGistButtonClick={this.onNewGistButtonClick.bind(this)}
            style={{ width: 160 }}
          />
          <GistTreeView
            gists={gists}
            onGistFileClick={this.onActiveGistFileChanged.bind(this)}
            activeGistItem={activeGistItem}
            gistsLoaded={gistsLoaded}
            style={{ flex: 1, height: "100%" }}
          />
        </SidebarWrapper>
        <MainWrapper id="gist-main">
          {activeGistItem ? (
            <GistFileView
              activeGistItem={activeGistItem}
              content={this.state.activeGistFileContent}
              state={this.state.gistFileState}
              onDeleteThisGistClick={this.onDeleteThisGistClick.bind(this)}
              onDeleteThisFileClick={this.onDeleteThisFileClick.bind(this)}
              onAddFileClick={this.onAddFileClick.bind(this)}
              updateGistDescription={this.updateGistDescription.bind(this)}
              updateGistFilename={this.updateGistFileName.bind(this)}
              updateGistFileContent={this.updateGistFileContent.bind(this)}
            />
          ) : (
            this.renderGistFileNotSelected()
          )}
        </MainWrapper>
      </Wrapper>
    );
  }
}
