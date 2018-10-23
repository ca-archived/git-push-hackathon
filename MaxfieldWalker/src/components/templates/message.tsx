import * as React from "react";
import styled from "styled-components";
import { Color } from "../../theme/resources";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${Color.white};
`;

const MessageSpan = styled.span`
  color: gray;
  user-select: none;
  text-align: center;
  line-height: 180%;
`;

export const Message: React.StatelessComponent = props => {
  return (
    <Wrapper>
      <MessageSpan>{props.children}</MessageSpan>
    </Wrapper>
  );
};
