import React, { FC } from "react";
import { Route, Switch, RouteProps } from "react-router-dom";
import { GoogleSignedIn } from "./Auth";

export interface AuthRouteProps extends RouteProps {
  auth?: "Google";
}

interface Props {
  routes: AuthRouteProps[];
}

export const createRouter: FC<Props> = ({ routes }) => {
  return (
    <Switch>
      {routes
        .filter(v => !v.auth)
        .map((route, index) => (
          <Route {...route} key={`route-key-${index}`} />
        ))}
      <GoogleSignedIn>
        <Switch>
          {routes
            .filter(v => v.auth === "Google")
            .map((route, index) => (
              <Route {...route} key={`route-key-google-${index}`} />
            ))}
        </Switch>
      </GoogleSignedIn>
    </Switch>
  );
};
