import * as React from "react";
import {
  Color,
  NumberResources,
  TransitionResources,
  ColorResources,
  FontSize
} from "../../theme/resources";
import styled from "styled-components";

export type TreeViewItemActiveState = "active" | "semi-active" | "not-active";

interface Props {
  itemState: TreeViewItemActiveState;
  filename: string;
  mini?: boolean;
}

interface WrapperProps {
  mini?: boolean;
}

const Wrapper = styled.div`
  padding: 6px 8px;
  transition: ${TransitionResources.tree_view_background_color};
  border-radius: ${NumberResources.radius_mid}px;
  cursor: pointer;
  user-select: none;
  font-size: ${({ mini }: WrapperProps) =>
    mini ? FontSize.small : FontSize.mid}px;
  padding: ${({ mini }: WrapperProps) => (mini ? "4px 8px" : "6px 8px")};

  &:hover {
    background-color: ${ColorResources.tree_view_high_background};
  }
`;

export const TreeViewFilename: React.StatelessComponent<Props> = props => {
  const { itemState, filename, mini } = props;

  const style = nameStyles[itemState];

  return (
    <Wrapper style={style} mini={mini}>
      <span>{filename}</span>
    </Wrapper>
  );
};

const nameStyles: { [key: string]: React.CSSProperties } = {
  active: {
    backgroundColor: Color.orange,
    color: Color.black
  },
  "semi-active": {
    backgroundColor: "#bbb",
    color: Color.black
  },
  "not-active": {
    color: Color.black
  }
};
