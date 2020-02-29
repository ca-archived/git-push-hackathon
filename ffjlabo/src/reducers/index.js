import playlistReducer from './playlist';
import playlistItemsReducer from './playlistItems';
import {combineReducers} from 'redux';

const Reducers = combineReducers({playlistReducer, playlistItemsReducer});

export default Reducers;
