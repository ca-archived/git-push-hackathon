import * as React from "react";
import { NumberResources, Color, FontSize } from "../../theme/resources";
import { CustomStyle } from "../../theme/styles";
import styled from "styled-components";

const Button = styled.button`
  background-color: ${Color.black};
  color: ${Color.white};
  border: none;
  padding: 8px 16px;
  border-radius: ${NumberResources.radius_mid}px;
  font-size: ${FontSize.button}px;
  font-weight: bold;
  letter-spacing: 0.1em;
  cursor: pointer;

  &:hover {
    background-color: ${Color.darkgray};
  }

  &:disabled {
    cursor: default;
    background-color: ${Color.gray};
  }
`;

interface ButtonProps extends CustomStyle {
  onClick?: () => void;
  disabled?: boolean;
}

export const PrimaryButton: React.StatelessComponent<ButtonProps> = props => {
  const { onClick, disabled, children, style } = props;

  return (
    <Button style={style} onClick={onClick} disabled={disabled}>
      {children}
    </Button>
  );
};
