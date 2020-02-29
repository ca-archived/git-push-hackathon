import React, {useState, useEffect} from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import {createStore, applyMiddleware} from 'redux';
import Cookies from 'js-cookie';

import Reducers from './reducers';
import Home from './components/pages/Home';
import Playlist from './components/pages/Playlist';
import PlaylistItems from './components/pages/PlaylistItems';
import Header from './components/Header';
import Login from './components/pages/Login';

const store = createStore(Reducers, applyMiddleware(thunk));

const App = () => {
  const accessToken = Cookies.get('access_token');

  if (accessToken === undefined) {
    return <Login />;
  }

  return (
    <Router>
      <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/playlist" component={Playlist} />
        <Route path="/playlist/:playlistId" component={PlaylistItems} />
      </Switch>
    </Router>
  );
};

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('app')
);
