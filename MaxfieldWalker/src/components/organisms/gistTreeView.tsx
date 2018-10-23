import * as React from "react";
import { CustomStyle } from "../../theme/styles";
import { CustomTreebeard } from "../atoms/customTreebeard";
import { TreebeardNode } from "@maxfield/react-treebeard";
import { ListedGistFile, ListedGist } from "../../api/objects/gist";
import { ActiveGistItem } from "../templates/gistFileView";
import Scrollbars from "react-custom-scrollbars";
import styled from "styled-components";
import { FontSize } from "../../theme/resources";

const MessageSpan = styled.div`
  color: gray;
  user-select: none;
  margin-top: 26px;
  margin-left: 12px;
  font-size: ${FontSize.small}px;
`;

interface Props extends CustomStyle {
  gists: ListedGist[] | undefined;
  onGistFileClick(gist: ListedGist, gistFile: ListedGistFile): void;
  gistsLoaded: boolean;
  activeGistItem: ActiveGistItem | undefined;
}

export interface GistFileTreebeardNode extends CustomTreebeardNode {
  gistFile: ListedGistFile;
  gist: ListedGist;
}

export interface CustomTreebeardNode extends TreebeardNode {
  type: "parent" | "child";
}

interface State {
  activeNode: any;
  data: GistFileTreebeardNode[];
}

export default class GistTreeView extends React.Component<Props, State> {
  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      activeNode: undefined,
      data: []
    };
  }

  componentDidUpdate(prevProps: Readonly<Props>) {
    if (
      this.props.gists &&
      (!prevProps.gists || prevProps.gists != this.props.gists)
    ) {
      const { gists } = this.props;
      this.updateData(gists);
    }

    if (
      this.props.activeGistItem &&
      (!prevProps.activeGistItem ||
        prevProps.activeGistItem.file.raw_url !==
          this.props.activeGistItem.file.raw_url)
    ) {
      const gistId = this.props.activeGistItem.gist.id;
      const filename = this.props.activeGistItem.file.filename;
      this.activateGistFileFromCodeBehind(gistId, filename);
    }
  }

  private updateData(gists: ListedGist[]) {
    const newData: GistFileTreebeardNode[] = [];
    const { data: prevData } = this.state;
    gists.forEach(gist => {
      const newNode = this.convertGistToTreebeardItem(gist);
      const prevNode = prevData.find(x => x.gist.id === gist.id);
      if (prevNode) {
        // Toggle状態が保持されるようにしている
        newNode.toggled = prevNode.toggled;
      }
      newData.push(newNode);
    });

    this.setState({
      data: newData
    });
  }

  private toggleNode(node: CustomTreebeardNode, toggled?: boolean) {
    if (node.children && toggled !== undefined) {
      this.mutateData(
        n => n.id === node.id,
        n => {
          n.toggled = toggled;
          return n;
        }
      );
    }
  }

  private mutateData(
    predicate: (node: CustomTreebeardNode) => boolean,
    mutate: (node: GistFileTreebeardNode) => GistFileTreebeardNode
  ) {
    const index = this.state.data.findIndex(predicate);
    if (index >= 0) {
      const copy = Object.assign({}, this.state.data[index]);
      this.setState({
        data: [
          ...this.state.data.slice(0, index),
          mutate(copy),
          ...this.state.data.slice(index + 1)
        ]
      });
    }
  }

  private activeNodeChanged(node: CustomTreebeardNode) {
    const prevActiveNode = this.state.activeNode;
    if (prevActiveNode) {
      // 以前アクティブだったアイテムの
      // activeフラグを下ろす
      prevActiveNode.active = false;
    }
    // クリックされたノードをアクティブにする
    node.active = true;
    // this.toggleNode(node, toggled);
    // アクティブなノードを保持しておく
    this.setState({ activeNode: node });
  }

  onTreeItemClick(node: CustomTreebeardNode) {
    this.activeNodeChanged(node);

    const { onGistFileClick, activeGistItem } = this.props;
    const n = node as GistFileTreebeardNode;

    const isDifferentGistFile =
      !activeGistItem || n.gistFile.raw_url !== activeGistItem.file.raw_url;

    if (isDifferentGistFile && onGistFileClick) {
      onGistFileClick(n.gist, n.gistFile);
    }
  }

  onTriangleClick(node: CustomTreebeardNode, toggled: boolean) {
    this.toggleNode(node, toggled);
  }

  private convertGistToTreebeardItem(gist: ListedGist): GistFileTreebeardNode {
    const files = gist.files;
    const firstFile = files[0];

    return {
      name: firstFile.filename,
      toggled: false,
      id: gist.id, // nodeを識別するために必要
      children:
        files.length > 1
          ? files.slice(1).map(x => ({
              name: x.filename,
              toggled: false,
              selected: false,
              gist: gist,
              gistFile: x,
              type: "child"
            }))
          : undefined,
      type: "parent",
      gist: gist,
      gistFile: firstFile
    };
  }

  private activateGistFileFromCodeBehind(gistId: string, gistFileName: string) {
    const gistIndex = this.state.data.findIndex(x => x.gist.id === gistId);

    if (gistIndex < 0) return;
    const parentNode = this.state.data[gistIndex];

    if (parentNode.gistFile.filename === gistFileName) {
      parentNode.toggled = true;
      this.activeNodeChanged(parentNode);
    } else {
      if (parentNode.children) {
        const children = parentNode.children as GistFileTreebeardNode[];
        const gistFileIndex = children.findIndex(
          x => x.gistFile.filename === gistFileName
        );
        if (gistFileIndex >= 0) {
          const fileNode = children[gistFileIndex];
          parentNode.toggled = true;
          this.activeNodeChanged(fileNode);
        }
      }
    }
  }

  render() {
    const { gistsLoaded } = this.props;
    const { data } = this.state;
    return (
      <Scrollbars autoHide autoHideTimeout={500}>
        {gistsLoaded ? (
          data.length > 0 ? (
            <CustomTreebeard
              data={data}
              onToggle={this.onTreeItemClick.bind(this)}
              onTriangleClick={this.onTriangleClick.bind(this)}
            />
          ) : (
            <MessageSpan>No gists</MessageSpan>
          )
        ) : (
          <MessageSpan>Loading...</MessageSpan>
        )}
      </Scrollbars>
    );
  }
}
