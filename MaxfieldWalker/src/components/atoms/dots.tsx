import * as React from "react";
import { CustomStyle } from "../../theme/styles";

interface Props extends CustomStyle {
  fill: string;
}

export const Dots: React.StatelessComponent<Props> = props => {
  const { fill, style } = props;
  return (
    <svg
      width="13"
      height="3"
      viewBox="0 0 13 3"
      fill={fill}
      xmlns="http://www.w3.org/2000/svg"
      style={style}
    >
      <circle cx="1.5" cy="1.5" r="1.5" />
      <circle cx="6.5" cy="1.5" r="1.5" />
      <circle cx="11.5" cy="1.5" r="1.5" />
    </svg>
  );
};
