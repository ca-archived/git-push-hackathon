import * as React from "react";
import { CustomStyle } from "../../theme/styles";
import styled from "styled-components";

interface ImgProps {
  active: boolean;
}

const Img = styled.img`
  cursor: pointer;
  padding: 4px;
  opacity: ${(props: ImgProps) => (props.active ? 1.0 : 0.5)};
  &:hover {
    opacity: 1;
  }
`;

interface Props extends CustomStyle {
  source: string;
  active: boolean;
  onClick?: () => void;
  tooltip?: string;
}

export const AppBarIconButton: React.StatelessComponent<Props> = props => {
  const { source, active, onClick, tooltip, style } = props;
  return (
    <Img
      src={source}
      active={active}
      style={style}
      onClick={onClick}
      title={tooltip}
    />
  );
};
