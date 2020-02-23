import { css } from "styled-components";

export const fonts = css`
  @font-face {
    font-family: "Yu Gothic";
    src: local("Yu Gothic Medium");
    font-weight: 100;
  }
  @font-face {
    font-family: "Yu Gothic";
    src: local("Yu Gothic Medium");
    font-weight: 200;
  }
  @font-face {
    font-family: "Yu Gothic";
    src: local("Yu Gothic Medium");
    font-weight: 300;
  }
  @font-face {
    font-family: "Yu Gothic";
    src: local("Yu Gothic Medium");
    font-weight: 400;
  }
  @font-face {
    font-family: "Yu Gothic";
    src: local("Yu Gothic Bold");
    font-weight: bold;
  }
  @font-face {
    font-family: "Helvetica Neue";
    src: local("Helvetica Neue Regular");
    font-weight: 100;
  }
  @font-face {
    font-family: "Helvetica Neue";
    src: local("Helvetica Neue Regular");
    font-weight: 200;
  }

  html {
    font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue",
      "Yu Gothic", YuGothic, Verdana, Meiryo, "M+ 1p", sans-serif;
  }
  @media all and (-ms-high-contrast: none) {
    html {
      font-family: Verdana, Meiryo, sans-serif;
    }
  }
  @media all and (-ms-high-contrast: active) {
    html {
      font-family: Verdana, Meiryo, sans-serif;
    }
  }
`;
