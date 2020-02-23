import { ComponentPropsWithRef } from "react";
import styled, { css } from "styled-components";
import { Link } from "react-router-dom";

export type ButtonSize = "large" | "medium" | "small" | "xSmall";

export type ButtonSizes = {
  [size in ButtonSize]: {
    size: number;
    fontSize: number;
  };
};

export const buttonSizes: ButtonSizes = {
  large: {
    size: 44,
    fontSize: 16
  },
  medium: {
    size: 36,
    fontSize: 14
  },
  small: {
    size: 32,
    fontSize: 14
  },
  xSmall: {
    size: 28,
    fontSize: 12
  }
};

export const buttonSizeStyle = (buttonSize: ButtonSize = "medium") => {
  const { size, fontSize } = buttonSizes[buttonSize];
  return css`
    height: ${size}px;
    line-height: ${size}px;
    font-size: ${fontSize}px;
  `;
};

export type ButtonProps = {
  size?: ButtonSize;
  disabled?: boolean;
  inverse?: boolean;
  minWidth?: number | string;
  maxWidth?: number | string;
} & Partial<ComponentPropsWithRef<"button">> &
  Partial<ComponentPropsWithRef<"a">> &
  Partial<ComponentPropsWithRef<typeof Link>>;

const buttonSizeNormalizer = (size: number | string) => {
  if (typeof size === "string") return size;
  return `${size}px`;
};

export const BaseButton = styled.button<ButtonProps>`
  display: block;
  text-align: center;
  cursor: pointer;
  outline: none;
  border: none;
  font-weight: 600;
  margin: 0;
  width: 100%;
  background-color: transparent;
  border-radius: ${({ theme }) => theme.utils.unitPx(3)};
  padding-left: ${({ theme }) => theme.utils.unitPx(2)};
  padding-right: ${({ theme }) => theme.utils.unitPx(2)};
  transition: 200ms all;
  ${({ minWidth }) =>
    minWidth && `min-width: ${buttonSizeNormalizer(minWidth)};`}
  ${({ maxWidth }) =>
    maxWidth && `max-width: ${buttonSizeNormalizer(maxWidth)};`}
  ${({ size }) => buttonSizeStyle(size)};
  &:disabled {
    cursor: default;
    pointer-events: none;
    color: rgba(0, 0, 0, 0.24);
    background-color: rgba(0, 0, 0, 0.06);
    box-shadow: none;
  }
`;
