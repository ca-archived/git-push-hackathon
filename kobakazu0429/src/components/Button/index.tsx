import styled, { css } from "styled-components";
import { BaseButton } from "./Base";

export const Button = styled(BaseButton)`
  color: rgba(0, 0, 0, 0.56);
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.02) 0px 0px 0px 1px,
    rgba(0, 0, 0, 0.1) 0px 0px 0px 1px;
`;

export const BrandButton = styled(BaseButton)`
  ${({ inverse }) =>
    inverse
      ? css`
          color: ${({ theme }) => theme.color.brand};
          border: 1px solid ${({ theme }) => theme.color.brand};
        `
      : css`
          color: #fff;
          background-color: ${({ theme }) => theme.color.brand};
        `}
`;
