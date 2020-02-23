import React, { FC, useCallback, useContext } from "react";
import styled from "styled-components";
import { useHistory } from "react-router-dom";
import { observer } from "mobx-react-lite";
import {
  GoogleLogin,
  GoogleLoginResponse,
  GoogleLoginResponseOffline
} from "react-google-login";
import RootContext from "@/contexts/RootContext";

const clientId = process.env.GOOGLE_OAUTH_CLIENT_ID ?? "";
const scopes = [
  "https://www.googleapis.com/auth/youtube",
  "https://www.googleapis.com/auth/youtube.force-ssl",
  "https://www.googleapis.com/auth/youtube.readonly"
];

export const SignInPage: FC = observer(() => {
  const { googleOAuthStore } = useContext(RootContext);
  const history = useHistory();

  const handleSuccess = useCallback(
    (response: GoogleLoginResponse | GoogleLoginResponseOffline) => {
      isOnline(response) && googleOAuthStore.setResponse(response);
      history.push("/");
    },
    []
  );

  const isOnline = (response: any): response is GoogleLoginResponse =>
    response && response.accessToken;

  const handleFailure = useCallback(
    (_response: GoogleLoginResponse | GoogleLoginResponseOffline) => {
      console.warn("Sign in with Google got failure");
    },
    []
  );

  return (
    <Wrapper>
      <AppTitle>Music Player</AppTitle>

      <GoogleLogin
        clientId={clientId}
        buttonText="Sign in with Google"
        scope={scopes.join(" ")}
        onSuccess={handleSuccess}
        onFailure={handleFailure}
        cookiePolicy={"single_host_origin"}
      />
    </Wrapper>
  );
});

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const AppTitle = styled.h1`
  text-align: center;
`;
