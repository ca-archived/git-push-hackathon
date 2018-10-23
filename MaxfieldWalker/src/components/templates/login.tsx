import * as React from "react";
import { PrimaryButton } from "../atoms/buttons";
import styled from "styled-components";
import { Color } from "../../theme/resources";

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Text = styled.h3`
  font-size: 24px;
  color: ${Color.gray};
  font-weight: normal;
`;

interface Props {
  onLoginButtonClick?: () => void;
}

export const Login: React.StatelessComponent<Props> = props => {
  const { onLoginButtonClick } = props;

  return (
    <Wrapper>
      <ContentWrapper>
        <Text style={{ margin: "16px 0" }}>GitHub Gist Client</Text>
        <PrimaryButton onClick={onLoginButtonClick}>Login</PrimaryButton>
      </ContentWrapper>
    </Wrapper>
  );
};
