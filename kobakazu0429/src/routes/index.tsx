import React, { FC } from "react";
import { Route, Switch, RouteProps } from "react-router-dom";

interface Props {
  routes: RouteProps[];
}

export const createRouter: FC<Props> = ({ routes }) => {
  return (
    <Switch>
      {routes.map((route, index) => (
        <Route {...route} key={`route-key-${index}`} />
      ))}
    </Switch>
  );
};
