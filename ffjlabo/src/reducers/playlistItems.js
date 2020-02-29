const initialState = {
  playlistItems: [],
};

const playlistItemsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'GET_PLAYLIST_ITEMS':
      return {...state, playlistItems: action.payload.playlistItems};
    default:
      return {...state};
  }
};

export default playlistItemsReducer;
