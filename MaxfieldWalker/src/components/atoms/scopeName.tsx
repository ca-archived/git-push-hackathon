import * as React from "react";
import { FontSize, ColorResources } from "../../theme/resources";
import { CustomStyle } from "../../theme/styles";
import styled from "styled-components";

interface SpanProps {
  isPrivate: boolean;
}

const Span = styled.span`
  font-size: ${FontSize.tiny}px;
  cursor: default;
  font-weight: bold;
  letter-spacing: 0.15em;
  color: ${({ isPrivate }: SpanProps) =>
    isPrivate ? ColorResources.private_label : ColorResources.public_label};
`;

interface Props extends CustomStyle {
  isPrivate: boolean;
}

export const ScopeName: React.StatelessComponent<Props> = props => {
  const { isPrivate, style } = props;
  const text = isPrivate ? "SECRET" : "PUBLIC";

  return (
    <Span style={style} isPrivate={isPrivate}>
      {text}
    </Span>
  );
};
