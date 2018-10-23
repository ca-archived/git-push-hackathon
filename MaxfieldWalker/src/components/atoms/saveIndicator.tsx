import * as React from "react";
import styled from "styled-components";
import { FontSize, Color } from "../../theme/resources";

export type SaveIndicatorState = "normal" | "saving" | "pending";

const Wrapper = styled.div`
  padding: 8px 2px;
  text-align: right;
  user-select: none;
`;

const Span = styled.span`
  font-size: ${FontSize.tiny}px;
  color: ${Color.gray};
`;

const LinkSpan = styled(Span)`
  font-weight: bold;
  color: ${Color.black};
  margin-left: 3px;
  cursor: pointer;
`;

interface Props {
  onSaveNowClick?: () => void;
  state: SaveIndicatorState;
}

export class SaveIndicator extends React.Component<Props> {
  renderNormalState() {
    return <Span />;
  }

  renderSavingState() {
    return <Span>Saving...</Span>;
  }

  renderPendingState() {
    const { onSaveNowClick } = this.props;
    return (
      <Span>
        Autosaved soon or
        <LinkSpan onClick={onSaveNowClick}>now</LinkSpan>
      </Span>
    );
  }

  render() {
    const { state } = this.props;
    return (
      <Wrapper>
        {state === "normal"
          ? this.renderNormalState()
          : state === "saving"
            ? this.renderSavingState()
            : state === "pending"
              ? this.renderPendingState()
              : null}
      </Wrapper>
    );
  }
}
