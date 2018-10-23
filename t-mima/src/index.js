import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { App } from './app';
import { Navbar } from './navbar';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(<App />, document.getElementById('root'));
ReactDOM.render(<Navbar />, document.getElementById('navbar'));

serviceWorker.unregister();
