import {GET_PLAYLISTS, ADD_PLAYLIST} from '../actions/constants';

const initialState = {
  playlists: [],
};

const playlistReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_PLAYLISTS:
      return {...state, playlists: action.payload.playlists};
    case ADD_PLAYLIST:
      const {playlists} = state;
      const arr = [action.payload.insertedPlaylist, ...playlists];
      return {playlists: arr};
    default:
      return {...state};
  }
};

export default playlistReducer;
