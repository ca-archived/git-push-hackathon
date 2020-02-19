import React, { FC, useContext } from "react";
import { Redirect } from "react-router-dom";
import { observer } from "mobx-react-lite";
import RootContext from "@/contexts/RootContext";

export const GoogleSignedIn: FC = observer((props: any) => {
  const { googleOAuthStore } = useContext(RootContext);
  return googleOAuthStore.response?.accessToken ? (
    props.children
  ) : (
    <Redirect to="/sign_in" />
  );
});
