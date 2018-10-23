import * as React from "react";
import { PrimaryButton } from "../atoms/buttons";
import { CustomStyle } from "../../theme/styles";
import styled from "styled-components";

const Wrapper = styled.div`
  padding: 16px 12px;
`;

interface Props extends CustomStyle {
  onNewGistButtonClick?: () => Promise<void> | void;
}

interface State {
  disabled: boolean;
}

export class GistSidebarControls extends React.Component<Props, State> {
  constructor(props: Props, state: State) {
    super(props, state);
    this.state = {
      disabled: false
    };
  }

  async onClick() {
    const { onNewGistButtonClick } = this.props;
    if (onNewGistButtonClick) {
      this.setState({ disabled: true });
      await onNewGistButtonClick();
      this.setState({ disabled: false });
    }
  }

  render() {
    const { style } = this.props;

    return (
      <Wrapper style={style}>
        <PrimaryButton
          onClick={this.onClick.bind(this)}
          style={{ width: "100%" }}
          disabled={this.state.disabled}
        >
          New gist
        </PrimaryButton>
      </Wrapper>
    );
  }
}
