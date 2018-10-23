import * as React from "react";
import { CustomStyle } from "../../theme/styles";
import styled from "styled-components";
import { ColorResources, TransitionResources } from "../../theme/resources";

interface WrapperProps {
  width: number;
  height: number;
}

const Wrapper = styled.span`
  width: ${(props: WrapperProps) => props.width}px;
  height: ${(props: WrapperProps) => props.height}px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  border-radius: 50%;
  transition: ${TransitionResources.tree_view_background_color};
  &:hover {
    background-color: ${ColorResources.tree_view_high_background};
  }
`;

const MySvg = styled.svg`
  display: inline-block;
  transform: rotate(
    ${(props: { toggled: boolean }) => (props.toggled ? 90 : 0)}deg
  );
  fill: ${(props: { toggled: boolean }) =>
    props.toggled ? "#000" : ColorResources.tree_view_sub_foreground};
`;

interface TreeViewItemTriangleProps extends CustomStyle {
  width: number;
  toggled: boolean;
  onClick?: () => void;
}

export const TreeViewItemTriangle: React.StatelessComponent<
  TreeViewItemTriangleProps
> = props => {
  const { width, toggled, style, onClick } = props;

  const size = 18;
  const height = width * 1.5;
  const points = `0,0 0,${height} ${width},${height / 2}`;

  return (
    <Wrapper width={size} height={size} style={style} onClick={onClick}>
      <MySvg width={width} height={height} toggled={toggled}>
        <polygon points={points} />
      </MySvg>
    </Wrapper>
  );
};
