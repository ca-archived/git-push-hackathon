import React, {useState, useEffect} from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import Home from './components/pages/Home';
import Playlist from './components/pages/Playlist';
import PlaylistItems from './components/pages/PlaylistItems';
import Header from './components/Header';

const App = () => (
  <Router>
    <Header />
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/playlist" component={Playlist} />
      <Route path="/playlist/:playlistId" component={PlaylistItems} />
    </Switch>
  </Router>
);

ReactDOM.render(<App />, document.getElementById('app'));
