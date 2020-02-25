import React, { FC, useContext } from "react";
import { Redirect } from "react-router-dom";
import { observer } from "mobx-react-lite";
import RootContext from "@/contexts/RootContext";
import { isDevelopment, useMockApi } from "@/utils/environment";

export const GoogleSignedIn: FC = observer((props: any) => {
  const { googleOAuthStore } = useContext(RootContext);
  if (isDevelopment() && useMockApi()) return props.children;
  return googleOAuthStore.response?.accessToken ? (
    props.children
  ) : (
    <Redirect to="/sign_in" />
  );
});
