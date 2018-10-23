import { CSSProperties } from "react";

export const FillStyle: CSSProperties = {
  width: "100%",
  height: "100%",
  flex: 1
};

export interface CustomStyle {
  style?: CSSProperties;
}
