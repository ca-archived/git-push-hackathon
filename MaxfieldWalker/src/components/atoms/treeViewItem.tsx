import * as React from "react";
import { TreeViewItemActiveState, TreeViewFilename } from "./treeViewFilename";
import styled from "styled-components";
import { FontSize, ColorResources } from "../../theme/resources";

const Wrapper = styled.div`
  cursor: default;
  flex: 1;
`;

export interface TreeViewItemProps {
  onClick?: () => void;
  name: string;
  activeState: TreeViewItemActiveState;
}

interface TreeViewChildItemProps extends TreeViewItemProps {}

/**
 * 子要素用
 */
export const TreeViewChildItem: React.StatelessComponent<
  TreeViewChildItemProps
> = props => {
  const { name, onClick, activeState } = props;

  return (
    <Wrapper onClick={onClick}>
      <TreeViewFilename filename={name} itemState={activeState} mini={true} />
    </Wrapper>
  );
};

interface TreeViewRootItemProps extends TreeViewItemProps {
  description: string;
}

const DescriptionWrapper = styled.div`
  padding: 0 8px;
`;

const Description = styled.span`
  color: ${ColorResources.tree_view_sub_foreground};
  font-size: ${FontSize.tiny}px;
  font-style: italic;
`;

/**
 * 親要素用
 */
export const TreeViewRootItem: React.StatelessComponent<
  TreeViewRootItemProps
> = props => {
  const { name, description, onClick, activeState } = props;
  return (
    <Wrapper onClick={onClick}>
      <TreeViewFilename filename={name} itemState={activeState} />
      <DescriptionWrapper>
        <Description>{description}</Description>
      </DescriptionWrapper>
    </Wrapper>
  );
};
