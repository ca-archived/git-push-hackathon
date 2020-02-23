import React, {useState, useEffect} from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Link, Switch} from 'react-router-dom';

const Header = () => (
  <ul>
    <li>
      <Link to="/">Home</Link>
    </li>
    <li>
      <Link to="/playlist">playlist</Link>
    </li>
    <li>
      <Link to="/playlist/test">playlistitems</Link>
    </li>
  </ul>
);

export default Header;
