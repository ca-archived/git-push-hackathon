import * as React from "react";
import styled from "styled-components";
import { ColorResources } from "../../theme/resources";
import { AppBarIconButton } from "../atoms/appBarIconButton";
import { AppBarAvatar } from "../atoms/appBarAvatar";
import { GithubClientFactory } from "../../api/clientGenerator";

export enum AppBarItem {
  github,
  gist,
  account,
  settings
}

interface WrapperProps {
  width: number;
}

const Wrapper = styled.div`
  width: ${(props: WrapperProps) => props.width}px;
  background-color: ${ColorResources.app_bar_background};
  align-items: center;
  display: flex;
  flex-direction: column;
`;

const TopWrapper = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  margin-top: 12px;
`;

const BottomWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 12px;
`;

interface Props {
  activeItem: AppBarItem;
  onItemClick?: (item: AppBarItem) => void;
}

interface State {
  avatarUrl: string | undefined;
}

export class AppBar extends React.Component<Props, State> {
  constructor(props: Props, state: State) {
    super(props, state);
    this.state = {
      avatarUrl: undefined
    };
  }

  async componentDidMount() {
    // アバター画像を読み込む
    const api = GithubClientFactory.instance();
    const user = await api.getUserData();
    this.setState({
      avatarUrl: user.avatar_url
    });
  }

  render() {
    const { activeItem, onItemClick } = this.props;
    const { avatarUrl } = this.state;

    const commonStyle: React.CSSProperties = {
      width: 28,
      height: 28,
      padding: "16px 4px"
    };

    return (
      <Wrapper width={64}>
        <TopWrapper>
          <AppBarIconButton
            source="../../images/icons/mark-github_white_28x28.svg"
            style={commonStyle}
            active={activeItem === AppBarItem.github}
            onClick={() => onItemClick && onItemClick(AppBarItem.github)}
            tooltip="GitHub"
          />
          <AppBarIconButton
            source="../../images/icons/gist_white_28x28.svg"
            style={{ ...commonStyle, height: 32 }}
            active={activeItem === AppBarItem.gist}
            onClick={() => onItemClick && onItemClick(AppBarItem.gist)}
            tooltip="Gist"
          />
          {avatarUrl ? (
            <AppBarAvatar
              avatarUrl={avatarUrl}
              style={{ ...commonStyle, borderRadius: "50%" }}
              active={activeItem === AppBarItem.account}
              onClick={() => onItemClick && onItemClick(AppBarItem.account)}
              tooltip="Account"
            />
          ) : null}
        </TopWrapper>
        <BottomWrapper>
          <AppBarIconButton
            source="../../images/icons/gear_white_28x28.svg"
            active={activeItem === AppBarItem.settings}
            onClick={() => onItemClick && onItemClick(AppBarItem.settings)}
            tooltip="Settings"
            style={{ width: 24, height: 24 }}
          />
        </BottomWrapper>
      </Wrapper>
    );
  }
}
