import {GET_PLAYLIST_ITEMS, ADD_PLAYLIST_ITEM} from '../actions/constants';

const initialState = {
  playlistItems: [],
};

const playlistItemsReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_PLAYLIST_ITEMS:
      return {...state, playlistItems: action.payload.playlistItems};
    case ADD_PLAYLIST_ITEM:
      const {playlistItems} = state;
      const arr = [action.payload.insertedPlaylistItem, ...playlistItems];
      return {playlistItems: arr};
    default:
      return {...state};
  }
};

export default playlistItemsReducer;
