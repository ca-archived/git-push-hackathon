import * as React from "react";
import {
  Treebeard,
  TreebeardNode,
  decorators
} from "@maxfield/react-treebeard";
import { TreeViewChildItem, TreeViewRootItem } from "./treeViewItem";
import { TreeViewItemTriangle } from "../atoms/treeViewTriangle";
import { CustomTreebeardNode } from "../organisms/gistTreeView";
import styled from "styled-components";

const Wrapper = styled.div`
  margin: 4px 0;
  padding-left: 4px;
  padding-right: 12px;
`;

const ParentHasChildrenWrapper = styled.div`
  display: flex;
  align-items: center;
`;

const ParentNoChildrenWrapper = styled.div`
  margin-left: 22px;
`;

const ChildWrapper = styled.div``;

interface Props {
  data: TreebeardNode | TreebeardNode[];
  onToggle?(node: TreebeardNode): void;
  onTriangleClick?(node: TreebeardNode, toggled: boolean): void;
}

export const CustomTreebeard: React.StatelessComponent<Props> = ps => {
  const { data, onToggle, onTriangleClick } = ps;

  decorators.Toggle = () => undefined;

  decorators.Container = props => {
    const { node: n, onClick, onTriangleClick } = props;
    const node = n as CustomTreebeardNode;
    const hasChildren = node.children;
    const { active, type } = node;

    const triangle = (
      <TreeViewItemTriangle
        width={6}
        style={{ marginTop: -18, marginRight: 4 }}
        toggled={node.toggled}
        onClick={() => onTriangleClick(node, !node.toggled)}
      />
    );

    const isSemiactive =
      node.children &&
      !active &&
      node.children.findIndex(x => x.active == true) >= 0;
    const rootItem = (
      <TreeViewRootItem
        name={node.name}
        description={(node as any).gist.description || "No description"}
        onClick={onClick}
        activeState={
          isSemiactive ? "semi-active" : active ? "active" : "not-active"
        }
      />
    );

    const childItem = (
      <TreeViewChildItem
        name={node.name}
        onClick={onClick}
        activeState={active ? "active" : "not-active"}
      />
    );

    return (
      <Wrapper>
        {type === "parent" ? (
          hasChildren ? (
            <ParentHasChildrenWrapper>
              {triangle}
              {rootItem}
            </ParentHasChildrenWrapper>
          ) : (
            <ParentNoChildrenWrapper>{rootItem}</ParentNoChildrenWrapper>
          )
        ) : (
          <ChildWrapper>{childItem}</ChildWrapper>
        )}
      </Wrapper>
    );
  };

  return (
    <Treebeard
      data={data}
      onToggle={onToggle}
      onTriangleClick={onTriangleClick}
      decorators={decorators}
      animations={false}
      style={{
        tree: {
          base: {
            listStyle: "none",
            padding: 0,
            backgroundColor: "transparent"
          },
          node: {
            base: {
              position: "relative"
            },
            subtree: {
              listStyle: "none",
              paddingLeft: 32 // 子要素の左パディング
            }
          }
        }
      }}
    />
  );
};
