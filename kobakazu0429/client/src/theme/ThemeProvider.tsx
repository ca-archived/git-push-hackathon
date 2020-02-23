import React, { FC } from "react";
import { ThemeProvider as StyledThemeProvider } from "styled-components";
import { themes, ThemeName } from "./theme";

interface Props {
  themeName: ThemeName;
}

export const ThemeProvider: FC<Props> = ({ themeName, children }) => {
  return (
    <StyledThemeProvider theme={themes[themeName]}>
      {children}
    </StyledThemeProvider>
  );
};
