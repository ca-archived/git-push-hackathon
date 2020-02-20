import React, { FC } from "react";
import { ThemeProvider } from "@/theme/ThemeProvider";
import { GlobalStyle } from "@/theme/GlobalStyle";
import { createRouter, AuthRouteProps } from "@/routes";
import { RootContextProvider } from "@/contexts/RootContext";
import RootStore from "@/stores/RootStore";
import { TopPage } from "@/pages/TopPage";
import { PlayerPage } from "@/pages/PlayerPage";
import { SignInPage } from "@/pages/SignInPage";
import { ModalProvider } from "@/utils/customHooks/useModal";

const routes: AuthRouteProps[] = [
  {
    exact: true,
    path: "/",
    component: TopPage,
    auth: "Google"
  },
  {
    exact: true,
    path: "/player",
    component: PlayerPage
  },
  {
    exact: true,
    path: "/sign_in",
    component: SignInPage
  }
];

const Router = createRouter({ routes });
export const App: FC = () => {
  return (
    <ThemeProvider themeName="default">
      <GlobalStyle />
      <RootContextProvider value={new RootStore()}>
        <ModalProvider>{Router}</ModalProvider>
      </RootContextProvider>
    </ThemeProvider>
  );
};
