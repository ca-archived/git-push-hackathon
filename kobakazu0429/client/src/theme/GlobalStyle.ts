import { createGlobalStyle } from "styled-components";
import { fonts } from "./font";

export const GlobalStyle = createGlobalStyle`
  *,
  *:before,
  *:after {
    box-sizing: border-box;
  };

  html, body {
    color: ${({ theme }) => theme.color.text.primary};
    #root {
      max-width: 350px;
      margin: 0 auto;
    }
  }

  ${fonts}

  ul, li {
    margin: 0;
    padding: 0;
  };

  input, button, textarea, select {
    appearance: none;
    margin: 0;
    padding: 0;
    background: none;
    border: none;
    border-radius: 0;
  };

  button {
    background-color: transparent;
    cursor: pointer;
  };

  a {
    text-decoration: none;
  };

  hr {
    border-style: solid;
    background:#999;
    border-width: 0px;
    height: 1px;
    width: 100%
  }
`;
