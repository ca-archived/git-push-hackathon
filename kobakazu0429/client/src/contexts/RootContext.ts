import React from "react";

import RootStore from "@/stores/RootStore";

const RootContext = React.createContext({} as RootStore);

export const { Provider: RootContextProvider } = RootContext;
export default RootContext;
