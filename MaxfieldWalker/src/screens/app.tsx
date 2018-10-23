import * as React from "react";
import { AppState } from "../store";
import { connect } from "react-redux";
import { Link } from "react-router-dom";
import GistApp from "./gistApp";
import { FillStyle } from "../theme/styles";
import { AppBar, AppBarItem } from "../components/templates/appBar";
import { Github } from "./github";
import { Account } from "./account";
import { Settings } from "./settings";
import styled from "styled-components";

const Wrapper = styled.div`
  display: flex;
  position: relative;
`;

const MainWrapper = styled.div`
  position: relative;
  flex: 1;
`;

const OverlayWrapper = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
`;

interface Props {
  accessToken: string;
}

interface State {
  activeItem: AppBarItem;
}

export class Comp extends React.Component<Props, State> {
  constructor(props: Props, state: State) {
    super(props, state);

    this.state = {
      activeItem: AppBarItem.gist
    };
  }

  accessTokenExists() {
    const token = this.props.accessToken;
    return (
      (typeof token === "string" && token !== "") ||
      process.env.NODE_ENV === "development"
    );
  }

  renderErrorScreen() {
    return (
      <div>
        <p>No access token</p>
        <Link to="/">Home</Link>
      </div>
    );
  }

  onItemClick(item: AppBarItem) {
    this.setState({ activeItem: item });
  }

  render() {
    if (!this.accessTokenExists()) {
      return this.renderErrorScreen();
    }

    const { activeItem } = this.state;

    return (
      <Wrapper style={FillStyle}>
        <AppBar
          activeItem={activeItem}
          onItemClick={this.onItemClick.bind(this)}
        />
        <MainWrapper>
          <GistApp style={{ height: "100%" }} />
          {activeItem !== AppBarItem.gist ? (
            <OverlayWrapper>
              {activeItem === AppBarItem.github ? (
                <Github />
              ) : activeItem === AppBarItem.account ? (
                <Account />
              ) : activeItem === AppBarItem.settings ? (
                <Settings />
              ) : null}
            </OverlayWrapper>
          ) : null}
        </MainWrapper>
      </Wrapper>
    );
  }
}

function mapStateToProps(state: AppState) {
  return {
    accessToken: state.accessToken
  };
}

export const App = connect(mapStateToProps)(Comp);
