import * as React from "react";
import { Provider } from "react-redux";
import { store } from "./store";
import { BrowserRouter, Route } from "react-router-dom";
import { Home } from "./screens/home";
import { App } from "./screens/app";
import { FillStyle } from "./theme/styles";

export default class Root extends React.Component {
  render() {
    return (
      <Provider store={store}>
        <BrowserRouter>
          <div style={FillStyle}>
            <Route exact path="/" component={Home} />
            <Route exact path="/app" component={App} />
          </div>
        </BrowserRouter>
      </Provider>
    );
  }
}
