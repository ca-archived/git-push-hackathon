// 参考: https://blog.campvanilla.com/reactjs-dropdown-menus-b6e06ae3a8fe

import * as React from "react";
import styled from "styled-components";
import {
  NumberResources,
  Color,
  FontSize,
  ColorResources
} from "../../theme/resources";
import { CSSTransition } from "react-transition-group";
import { Dots } from "./dots";

interface MiscButtonProps {
  active: boolean;
}

const MiscButton = styled.button`
  width: 36px;
  height: 36px;
  background-color: ${(props: MiscButtonProps) =>
    props.active ? Color.silver : Color.white};
  border-radius: ${NumberResources.radius_mid}px;
  border: 1px solid ${Color.black};
  cursor: pointer;

  &:hover {
    background-color: ${(props: MiscButtonProps) =>
      props.active ? Color.silver : Color.lightgray};
  }
`;

const ButtonsWrapper = styled.div`
  position: absolute;
  flex-direction: column;
  border-radius: ${NumberResources.radius_mid}px;
  z-index: 99;
  right: 0;
  margin-top: 8px;
  margin-right: -5px;
  box-shadow: -4px 4px 24px rgba(0, 0, 0, 0.1);
  border: 1px solid ${Color.lightgray};
`;

interface ButtonProps {
  danger?: boolean;
}

const Button = styled.button`
  &:first-child {
    border-top-width: 0;
    border-top-left-radius: ${NumberResources.radius_mid}px;
    border-top-right-radius: ${NumberResources.radius_mid}px;
  }

  &:last-child {
    border-bottom-left-radius: ${NumberResources.radius_mid}px;
    border-bottom-right-radius: ${NumberResources.radius_mid}px;
  }

  &:hover {
    background-color: ${(props: ButtonProps) =>
      props.danger ? ColorResources.danger : Color.white};
    color: ${(props: ButtonProps) =>
      props.danger ? Color.white : ColorResources.danger};
  }

  color: ${(props: ButtonProps) =>
    props.danger ? ColorResources.danger : Color.black};
  display: block;
  border: none;
  border-top: 1px solid ${Color.lightgray};
  padding: 12px 12px;
  background-color: ${Color.white};
  cursor: pointer;
  padding: 12px 16px;
  font-size: ${FontSize.button}px;
`;

export interface DropdownCommand {
  displayName: string;
  isDanger?: boolean;
  onClick?: () => Promise<void>;
}

interface Props {
  commands: DropdownCommand[];
}

interface State {
  showMenu: boolean;
}

export class DropdownMenu extends React.Component<Props, State> {
  private dropdownMenu: HTMLDivElement;

  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      showMenu: false
    };
  }

  showMenu(event: any) {
    event.preventDefault();

    const { showMenu } = this.state;
    if (!showMenu) {
      this.setState({ showMenu: true });
      document.addEventListener("click", this.closeOnOtherAreaClick);
    }
  }

  private closeOnOtherAreaClick = (event: any) => {
    event.preventDefault();

    this.setState({ showMenu: false });
    document.removeEventListener("click", this.closeOnOtherAreaClick);
  };

  private renderButtons() {
    const { commands } = this.props;
    return (
      <ButtonsWrapper
        innerRef={(element: HTMLDivElement) => {
          this.dropdownMenu = element;
        }}
      >
        {commands.map((command: DropdownCommand, index: number) => (
          <Button
            key={index}
            onClick={command.onClick}
            danger={command.isDanger}
          >
            {command.displayName}
          </Button>
        ))}
      </ButtonsWrapper>
    );
  }

  render() {
    return (
      <div>
        <MiscButton
          active={this.state.showMenu}
          onClick={this.showMenu.bind(this)}
        >
          <Dots fill={Color.black} style={{ marginBottom: 3 }} />
        </MiscButton>

        {this.state.showMenu ? (
          <CSSTransition classNames="dropdown-menu" in appear timeout={0}>
            {this.renderButtons()}
          </CSSTransition>
        ) : null}
      </div>
    );
  }
}
