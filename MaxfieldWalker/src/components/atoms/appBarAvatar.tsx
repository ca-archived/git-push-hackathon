import * as React from "react";
import { CustomStyle } from "../../theme/styles";
import { AppBarIconButton } from "./appBarIconButton";

interface Props extends CustomStyle {
  avatarUrl: string;
  active: boolean;
  onClick?: () => void;
  tooltip?: string;
}

export const AppBarAvatar: React.StatelessComponent<Props> = props => {
  const { avatarUrl, active, onClick, tooltip, style } = props;
  return (
    <AppBarIconButton
      source={avatarUrl}
      active={active}
      onClick={onClick}
      tooltip={tooltip}
      style={style}
    />
  );
};
