import * as React from "react";
import { GITHUB_CLIENT_ID, GITHUB_CLIENT_SECRET } from "../api/credentials";
import * as qs from "query-string";
import { GithubOAuth } from "../api/oauth";
import { withRouter, RouteComponentProps } from "react-router";
import { Dispatch } from "redux";
import { SetGithubAccessTokenAction, setGithubAccessToken } from "../actions";
import { connect } from "react-redux";
import { Login } from "../components/templates/login";
import { Message } from "../components/templates/message";

interface P {
  location: {
    search?: string;
  };

  setAccessToken(accessToken: string): SetGithubAccessTokenAction;
}

type Props = P & RouteComponentProps<{}>;

class Comp extends React.Component<Props> {
  async componentDidMount() {
    if (this.code) {
      const oauth = new GithubOAuth();
      const accessToken = await oauth.getAccessToken(
        GITHUB_CLIENT_ID,
        GITHUB_CLIENT_SECRET,
        this.code
      );

      if (accessToken) {
        this.props.setAccessToken(accessToken);

        setTimeout(() => {
          // ページ遷移する
          this.props.history.push("/app");
        }, 1000);
      } else {
        console.error("ERROR: access token is empty");
      }
    }
  }

  private get code(): string | undefined {
    if (this.props.location.search) {
      const params = qs.parse(this.props.location.search);

      const code = params["code"];
      if (code && typeof code === "string") {
        return code;
      }
    }
  }

  renderLoginScreen() {
    return <Login onLoginButtonClick={this.onLoginButtonClick.bind(this)} />;
  }

  renderProcessingScreen() {
    return (
      <Message>
        Welcome to GitHub Gist Client
        <br />
        Processing...
      </Message>
    );
  }

  isLoggingIn() {
    return this.code !== undefined;
  }

  async onLoginButtonClick() {
    const params = qs.stringify({
      client_id: GITHUB_CLIENT_ID,
      scope: ["repo", "gist"].join(",")
    });

    window.location.href = `https://github.com/login/oauth/authorize?${params}`;
  }

  render() {
    return this.isLoggingIn()
      ? this.renderProcessingScreen()
      : this.renderLoginScreen();
  }
}

function mapDispatchToProps(dispatch: Dispatch) {
  return {
    setAccessToken: (accessToken: string) =>
      dispatch(setGithubAccessToken(accessToken))
  };
}

export const Home = connect(
  () => ({}),
  mapDispatchToProps
)(withRouter(Comp));
